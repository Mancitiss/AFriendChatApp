using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.IO;
using System.Reflection;
using CryptSharp;
using Jil;
using System.Security.Cryptography.X509Certificates;
using System.Net.Security;
using System.Security.Authentication;
using System.Drawing;
using System.Configuration;
using System.Collections.Specialized;
using System.Drawing.Imaging;
using System.Collections.Concurrent;
using System.Security.Policy;

namespace AFriendServer
{
    internal class Program
    {
        static string avatar_path = ConfigurationManager.AppSettings.Get("avatar_path");
        static string img_path = ConfigurationManager.AppSettings.Get("msg_img_path");
        //Environment.GetEnvironmentVariable("certpath", EnvironmentVariableTarget.User)
        //Environment.GetEnvironmentVariable("certpass", EnvironmentVariableTarget.User)
        static X509Certificate serverCertificate = new X509Certificate(@"F:\Python Learning\web_cert2022\server.pfx", Environment.GetEnvironmentVariable("certpass", EnvironmentVariableTarget.User));

        static ConcurrentDictionary<string, Client> sessions = new ConcurrentDictionary<string, Client>();
        static ConcurrentDictionary<string, FileToWrite> files = new ConcurrentDictionary<string, FileToWrite>();

        static SqlConnection sql;
        static Random rand;
        
        
        static Thread main_thread;
        //static ManualResetEvent mainstop = new ManualResetEvent(true);

        public static Int64 NextInt64(Random rnd)
        {
            var buffer = new byte[sizeof(Int64)];
            rnd.NextBytes(buffer);
            return BitConverter.ToInt64(buffer, 0);
        }

        public static void Main(string[] args)
        {
            main_thread = Thread.CurrentThread;
            try
            {
                Console.WriteLine(Path.GetDirectoryName(Assembly.GetEntryAssembly().Location));
                Console.WriteLine("Avatar Path: " + avatar_path);
                Console.WriteLine("Img Path: " + img_path);
                rand = new Random();

                using (sql = new SqlConnection(
                        "Data Source=" +
                        Environment.GetEnvironmentVariable("DBServer", EnvironmentVariableTarget.User) +
                        ";Initial Catalog=" +
                        Environment.GetEnvironmentVariable("DBicatalog", EnvironmentVariableTarget.User) +
                        ";User ID=" +
                        Environment.GetEnvironmentVariable("DBusername", EnvironmentVariableTarget.User) +
                        ";Password=" +
                        Environment.GetEnvironmentVariable("DBpassword", EnvironmentVariableTarget.User) +
                        ";MultipleActiveResultSets = true;"
                        ))
                {
                    try
                    {

                        sql.Open();
                        ExecuteServer();
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e.ToString());

                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        internal static void shutdown(string id) 
        {
            Console.WriteLine("{0} has quit", id);
            try
            {
                sessions[id].stream.Dispose();
            } catch (Exception e)
            {

            }
            try
            {
                sessions[id].client.Dispose();
            } catch (Exception e)
            {

            }
            byte state = sessions[id].status;
            sessions.TryRemove(id, out Client temp);
            string str_id = id;
            while (str_id[0] == '0' && str_id.Length > 1) str_id.Remove(0, 1);
            using (SqlCommand cmd = new SqlCommand("update top (1) account set state=@state where id=@id", sql))
            {
                cmd.Parameters.AddWithValue("@id", Int64.Parse(str_id));
                cmd.Parameters.AddWithValue("@state", state);
                cmd.ExecuteNonQuery();
            }
        }

        private static void exception_handler(KeyValuePair<string, Client> item, string se)
        {
            try
            {
                if (se.Contains("open and available Connection"))
                {
                    sql.Open();
                }
                else if (se.Contains("Execution Timeout Expired"))
                {
                    sql.Open();
                }
                else if (se.Contains("was forcibly closed"))
                {
                    shutdown(item.Key);
                }
            }catch(Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private static void ExecuteServer()
        {
            TcpListener listener = new TcpListener(IPAddress.Any, 11111);
            listener.Start();

            Console.WriteLine("Server at: {0}", IPAddress.Any);

            try
            {

                while (true)
                {
                    //Thread.Sleep(10);
                    TcpClient client = listener.AcceptTcpClient();

                    //Console.WriteLine("Accepted Client");
                    try
                    {
                        ThreadPool.QueueUserWorkItem(Receive_from_socket_not_logged_in, client);
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e.ToString());
                    }
                    client = null;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private static bool receive_ASCII_data_automatically(SslStream s, out string data)
        {
            if (SslStream_receive_ASCII(s, 2, out data))
            {
                int bytesize;
                if (Int32.TryParse(data, out bytesize))
                {
                    if (SslStream_receive_ASCII(s, bytesize, out data))
                    {
                        if (Int32.TryParse(data, out bytesize))
                        {
                            if (SslStream_receive_ASCII(s, bytesize, out data))
                            {
                                return true;
                            }
                        }
                    }
                }
            }
            data = "";
            return false;
        }

        private static bool receive_data_automatically(SslStream s, out string data)
        {
            if (SslStream_receive(s, 4, out data))
            {
                //Console.WriteLine("1:"+data);
                int bytesize;
                if (Int32.TryParse(data, out bytesize))
                {
                    bytesize = bytesize * 2;
                    if (SslStream_receive(s, bytesize, out data))
                    {
                        //Console.WriteLine("2:" + data);
                        if (Int32.TryParse(data, out bytesize))
                        {
                            if (SslStream_receive(s, bytesize, out data))
                            {
                                //Console.WriteLine("3:" + data);
                                return true;
                            }
                        }
                    }
                }
            }
            //Console.WriteLine("wrong data: " + data);
            data = "";
            return false;
        }

        private static bool SslStream_receive_bytes(SslStream s, int byte_expected, out byte[] data)
        {
            int total_byte_received = 0;
            data = new byte[byte_expected];
            int received_byte;
            do
            {
                received_byte = s.Read(data, total_byte_received, byte_expected);
                if (received_byte > 0)
                {
                    total_byte_received += received_byte;
                    byte_expected -= received_byte;
                }
                else break;
            } while (byte_expected > 0 && received_byte > 0);
            if (byte_expected == 0) // all data received
            {
                return true;
            }
            else // data corrupted
            {
                return false;
            }
        }

        private static bool SslStream_receive_ASCII(SslStream s, int byte_expected, out string data_string)
        {
            int total_byte_received = 0;
            byte[] data = new byte[byte_expected];
            int received_byte;
            do
            {
                received_byte = s.Read(data, total_byte_received, byte_expected);
                if (received_byte > 0)
                {
                    total_byte_received += received_byte;
                    byte_expected -= received_byte;
                }
                else break;
            } while (byte_expected > 0 && received_byte > 0);
            if (byte_expected == 0) // all data received
            {
                data_string = Encoding.ASCII.GetString(data, 0, total_byte_received);
                return true;
            }
            else // data corrupted
            {
                data_string = "";
                return false;
            }
        }

        private static bool SslStream_receive(SslStream s, int byte_expected, out string data_string)
        {
            int total_byte_received = 0;
            byte[] data = new byte[byte_expected];
            int received_byte;
            do
            {
                received_byte = s.Read(data, total_byte_received, byte_expected);
                if (received_byte > 0)
                {
                    total_byte_received += received_byte;
                    byte_expected -= received_byte;
                }
                else break;
            } while (byte_expected > 0 && received_byte > 0);
            if (byte_expected == 0) // all data received
            {
                data_string = Encoding.Unicode.GetString(data, 0, total_byte_received);
                return true;
            }
            else // data corrupted
            {
                //Console.WriteLine("Corrupted: " + Encoding.Unicode.GetString(data, 0, total_byte_received));
                data_string = "";
                return false;
            }
        }

        private static string data_with_byte(string data)
        {
            if (!string.IsNullOrEmpty(data))
            {
                string databyte = Encoding.Unicode.GetByteCount(data).ToString();
                return databyte.Length.ToString().PadLeft(2, '0') + databyte + data;
            }
            return "";
        }

        private static string data_with_ASCII_byte(string data)
        {
            if (!string.IsNullOrEmpty(data))
            {
                string databyte = Encoding.ASCII.GetByteCount(data).ToString();
                return databyte.Length.ToString().PadLeft(2, '0') + databyte + data;
            }
            return "";
        }

        private static async void Receive_message(object si)
        {
            //Console.WriteLine("Work Started");
            string id = si as string;
            try
            {
                SslStream s = sessions[id].stream;
                string data;
                do
                {
                    if (SslStream_receive(s, 8, out data))
                    {
                        if (data!=null && data!="1904") Console.WriteLine("Work: " + data);
                        if (data != null && data != "")
                        {
                            string instruction = data;
                            switch (instruction)
                            {
                                case "6475":
                                    {
                                        string receiver_id;
                                        if (SslStream_receive(s, 38, out receiver_id))
                                        {
                                            Console.WriteLine(receiver_id);
                                            string id1, id2;
                                            if (id.CompareTo(receiver_id) <= 0)
                                            {
                                                id1 = id;
                                                id2 = receiver_id;
                                            }
                                            else
                                            {
                                                id1 = receiver_id;
                                                id2 = id;
                                            }
                                            if (receive_data_automatically(s, out data))
                                            {
                                                //Console.WriteLine(data);
                                                Int64 num;
                                                if (Int64.TryParse(data, out num))
                                                {
                                                    if (num == 0)
                                                    {
                                                        SqlCommand command = new SqlCommand("select top 1 count from friend where id1=@id1 and id2=@id2", sql);
                                                        command.Parameters.AddWithValue("@id1", id1);
                                                        command.Parameters.AddWithValue("@id2", id2);
                                                        using (SqlDataReader reader = command.ExecuteReader())
                                                        {
                                                            if (reader.Read())
                                                            {
                                                                num = (Int64)reader["count"];
                                                            }
                                                        }
                                                        //Console.WriteLine(num);
                                                        int i = 0;
                                                        List<MessageObject> messageObjects = new List<MessageObject>();
                                                        while (num > 0 && i < 20)
                                                        {
                                                            command = new SqlCommand("select top 1 * from message where id1=@id1 and id2=@id2 and messagenumber=@messagenumber", sql);
                                                            command.Parameters.AddWithValue("@id1", id1);
                                                            command.Parameters.AddWithValue("@id2", id2);
                                                            command.Parameters.AddWithValue("@messagenumber", num);
                                                            using (SqlDataReader reader = command.ExecuteReader())
                                                            {
                                                                if (reader.Read())
                                                                {
                                                                    //Console.WriteLine((DateTime)reader["timesent"]);
                                                                    if ((byte)reader["type"] == 0 || (byte)reader["type"] == 3)
                                                                    {
                                                                        MessageObject msgobj = new MessageObject(reader["id1"].ToString().PadLeft(19, '0'), reader["id2"].ToString().PadLeft(19, '0'), (Int64)reader["messagenumber"], (DateTime)reader["timesent"], (bool)reader["sender"], reader["message"].ToString(), (byte)reader["type"]);
                                                                        messageObjects.Add(msgobj);
                                                                    }
                                                                    else if ((byte)reader["type"] == 1 && File.Exists(img_path + id1 + "_" + id2 + "_" + num.ToString() + ".png"))
                                                                    {
                                                                        MessageObject msgobj = new MessageObject(reader["id1"].ToString().PadLeft(19, '0'), reader["id2"].ToString().PadLeft(19, '0'), (Int64)reader["messagenumber"], (DateTime)reader["timesent"], (bool)reader["sender"], ImageToString(img_path + id1 + "_" + id2 + "_" + num + ".png"), (byte)reader["type"]);
                                                                        messageObjects.Add(msgobj);
                                                                    }
                                                                }
                                                            }
                                                            num = num - 1;
                                                            i = i + 1;
                                                        }
                                                        string datasend = JSON.Serialize<List<MessageObject>>(messageObjects, Options.MillisecondsSinceUnixEpochUtc);
                                                        Console.WriteLine("===============================================");
                                                        // write datasend to console
                                                        Console.WriteLine(datasend);
                                                        Console.WriteLine("===============================================");
                                                        string datasendbyte = Encoding.Unicode.GetByteCount(datasend).ToString();
                                                        sessions[id].Queue_command(Encoding.Unicode.GetBytes("6475" + receiver_id + datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend));
                                                        //Console.WriteLine("Old messages sent");
                                                        if (sessions.ContainsKey(id))
                                                        {
                                                            if (sessions[id].loaded > 1)
                                                            {
                                                                sessions[id].loaded -= 1;
                                                            }
                                                            else if (sessions[id].loaded == 1)
                                                            {
                                                                sessions[id].Queue_command(Encoding.Unicode.GetBytes("2411"));
                                                                sessions[id].loaded -= 1;
                                                            }
                                                        }
                                                    }
                                                    else if (num > 1)
                                                    {
                                                        int i = 0;
                                                        SqlCommand command;
                                                        List<MessageObject> messageObjects = new List<MessageObject>();
                                                        while (num > 0 && i < 20)
                                                        {
                                                            command = new SqlCommand("select top 1 * from message where id1=@id1 and id2=@id2 and messagenumber=@messagenumber", sql);
                                                            command.Parameters.AddWithValue("@id1", id1);
                                                            command.Parameters.AddWithValue("@id2", id2);
                                                            command.Parameters.AddWithValue("@messagenumber", num);
                                                            using (SqlDataReader reader = command.ExecuteReader())
                                                            {
                                                                if (reader.Read())
                                                                {
                                                                    if ((byte)reader["type"] == 0 || (byte)reader["type"] == 3)
                                                                    {
                                                                        MessageObject msgobj = new MessageObject(reader["id1"].ToString().PadLeft(19, '0'), reader["id2"].ToString().PadLeft(19, '0'), (Int64)reader["messagenumber"], (DateTime)reader["timesent"], (bool)reader["sender"], reader["message"].ToString(), (byte)reader["type"]);
                                                                        messageObjects.Add(msgobj);
                                                                    }
                                                                    else if ((byte)reader["type"] == 1 && File.Exists(img_path + id1 + "_" + id2 + "_" + num.ToString() + ".png"))
                                                                    {
                                                                        MessageObject msgobj = new MessageObject(reader["id1"].ToString().PadLeft(19, '0'), reader["id2"].ToString().PadLeft(19, '0'), (Int64)reader["messagenumber"], (DateTime)reader["timesent"], (bool)reader["sender"], ImageToString(img_path + id1 + "_" + id2 + "_" + num.ToString() + ".png"), (byte)reader["type"]);
                                                                        messageObjects.Add(msgobj);
                                                                    }
                                                                }
                                                            }
                                                            num = num - 1;
                                                            i = i + 1;
                                                        }
                                                        string datasend = JSON.Serialize<List<MessageObject>>(messageObjects, Options.MillisecondsSinceUnixEpochUtc);
                                                        string datasendbyte = Encoding.Unicode.GetByteCount(datasend).ToString();
                                                        sessions[id].Queue_command(Encoding.Unicode.GetBytes("6475" + receiver_id + datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend));
                                                        //Console.WriteLine("Old messages sent");
                                                    }
                                                }
                                            }
                                        }

                                        break;
                                    } // load message

                                case "1901":
                                    {
                                        SslStream_receive(s, 4, out data);
                                        int bytesize = Int32.Parse(data) * 2;
                                        SslStream_receive(s, bytesize, out data);
                                        Int32.TryParse(data, out int temp);
                                        if (SslStream_receive(s, temp, out string data_string))// all data received, save to database, send to socket
                                        {
                                            string receiver_id = data_string.Substring(0, 19);
                                            data_string = data_string.Remove(0, 19);
                                            //save to database start
                                            string id1, id2;
                                            if (id.CompareTo(receiver_id) <= 0)
                                            {
                                                id1 = id;
                                                id2 = receiver_id;
                                            }
                                            else
                                            {
                                                id1 = receiver_id;
                                                id2 = id;
                                            }
                                            string sqlmessage = data_string;
                                            try
                                            {
                                                bool success = false;
                                                DateTime now = DateTime.Now;
                                                using (SqlCommand command = new SqlCommand("insert into message values (@id1, @id2, @n, @datetimenow, @sender, @message, 0)", sql))
                                                {
                                                    command.Parameters.AddWithValue("@id1", id1);
                                                    command.Parameters.AddWithValue("@id2", id2);
                                                    command.Parameters.AddWithValue("@n", rand.Next(-1000000000, 0));
                                                    command.Parameters.AddWithValue("@datetimenow", now);
                                                    command.Parameters.AddWithValue("@sender", id == id2);
                                                    command.Parameters.AddWithValue("@message", sqlmessage);
                                                    if (command.ExecuteNonQuery() >= 1) success = true;
                                                }
                                                if (success)
                                                {
                                                    using (SqlCommand another_command = new SqlCommand("select top 1 * from message where id1 = @id1 and id2 = @id2 and timesent = @timesent and sender = @sender", sql))
                                                    {
                                                        another_command.Parameters.AddWithValue("@id1", id1);
                                                        another_command.Parameters.AddWithValue("@id2", id2);
                                                        another_command.Parameters.AddWithValue("@timesent", now);
                                                        another_command.Parameters.AddWithValue("sender", id == id2);
                                                        using (SqlDataReader reader = another_command.ExecuteReader())
                                                        {
                                                            if (reader.Read())
                                                            {
                                                                MessageObject msgobj = new MessageObject(reader["id1"].ToString().PadLeft(19, '0'), reader["id2"].ToString().PadLeft(19, '0'), (Int64)reader["messagenumber"], (DateTime)reader["timesent"], (bool)reader["sender"], reader["message"].ToString(), (byte)reader["type"]);
                                                                //data_string = data_string.Insert(0, id);
                                                                //send to socket start
                                                                if (id != receiver_id) Send_to_id(id, msgobj);
                                                                if (!Send_to_id(receiver_id, msgobj))
                                                                {
                                                                    sessions[id].Queue_command(Encoding.Unicode.GetBytes("0404" + receiver_id));
                                                                }
                                                                else
                                                                {
                                                                    sessions[id].Queue_command(Encoding.Unicode.GetBytes("2211" + receiver_id));
                                                                }
                                                                Console.WriteLine("Sent");
                                                                //send to socket end
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                                Console.WriteLine(e.ToString());
                                                exception_handler(new KeyValuePair<string, Client>(id, sessions[id]), e.ToString());
                                            }
                                            //save to database end
                                        }
                                        else // data corrupted
                                        {
                                            Console.WriteLine("Data Corrupted");
                                        }
                                        /*
                                        byte_expected[id] = temp;*/
                                        //Console.WriteLine("After Message:" + item.Value.Available);
                                        break;
                                    } // handle message

                                case "1902": // handle image message
                                    {
                                        if (SslStream_receive(s, 38, out string receiver_id))
                                        {
                                            if (receive_ASCII_data_automatically(s, out string data_string))
                                            {
                                                //save to database start
                                                string id1, id2;
                                                if (id.CompareTo(receiver_id) <= 0)
                                                {
                                                    id1 = id;
                                                    id2 = receiver_id;
                                                }
                                                else
                                                {
                                                    id1 = receiver_id;
                                                    id2 = id;
                                                }
                                                try
                                                {
                                                    bool success = false;
                                                    DateTime now = DateTime.Now;
                                                    using (SqlCommand command = new SqlCommand("insert into message values (@id1, @id2, @n, @datetimenow, @sender, @message, 1)", sql))
                                                    {
                                                        command.Parameters.AddWithValue("@id1", id1);
                                                        command.Parameters.AddWithValue("@id2", id2);
                                                        command.Parameters.AddWithValue("@n", rand.Next(-1000000000, 0));
                                                        command.Parameters.AddWithValue("@datetimenow", now);
                                                        command.Parameters.AddWithValue("@sender", id == id2);
                                                        command.Parameters.AddWithValue("@message", "");
                                                        if (command.ExecuteNonQuery() >= 1) success = true;
                                                    }
                                                    if (success)
                                                    {
                                                        using (SqlCommand another_command = new SqlCommand("select top 1 * from message where id1 = @id1 and id2 = @id2 and timesent = @timesent and sender = @sender", sql))
                                                        {
                                                            another_command.Parameters.AddWithValue("@id1", id1);
                                                            another_command.Parameters.AddWithValue("@id2", id2);
                                                            another_command.Parameters.AddWithValue("@timesent", now);
                                                            another_command.Parameters.AddWithValue("sender", id == id2);
                                                            using (SqlDataReader reader = another_command.ExecuteReader())
                                                            {
                                                                if (reader.Read())
                                                                {
                                                                    try
                                                                    {
                                                                        string img_message = data_string;
                                                                        Image temp = StringToImage(data_string);
                                                                        string tempFile = img_path + id1 + "_" + id2 + "_" + reader["messagenumber"].ToString() + ".png";
                                                                        (new Bitmap(temp)).Save(tempFile, ImageFormat.Png);
                                                                        MessageObject msgobj = new MessageObject(reader["id1"].ToString().PadLeft(19, '0'), reader["id2"].ToString().PadLeft(19, '0'), (Int64)reader["messagenumber"], (DateTime)reader["timesent"], (bool)reader["sender"], img_message, (byte)reader["type"]);
                                                                        //data_string = data_string.Insert(0, id);
                                                                        //send to socket start
                                                                        if (id != receiver_id) Send_to_id(id, msgobj);
                                                                        if (!Send_to_id(receiver_id, msgobj))
                                                                        {
                                                                            sessions[id].Queue_command(Encoding.Unicode.GetBytes("0404" + receiver_id));
                                                                        }
                                                                        else
                                                                        {
                                                                            sessions[id].Queue_command(Encoding.Unicode.GetBytes("2211" + receiver_id));
                                                                        }
                                                                        Console.WriteLine("Sent");
                                                                    }
                                                                    catch (Exception e)
                                                                    {
                                                                        Console.WriteLine(e.ToString());
                                                                    }
                                                                    //send to socket end
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                catch (Exception e)
                                                {
                                                    Console.WriteLine(e.ToString());
                                                    exception_handler(new KeyValuePair<string, Client>(id, sessions[id]), e.ToString());
                                                }
                                                //save to database end
                                            }
                                        }
                                        break;
                                    } // handle image message

                                case "1903":
                                    {
                                        if (SslStream_receive(s, 38, out string receiver_id))
                                        {
                                            if (receive_data_automatically(s, out string file))
                                            {
                                                if (receive_ASCII_data_automatically(s, out string length))
                                                {
                                                    string id1, id2;
                                                    if (id.CompareTo(receiver_id) <= 0)
                                                    {
                                                        id1 = id;
                                                        id2 = receiver_id;
                                                    }
                                                    else
                                                    {
                                                        id1 = receiver_id;
                                                        id2 = id;
                                                    }
                                                    try
                                                    {
                                                        bool success = false;
                                                        DateTime now = DateTime.Now;
                                                        using (SqlCommand command = new SqlCommand("insert into message values (@id1, @id2, @n, @datetimenow, @sender, @message, 3)", sql))
                                                        {
                                                            command.Parameters.AddWithValue("@id1", id1);
                                                            command.Parameters.AddWithValue("@id2", id2);
                                                            command.Parameters.AddWithValue("@n", rand.Next(-1000000000, 0));
                                                            command.Parameters.AddWithValue("@datetimenow", now);
                                                            command.Parameters.AddWithValue("@sender", id == id2);
                                                            command.Parameters.AddWithValue("@message", file);
                                                            if (command.ExecuteNonQuery() >= 1) success = true;
                                                        }
                                                        if (success)
                                                        {
                                                            using (SqlCommand another_command = new SqlCommand("select top 1 * from message where id1 = @id1 and id2 = @id2 and timesent = @timesent and sender = @sender and message = @message and type = 3", sql))
                                                            {
                                                                another_command.Parameters.AddWithValue("@id1", id1);
                                                                another_command.Parameters.AddWithValue("@id2", id2);
                                                                another_command.Parameters.AddWithValue("@timesent", now);
                                                                another_command.Parameters.AddWithValue("sender", id == id2);
                                                                another_command.Parameters.AddWithValue("message", file);
                                                                using (SqlDataReader reader = another_command.ExecuteReader())
                                                                {
                                                                    if (reader.Read())
                                                                    {
                                                                        try
                                                                        {
                                                                            files.AddOrUpdate(id1 + "_" + id2 + "_" + reader["messagenumber"].ToString() + ".", new FileToWrite(long.Parse(length)), (key, oldValue) => oldValue);
                                                                            sessions[id].Queue_command
                                                                                (
                                                                                    Combine
                                                                                    (
                                                                                        Encoding.Unicode.GetBytes("1903" + receiver_id),
                                                                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(reader["messagenumber"].ToString())),
                                                                                        Encoding.Unicode.GetBytes(data_with_byte(reader["message"].ToString())),
                                                                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(files[id1 + "_" + id2 + "_" + reader["messagenumber"].ToString() + "."].size.ToString()))
                                                                                    )
                                                                                );
                                                                            MessageObject msgobj = new MessageObject(reader["id1"].ToString().PadLeft(19, '0'), reader["id2"].ToString().PadLeft(19, '0'), (Int64)reader["messagenumber"], (DateTime)reader["timesent"], (bool)reader["sender"], reader["message"].ToString(), (byte)reader["type"]);
                                                                            //data_string = data_string.Insert(0, id);
                                                                            //send to socket start
                                                                            if (id != receiver_id) Send_to_id(id, msgobj);
                                                                            if (!Send_to_id(receiver_id, msgobj))
                                                                            {
                                                                                sessions[id].Queue_command(Encoding.Unicode.GetBytes("0404" + receiver_id));
                                                                            }
                                                                            else
                                                                            {
                                                                                sessions[id].Queue_command(Encoding.Unicode.GetBytes("2211" + receiver_id));
                                                                            }
                                                                            Console.WriteLine("Sent");
                                                                        }
                                                                        catch (Exception e)
                                                                        {
                                                                            Console.WriteLine(e.ToString());
                                                                        }
                                                                        //send to socket end
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    catch (Exception e)
                                                    {
                                                        Console.WriteLine(e.ToString());
                                                        exception_handler(new KeyValuePair<string, Client>(id, sessions[id]), e.ToString());
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    } // pre-receive file from client

                                case "1904":
                                    {
                                        if (SslStream_receive(s, 38, out string receiver_id))
                                        {
                                            if (receive_ASCII_data_automatically(s, out string num))
                                            {
                                                if (long.TryParse(num, out long messagenumber))
                                                {
                                                    if (receive_ASCII_data_automatically(s, out string offsetstr))
                                                    {
                                                        if (long.TryParse(offsetstr, out long offset))
                                                        {
                                                            if (receive_ASCII_data_automatically(s, out string received_byte_str))
                                                            {
                                                                if (int.TryParse(received_byte_str, out int received_byte))
                                                                {
                                                                    if (SslStream_receive_bytes(s, received_byte, out byte[] databyte))
                                                                    {
                                                                        string id1, id2;
                                                                        if (id.CompareTo(receiver_id) <= 0)
                                                                        {
                                                                            id1 = id;
                                                                            id2 = receiver_id;
                                                                        }
                                                                        else
                                                                        {
                                                                            id1 = receiver_id;
                                                                            id2 = id;
                                                                        }
                                                                        string filename = id1 + "_" + id2 + "_" + num + ".";
                                                                        //Console.WriteLine("File: {0}", img_path + filename);
                                                                        sessions[id].files_on_transfer.AddOrUpdate(filename, true, (key, oldValue) => oldValue);
                                                                        if (sessions[id].files_on_transfer.ContainsKey(filename) && sessions[id].files_on_transfer[filename] && files.ContainsKey(filename) && files[filename].size > 0)
                                                                        {
                                                                            bool done = false;
                                                                            while (!done)
                                                                            {
                                                                                try
                                                                                {
                                                                                    if (files[filename].fileStream != null)
                                                                                    {
                                                                                        if (files[filename].fileStream.CanSeek && files[filename].fileStream.CanWrite)
                                                                                        {
                                                                                            files[filename].fileStream.Seek(offset, SeekOrigin.Begin);
                                                                                            files[filename].fileStream.Write(databyte, 0, received_byte);
                                                                                            files.AddOrUpdate(filename, new FileToWrite(files[filename].size - received_byte, files[filename].fileStream), (key, oldValue) => new FileToWrite(oldValue.size - received_byte, oldValue.fileStream));
                                                                                            if (files[filename].size <= 0)
                                                                                            {
                                                                                                files.TryRemove(filename, out FileToWrite temp);
                                                                                                try
                                                                                                {
                                                                                                    temp.fileStream.Dispose();
                                                                                                }
                                                                                                catch { }
                                                                                            }
                                                                                            //Console.WriteLine("Write to file ended");
                                                                                            done = true;
                                                                                        }
                                                                                    } 
                                                                                    else
                                                                                    {
                                                                                        files[filename].fileStream = File.Open(img_path + filename, FileMode.OpenOrCreate);
                                                                                        if (files[filename].fileStream.CanSeek && files[filename].fileStream.CanWrite)
                                                                                        {
                                                                                            files[filename].fileStream.Seek(offset, SeekOrigin.Begin);
                                                                                            files[filename].fileStream.Write(databyte, 0, received_byte);
                                                                                            files.AddOrUpdate(filename, new FileToWrite(files[filename].size - received_byte, files[filename].fileStream), (key, oldValue) => new FileToWrite(oldValue.size - received_byte, oldValue.fileStream));
                                                                                            if (files[filename].size <= 0)
                                                                                            {
                                                                                                files.TryRemove(filename, out FileToWrite temp);
                                                                                                try
                                                                                                {
                                                                                                    temp.fileStream.Dispose();
                                                                                                }
                                                                                                catch { }
                                                                                                sessions[id].files_on_transfer.TryRemove(filename, out bool tempbool);
                                                                                            }
                                                                                            //Console.WriteLine("Write to file ended");
                                                                                            done = true;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                catch (Exception e)
                                                                                {
                                                                                    if (e.ToString().Contains("being used by another process"))
                                                                                    {
                                                                                        //Console.WriteLine("Try again!");
                                                                                        await Task.Delay(100);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        Console.WriteLine("Fatal error: {0}, stopping", e.ToString());
                                                                                        files.TryRemove(filename, out FileToWrite temp);
                                                                                        try
                                                                                        {
                                                                                            temp.fileStream.Dispose();
                                                                                        } catch { }
                                                                                        sessions[id].files_on_transfer.TryRemove(filename, out bool tempbool);
                                                                                        throw e;
                                                                                    }
                                                                                }
                                                                            }
                                                                        } 
                                                                        else if (sessions[id].files_on_transfer.ContainsKey(filename) && sessions[id].files_on_transfer[filename] == false)
                                                                        {

                                                                            files.TryRemove(filename, out FileToWrite temp);
                                                                            if (temp != null)
                                                                            try
                                                                            {
                                                                                temp.fileStream.Dispose();
                                                                            }
                                                                            catch { }
                                                                            sessions[id].files_on_transfer.TryRemove(filename, out bool tempbool);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    } // receive file from client

                                case "1905":
                                    {
                                        if (SslStream_receive(s, 38, out string receiver_id))
                                        {
                                            if (receive_ASCII_data_automatically(s, out string num))
                                            {
                                                Task.Run(() => Send_file(id, receiver_id, num));
                                            }
                                        }
                                        break;
                                    } // send file to client

                                case "1234":
                                    {
                                        string receiver_id;
                                        if (SslStream_receive(s, 38, out receiver_id))
                                        {
                                            string id1, id2;
                                            if (id.CompareTo(receiver_id) <= 0)
                                            {
                                                id1 = id;
                                                id2 = receiver_id;
                                            }
                                            else
                                            {
                                                id1 = receiver_id;
                                                id2 = id;
                                            }
                                            string boolstr;
                                            if (SslStream_receive(s, 2, out boolstr))
                                            {
                                                using (SqlCommand command = new SqlCommand("update top (1) seen set seen=@bool where id1=@id1 and id2=@id2", sql))
                                                {
                                                    if (boolstr == "0")
                                                    {
                                                        command.Parameters.AddWithValue("@bool", 0);
                                                    }
                                                    else
                                                    {
                                                        command.Parameters.AddWithValue("@bool", 1);
                                                    }
                                                    command.Parameters.AddWithValue("@id1", id1);
                                                    command.Parameters.AddWithValue("@id2", id2);
                                                    command.ExecuteNonQuery();
                                                }
                                            }
                                        }

                                        break;
                                    } // me seen

                                case "0708":
                                    {
                                        string receiver_id;
                                        if (SslStream_receive(s, 38, out receiver_id))
                                        {
                                            string id1, id2;
                                            if (id.CompareTo(receiver_id) <= 0)
                                            {
                                                id1 = id;
                                                id2 = receiver_id;
                                            }
                                            else
                                            {
                                                id1 = receiver_id;
                                                id2 = id;
                                            }
                                            string commandtext = "select top 1 seen from seen where id1=@id1 and id2=@id2";
                                            SqlCommand command = new SqlCommand(commandtext, sql);
                                            command.Parameters.AddWithValue("@id1", Int64.Parse(id1));
                                            command.Parameters.AddWithValue("@id2", Int64.Parse(id2));
                                            bool result = false;
                                            using (SqlDataReader reader = command.ExecuteReader())
                                            {
                                                if (reader.Read())
                                                {
                                                    result = (bool)reader[0];
                                                }
                                            }
                                            if (result)
                                            {
                                                sessions[id].Queue_command(Encoding.Unicode.GetBytes("0708" + receiver_id + "1"));
                                            }
                                            else
                                            {
                                                sessions[id].Queue_command(Encoding.Unicode.GetBytes("0708" + receiver_id + "0"));
                                            }
                                        }

                                        break;
                                    } // load seen

                                case "2002":
                                    {
                                        string receiver_id;
                                        if (SslStream_receive(s, 38, out receiver_id))
                                        {
                                            string id1, id2;
                                            if (id.CompareTo(receiver_id) <= 0)
                                            {
                                                id1 = id;
                                                id2 = receiver_id;
                                            }
                                            else
                                            {
                                                id1 = receiver_id;
                                                id2 = id;
                                            }
                                            string messagenumberstring;
                                            if (receive_data_automatically(s, out messagenumberstring))
                                            {
                                                long messagenumber;
                                                if (long.TryParse(messagenumberstring, out messagenumber))
                                                {
                                                    using (SqlCommand command = new SqlCommand("delete top (1) from message where id1=@id1 and id2=@id2 and messagenumber=@messagenumber", sql))
                                                    {
                                                        command.Parameters.AddWithValue("@id1", id1);
                                                        command.Parameters.AddWithValue("@id2", id2);
                                                        command.Parameters.AddWithValue("messagenumber", messagenumber);
                                                        command.ExecuteNonQuery();
                                                    }
                                                    if (File.Exists(img_path + id1 + "_" + id2 + "_" + messagenumber.ToString() + ".png"))
                                                    {
                                                        File.Delete(img_path + id1 + "_" + id2 + "_" + messagenumber.ToString() + ".png");
                                                    }
                                                    try
                                                    {
                                                        string file = img_path + id1 + "_" + id2 + "_" + messagenumber.ToString() + ".";
                                                        if (File.Exists(file))
                                                        {
                                                            //Console.WriteLine("=========================================================================");
                                                            string filename = id1 + "_" + id2 + "_" + messagenumber + ".";
                                                            if (sessions.ContainsKey(id1) && sessions[id1].files_on_transfer.ContainsKey(file))
                                                            {
                                                                sessions[id1].files_on_transfer[file] = false;
                                                            }
                                                            if (sessions.ContainsKey(id2) && sessions[id2].files_on_transfer.ContainsKey(file))
                                                            {
                                                                sessions[id2].files_on_transfer[file] = false;
                                                            }
                                                            try
                                                            {
                                                                files.TryRemove(filename, out FileToWrite temp);
                                                                if (temp != null)
                                                                try
                                                                {
                                                                    temp.fileStream.Dispose();
                                                                }
                                                                catch { }
                                                            } catch (Exception exc)
                                                            {

                                                            }
                                                            Task.Run(() => Delete_this_file(file));
                                                        }
                                                    } catch (Exception ex) { }
                                                }
                                                if (sessions.ContainsKey(receiver_id))
                                                {
                                                    sessions[receiver_id].Queue_command(Encoding.Unicode.GetBytes("2002" + id + data_with_byte(messagenumber.ToString())));
                                                }
                                            }
                                        }

                                        break;
                                    } // delete message

                                case "2004":
                                    shutdown(id);
                                    break;
                                case "0609":
                                    {
                                        if (SslStream_receive(s, 38, out data))
                                        {
                                            string commandtext = "select top 1 id, name from account where id=@id and private=0";
                                            SqlCommand command = new SqlCommand(commandtext, sql);
                                            command.Parameters.AddWithValue("@id", Int64.Parse(data));
                                            using (SqlDataReader reader = command.ExecuteReader())
                                            {
                                                if (reader.Read())
                                                {
                                                    int state = sessions.ContainsKey(reader["id"].ToString()) ? 1 : 0;
                                                    string datasend = reader["id"].ToString().PadLeft(19, '0') + " " + reader["id"].ToString() + " " + reader["name"].ToString() + " " + state.ToString();
                                                    string datasendbyte = Encoding.Unicode.GetByteCount(datasend).ToString();
                                                    sessions[id].Queue_command(Encoding.Unicode.GetBytes("1609" + datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend));
                                                }
                                                else
                                                {
                                                    sessions[id].Queue_command(Encoding.Unicode.GetBytes("2609")); // info not found
                                                }
                                            }
                                        }

                                        break;
                                    } // iplookup
                                /*
                            case "0610":
                                {
                                    if (receive_data_automatically(s, out data))
                                    {
                                        string commandtext = "select top 1 id, username, name, state from account where username=@username and private=0";
                                        SqlCommand command = new SqlCommand(commandtext, sql);
                                        command.Parameters.AddWithValue("@username", data);
                                        using (SqlDataReader reader = command.ExecuteReader())
                                        {
                                            if (reader.Read())
                                            {
                                                string datasend = reader["id"].ToString().PadLeft(19, '0') + " " + reader["username"].ToString() + " " + reader["name"].ToString() + " " + reader["state"].ToString();
                                                string datasendbyte = Encoding.Unicode.GetByteCount(datasend).ToString();
                                                sessions[id].Queue_command(Encoding.Unicode.GetBytes("1609" + datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend));
                                                //Console.WriteLine("Info sent");
                                            }
                                            else
                                            {
                                                sessions[id].Queue_command(Encoding.Unicode.GetBytes("2609")); // info not found
                                            }
                                        }
                                    }

                                    break;
                                } //nameloopkpup
                                */
                                case "1060":
                                    {
                                        string requested_id;
                                        if (SslStream_receive(s, 38, out requested_id))
                                        {
                                            /*
                                            SqlCommand command = new SqlCommand("select avatar from account where id=@id", sql);
                                            command.Parameters.AddWithValue("@id", requested_id);
                                            using (SqlDataReader reader = command.ExecuteReader())
                                            {*/
                                            string path = avatar_path + requested_id + ".png";
                                            if (File.Exists(path))
                                            {
                                                /*
                                                if (reader[0].GetType() != typeof(DBNull))
                                                {*/
                                                sessions[id].Queue_command(Combine(Encoding.Unicode.GetBytes("1060" + requested_id), Encoding.ASCII.GetBytes(data_with_ASCII_byte(ImageToString(path)))));
                                                Console.WriteLine("Friend avatar sent!");

                                                //}
                                            }
                                            //}
                                        }

                                        break;
                                    } // load friend's avatars

                                case "0601":
                                    {
                                        string img_string;
                                        if (receive_ASCII_data_automatically(s, out img_string))
                                        {
                                            string tempFile = avatar_path + id + ".png";
                                            (new Bitmap(StringToImage(img_string))).Save(tempFile, ImageFormat.Png);
                                            /*
                                            using (SqlCommand command = new SqlCommand("update top (1) account set avatar=@avatar where id=@id", sql))
                                            {
                                                command.Parameters.AddWithValue("@avatar", img_string);
                                                command.Parameters.AddWithValue("@id", Int64.Parse(id));
                                                command.ExecuteNonQuery();
                                            }
                                            */
                                        }

                                        break;
                                    } // set avatar

                                case "4269":
                                    {
                                        string opw;
                                        if (receive_data_automatically(s, out opw))
                                        {
                                            string pw;
                                            if (receive_data_automatically(s, out pw))
                                            {
                                                SqlCommand command = new SqlCommand("Select top 1 pw from account where id=@id", sql);
                                                Int64 longkey;
                                                if (Int64.TryParse(id, out longkey))
                                                {
                                                    command.Parameters.AddWithValue("@id", Int64.Parse(id));
                                                    using (SqlDataReader reader = command.ExecuteReader())
                                                    {
                                                        if (reader.Read())
                                                        {
                                                            if (Crypter.CheckPassword(opw, reader["pw"].ToString()))
                                                            {
                                                                using (SqlCommand changepass = new SqlCommand("update top (1) account set pw = @pw where id = @id", sql))
                                                                {
                                                                    changepass.Parameters.AddWithValue("@pw", Crypter.Blowfish.Crypt(pw));
                                                                    changepass.Parameters.AddWithValue("@id", id);
                                                                    if (changepass.ExecuteNonQuery() == 1)
                                                                    {
                                                                        sessions[id].Queue_command(Encoding.Unicode.GetBytes("4269"));
                                                                    }
                                                                }
                                                            }
                                                            else
                                                            {
                                                                sessions[id].Queue_command(Encoding.Unicode.GetBytes("9624"));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        break;
                                    } // change pass

                                case "1508":
                                    {
                                        using (SqlCommand command = new SqlCommand("update top (1) account set private=1 where id=@id", sql))
                                        {
                                            command.Parameters.AddWithValue("@id", id);
                                            command.ExecuteNonQuery();
                                        }
                                        break;
                                    } // set private = true

                                case "0508":
                                    {
                                        using (SqlCommand command = new SqlCommand("update top (1) account set private=0 where id=@id", sql))
                                        {
                                            command.Parameters.AddWithValue("@id", id);
                                            command.ExecuteNonQuery();
                                        }
                                        break;
                                    } // set private = false;

                                case "1012":
                                    {
                                        string newname;
                                        if (receive_data_automatically(s, out newname))
                                        {
                                            using (SqlCommand changename = new SqlCommand("update top (1) account set name = @name where id = @id", sql))
                                            {
                                                changename.Parameters.AddWithValue("@name", newname);
                                                changename.Parameters.AddWithValue("@id", id);
                                                if (changename.ExecuteNonQuery() == 1)
                                                {
                                                    sessions[id].Queue_command(Encoding.Unicode.GetBytes("1012"));
                                                }
                                            }
                                        }

                                        break;
                                    } // user has changed their name

                                case "5859":
                                    {
                                        string receiver_id;
                                        if (SslStream_receive(s, 38, out receiver_id))
                                        {
                                            string id1, id2;
                                            if (id.CompareTo(receiver_id) <= 0)
                                            {
                                                id1 = id;
                                                id2 = receiver_id;
                                            }
                                            else
                                            {
                                                id1 = receiver_id;
                                                id2 = id;
                                            }
                                            using (SqlCommand command = new SqlCommand("delete top (1) from friend where id1=@id1 and id2=@id2", sql))
                                            {
                                                command.Parameters.AddWithValue("@id1", id1);
                                                command.Parameters.AddWithValue("@id2", id2);
                                                command.ExecuteNonQuery();
                                            }
                                            Thread thread = new Thread(() => Delete_conversation_thread(id1, id2));
                                            thread.IsBackground = true;
                                            thread.Start();
                                        }

                                        break;
                                    } // delete conversation

                                case "7351":
                                    {
                                        SslStream_receive(s, 2, out string statestr);
                                        if (byte.TryParse(statestr, out byte state))
                                        {
                                            sessions[id].status = state;
                                        }
                                        break;
                                    }

                                default:
                                    shutdown(id);
                                    Console.WriteLine("Received strange signal, socket closed");
                                    break;
                            }
                        }
                        else
                        {
                            shutdown(id);
                            Console.WriteLine("Received strange signal, socket closed (2)");
                        }
                        //sessions[id].stream = s;
                        Console.WriteLine("Work finished");
                    }
                    else
                    {
                        shutdown(id);
                        Console.WriteLine("Received strange signal, socket closed (3)");
                    }
                } while (sessions[id].client.Available > 0);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
                Console.WriteLine("Work quitted");
                try
                {
                    exception_handler(new KeyValuePair<string, Client>(id, sessions[id]), e.ToString());
                } catch (Exception xe)
                {
                    Console.WriteLine(xe.ToString());
                }
            }
            finally
            {
                try
                {
                    if (sessions.ContainsKey(id))
                        Interlocked.Exchange(ref sessions[id].is_locked, 0);
                } catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
        }

        private static async void Send_file(string id, string receiver_id, string num)
        {
            string id1, id2;
            if (id.CompareTo(receiver_id) <= 0)
            {
                id1 = id;
                id2 = receiver_id;
            }
            else
            {
                id1 = receiver_id;
                id2 = id;
            }
            string file = img_path + id1 + "_" + id2 + "_" + num + ".";
            if (sessions[id].files_on_transfer.ContainsKey(file)) return;
            sessions[id].files_on_transfer.TryAdd(file, true);
            try
            {
                if (File.Exists(file))
                {
                    using (FileStream fileStream = File.Open(file, FileMode.Open))
                    {
                        long filesize = fileStream.Length;
                        sessions[id].Queue_command
                        (
                            Combine
                            (
                                Encoding.Unicode.GetBytes("1905" + receiver_id),
                                Encoding.ASCII.GetBytes(data_with_ASCII_byte(num)),
                                Encoding.ASCII.GetBytes(data_with_ASCII_byte(filesize.ToString()))
                            )
                        );
                        long offset = 0;
                        byte[] buffer = new byte[32768];
                        while (offset < filesize)
                        {
                            if (filesize - offset > buffer.Length)
                            {
                                int first_byte_expected = buffer.Length;
                                int byte_expected = first_byte_expected;
                                int total_byte_received = 0;
                                int received_byte;
                                do
                                {
                                    received_byte = fileStream.Read(buffer, total_byte_received, byte_expected);
                                    if (received_byte > 0)
                                    {
                                        total_byte_received += received_byte;
                                        byte_expected -= received_byte;
                                    }
                                    else break;
                                } while (byte_expected > 0 && received_byte > 0);
                                while (sessions[id].commands.Count > 5 || sessions[id].is_locked == 1) await Task.Delay(30);
                                if (total_byte_received == first_byte_expected && sessions[id].files_on_transfer[file])
                                {
                                    sessions[id].Queue_command(Combine(Encoding.Unicode.GetBytes("1904"),
                                        Encoding.Unicode.GetBytes(receiver_id),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(num)),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(offset.ToString())),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(received_byte.ToString())),
                                        buffer));
                                }
                                else
                                {
                                    sessions[id].Queue_command(Combine(Encoding.Unicode.GetBytes("1904"),
                                        Encoding.Unicode.GetBytes(receiver_id),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(num)),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte("-1"))));
                                    break;
                                }
                                offset += total_byte_received;
                            }
                            else
                            {
                                int first_byte_expected = (int)(filesize - offset);
                                int byte_expected = first_byte_expected;
                                byte[] final_buffer = new byte[(int)(filesize - offset)];
                                int total_byte_received = 0;
                                int received_byte;
                                do
                                {
                                    received_byte = fileStream.Read(final_buffer, total_byte_received, byte_expected);
                                    if (received_byte > 0)
                                    {
                                        total_byte_received += received_byte;
                                        byte_expected -= received_byte;
                                    }
                                    else break;
                                } while (byte_expected > 0 && received_byte > 0);
                                if (total_byte_received == first_byte_expected && sessions[id].files_on_transfer[file])
                                {
                                    sessions[id].Queue_command(Combine(Encoding.Unicode.GetBytes("1904"),
                                        Encoding.Unicode.GetBytes(receiver_id),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(num)),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(offset.ToString())),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(received_byte.ToString())),
                                        final_buffer));
                                } else
                                {
                                    sessions[id].Queue_command(Combine(Encoding.Unicode.GetBytes("1904"),
                                        Encoding.Unicode.GetBytes(receiver_id),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte(num)),
                                        Encoding.ASCII.GetBytes(data_with_ASCII_byte("-1"))));
                                    break;
                                }
                                offset += total_byte_received;
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
            finally
            {
                try
                {
                    sessions[id].files_on_transfer.TryRemove(file, out bool temp);
                } catch { }
            }
        }

        private static async void Delete_conversation_thread(string id1, string id2)
        {
            using (SqlCommand command = new SqlCommand("delete from message where id1=@id1 and id2=@id2", sql))
            {
                command.Parameters.AddWithValue("@id1", id1);
                command.Parameters.AddWithValue("@id2", id2);
                command.ExecuteNonQuery();
            }
            using (SqlCommand command = new SqlCommand("delete from seen where id1=@id1 and id2=@id2", sql))
            {
                command.Parameters.AddWithValue("@id1", id1);
                command.Parameters.AddWithValue("@id2", id2);
                command.ExecuteNonQuery();
            }
            string filestodelete = id1 + "_" + id2 + "_" + "*.*";
            if (filestodelete.Length > 19) // simple protection against accidentally deleting system files
            {
                try
                {
                    string[] fileslist = System.IO.Directory.GetFiles(img_path, filestodelete);
                    foreach (string file in fileslist)
                    {
                        try
                        {
                            if (file.Length > 19) File.Delete(file);
                        }
                        catch (Exception e)
                        {
                            if (e.ToString().Contains("is being used by") && long.TryParse(file.Substring(file.LastIndexOf('_')+1, file.Length-file.LastIndexOf('_')-2), out long result)) 
                            {
                                string filename = id1 + "_" + id2 + "_" + result + ".";
                                if (sessions[id1].files_on_transfer.ContainsKey(filename))
                                {
                                    sessions[id1].files_on_transfer[filename] = false;
                                }
                                if (sessions[id2].files_on_transfer.ContainsKey(filename))
                                {
                                    sessions[id2].files_on_transfer[filename] = false;
                                }
                                Task.Run(()=>Delete_this_file(filename));
                            } 
                            else Console.WriteLine(e.ToString());
                        }
                    }
                } catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                }
            }
        }

        private static async void Delete_this_file(string file)
        {
            bool done = false;
            do
            {
                try
                {
                    if (files.TryRemove(file, out FileToWrite temp))
                    {
                        try
                        {
                            temp.fileStream.Dispose();
                        }
                        catch { }
                    }
                    //Console.WriteLine("*******************************Try deleting file: {0}", file);
                    File.Delete(file);
                    done = true;
                }
                catch (Exception ex)
                {
                    if (ex.ToString().Contains("is being used by")) {Console.WriteLine("File is in used {0}", file); await Task.Delay(100); }
                    else
                    {
                        Console.WriteLine(ex.ToString());
                        done = true;
                    }
                }
            } while (!done);
        }

        private static bool Send_to_id(string id, MessageObject msgobj)
        {
            // do something
            bool success = false;
            if (sessions.ContainsKey(id))
            {
                try
                {
                    string data = JSON.Serialize<MessageObject>(msgobj, Options.MillisecondsSinceUnixEpochUtc);
                    string data_string = Encoding.Unicode.GetByteCount(data).ToString();
                    sessions[id].Queue_command(Encoding.Unicode.GetBytes("1901" + data_string.Length.ToString().PadLeft(2, '0') + data_string + data));
                    //sessions[id].stream.Flush();
                    success = true;
                }
                catch(IOException ioe)
                {
                    if (ioe.ToString().Contains("was forcibly closed")) shutdown(id);
                    else Console.WriteLine(ioe.ToString());
                }
                catch (Exception e)
                {
                    success = false;
                    Console.WriteLine(e.ToString());
                }
            }
            return success;
        }

        private static byte[] Combine(params byte[][] arrays)
        {
            byte[] rv = new byte[arrays.Sum(a => a.Length)];
            int offset = 0;
            foreach (byte[] array in arrays)
            {
                System.Buffer.BlockCopy(array, 0, rv, offset, array.Length);
                offset += array.Length;
            }
            return rv;
        }

        private static async void Receive_from_socket_not_logged_in(object si)
        {
            TcpClient c = si as TcpClient;
            // A client has connected. Create the
            // SslStream using the client's network stream.
            SslStream sslStream = new SslStream(c.GetStream(), false);
            // Authenticate the server but don't require the client to authenticate.
            try
            {
                sslStream.AuthenticateAsServer(serverCertificate, clientCertificateRequired: false, checkCertificateRevocation: true);

                // Display the properties and settings for the authenticated stream.
                /*
                DisplaySecurityLevel(sslStream);
                DisplaySecurityServices(sslStream);
                DisplayCertificateInformation(sslStream);
                DisplayStreamProperties(sslStream);
                */

                SslStream_receive(sslStream, 8, out string data);
                //Console.WriteLine("not logged in:"+data);
                if (data != null && data != "")
                {
                    string instruction = data;
                    if (instruction == "0012") //0012 = work available
                    {
                        SslStream_receive_ASCII(sslStream, 19, out data);
                        sslStream.Dispose();
                        c.Dispose();
                        bool pass = false;
                        if (sessions.ContainsKey(data) && 1 == Interlocked.Exchange(ref sessions[data].is_waited, 1)) pass = true;
                        int h = 0;
                        while (!pass && h++ < 20 && !sessions.ContainsKey(data)) 
                        {
                                await Task.Delay(1000);
                        }
                        if (!pass && sessions.ContainsKey(data))
                        {
                            try
                            {
                                bool do_work = false;
                                while (1 == Interlocked.Exchange(ref sessions[data].is_locked, 1))
                                {
                                    await Task.Delay(1000);
                                }
                                //Console.WriteLine(item.Key + " is online");
                                if (sessions[data].client.Connected)
                                {
                                    if (sessions[data].client.Available > 0) 
                                    {
                                        //sessions[data].loopnum = 0;
                                        if (!sessions[data].client.Connected) // Something bad has happened, shut down
                                        {
                                            try
                                            {
                                                shutdown(data);
                                            }
                                            catch (Exception e)
                                            {
                                                Console.WriteLine(e.ToString());
                                            }
                                        }
                                        else // There is data waiting to be read"
                                        {
                                            try
                                            {
                                                do_work = true;
                                                ThreadPool.QueueUserWorkItem(Receive_message, data);
                                            }
                                            catch (Exception e)
                                            {
                                                exception_handler(new KeyValuePair<string, Client>(data, sessions[data]), e.ToString());
                                                Console.WriteLine(e.ToString());
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    shutdown(data);
                                }
                                if (!do_work) Interlocked.Exchange(ref sessions[data].is_locked, 0);
                            }
                            catch (Exception clientquit)
                            {
                                //Console.WriteLine(clientquit.ToString());
                                shutdown(data);
                            }
                            finally
                            {
                                try
                                {
                                    Interlocked.Exchange(ref sessions[data].is_waited, 0);
                                }
                                catch (Exception e)
                                {

                                }
                            }
                        }
                    }
                    else
                    if (instruction == "0010") // 0010 = log in
                    {
                        receive_data_automatically(sslStream, out data);
                        string[] list_str = new string[2];
                        list_str[0] = data;
                        receive_data_automatically(sslStream, out data);
                        list_str[1] = data;
                        Console.WriteLine(list_str[0]);
                        Console.WriteLine(list_str[1]);
                        try
                        {
                            Console.WriteLine("Before avatar");
                            string commandtext = "select top 1 id, name, pw, avatar, private, state from account where username=@username";
                            SqlCommand command = new SqlCommand(commandtext, sql);
                            command.Parameters.AddWithValue("@username", list_str[0]);
                            using (SqlDataReader reader = command.ExecuteReader())
                            {
                                Console.WriteLine("After avatar");
                                if (reader.Read())
                                {
                                    //if (list_str[1] == reader["pw"].ToString())
                                    if (list_str[1] == reader["pw"].ToString() || Crypter.CheckPassword(list_str[1], reader["pw"].ToString()))
                                    {
                                        if (list_str[1] == reader["pw"].ToString())
                                        {
                                            using (SqlCommand changepass = new SqlCommand("update top (1) account set pw = @pw where id = @id", sql))
                                            {
                                                changepass.Parameters.AddWithValue("@pw", Crypter.Blowfish.Crypt(list_str[1]));
                                                changepass.Parameters.AddWithValue("@id", reader["id"].ToString());
                                                changepass.ExecuteNonQuery();
                                            }
                                        }
                                        string id = reader["id"].ToString();
                                        string str_id = id;
                                        while (id.Length < 19) id = '0' + id;

                                        string name = reader["name"].ToString();
                                        string namebyte = Encoding.Unicode.GetByteCount(name).ToString();
                                        byte mystate = byte.Parse(reader["state"].ToString());

                                        sslStream.Write(Encoding.Unicode.GetBytes("0200"
                                            + id + namebyte.Length.ToString().PadLeft(2, '0') + namebyte + name + reader["private"].ToString().PadRight(5, ' ')));
                                        Console.WriteLine("Before dictionaries");
                                        try
                                        {
                                            if (sessions.ContainsKey(id))
                                            {
                                                try
                                                {
                                                    Interlocked.Exchange(ref sessions[id].is_locked, 1);
                                                    sessions[id].stream.Write(Encoding.Unicode.GetBytes("2004"));
                                                    Console.WriteLine("User logged in from another device");
                                                }
                                                catch (Exception iknow)
                                                {

                                                }
                                                finally
                                                {
                                                    shutdown(id);
                                                }
                                                
                                            }
                                            Client client = new Client();
                                            client.loaded = 0;
                                            client.loopnum = 0;
                                            
                                            Console.WriteLine("got id");

                                            Int64 id_int = (Int64)reader["id"];
                                            SqlCommand friendcommand = new SqlCommand("select id1, id2 from friend where id1=@id or id2=@id", sql);
                                            friendcommand.Parameters.AddWithValue("@id", id_int);
                                            using (SqlDataReader friendreader = friendcommand.ExecuteReader())
                                            {
                                                while (friendreader.Read())
                                                {
                                                    client.loaded += 1;
                                                    Int64 friendid = (Int64)friendreader["id1"];
                                                    if (id_int == friendid) friendid = (Int64)friendreader["id2"];

                                                    string friendcommandtext = "select top 1 id, name from account where id=@id";
                                                    SqlCommand friendcommandget = new SqlCommand(friendcommandtext, sql);
                                                    friendcommandget.Parameters.AddWithValue("@id", friendid);
                                                    using (SqlDataReader readerget = friendcommandget.ExecuteReader())
                                                    {
                                                        if (readerget.Read())
                                                        {
                                                            int state = sessions.ContainsKey(readerget["id"].ToString()) ? 1 : 0;
                                                            string datasend = readerget["id"].ToString().PadLeft(19, '0') + " " + readerget["id"].ToString() + " " + readerget["name"].ToString() + " " + state.ToString();
                                                            string datasendbyte = Encoding.Unicode.GetByteCount(datasend).ToString();
                                                            sslStream.Write(Encoding.Unicode.GetBytes("1609" + datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend));
                                                        }
                                                    }
                                                }
                                            }
                                            if (client.loaded == 0)
                                            {
                                                sslStream.Write(Encoding.Unicode.GetBytes("2411"));
                                            }
                                            /*
                                            if (reader["avatar"].GetType() != typeof(DBNull))
                                            {
                                                Console.WriteLine("Before get avatar");
                                                string tmp = reader["avatar"].ToString();
                                                string tmpbyte = Encoding.ASCII.GetByteCount(tmp).ToString();
                                                sslStream.Write(Combine(Encoding.Unicode.GetBytes("0601"), Encoding.ASCII.GetBytes(tmpbyte.Length.ToString().PadLeft(2, '0') + tmpbyte + tmp)));
                                            }
                                            */
                                            string avt = avatar_path + id + ".png";
                                            try
                                            {
                                                if (File.Exists(avt))
                                                {
                                                    Console.WriteLine("Before get avatar");
                                                    sslStream.Write(Combine(Encoding.Unicode.GetBytes("0601"), Encoding.ASCII.GetBytes(data_with_ASCII_byte(ImageToString(avt)))));
                                                }
                                            } catch (Exception exc)
                                            {
                                                Console.WriteLine(exc.ToString());
                                            }
                                            while (sessions.ContainsKey(id)) await Task.Delay(1000);
                                            client.client = c;
                                            client.stream = sslStream;
                                            client.is_locked = 0;
                                            client.id = id;
                                            client.status = mystate;
                                            client.stream.Write(Encoding.Unicode.GetBytes("7351" + mystate.ToString()));
                                            sessions.AddOrUpdate(id, client, (key, oldValue) => { shutdown(key); return client; });
                                            Console.WriteLine("Joined");
                                        } catch (Exception e)
                                        {
                                            Console.WriteLine(e.ToString());
                                            shutdown(str_id);
                                        }/*
                                        finally
                                        {
                                            is_locked[id] = false;
                                        }*/
                                        c = null;
                                        sslStream = null;
                                    }
                                    else
                                    {
                                        try
                                        {
                                            try
                                            {
                                                sslStream.Write(Encoding.Unicode.GetBytes("-200"));
                                            }
                                            catch
                                            {

                                            }
                                            try
                                            {
                                                sslStream.Dispose();
                                            }
                                            catch
                                            {

                                            }
                                            c.Dispose();
                                        } catch (Exception e)
                                        {
                                            Console.WriteLine();
                                        }
                                    } // wrong password

                                }
                                else
                                {
                                    try
                                    {
                                        try
                                        {
                                            sslStream.Write(Encoding.Unicode.GetBytes("-200"));
                                        } catch
                                        {

                                        }
                                        try
                                        {
                                            sslStream.Dispose();
                                        }
                                        catch
                                        {

                                        }
                                        c.Dispose();
                                    } catch (Exception e)
                                    {
                                        Console.WriteLine(e.ToString());
                                    }
                                } // log-in failed account doesn't exist
                            }
                        }
                        catch (Exception e)
                        {
                            Console.WriteLine(e.ToString());
                        }
                    }
                    else if (instruction == "0011") // 0011 = sign up 
                    {
                        try
                        {
                            receive_data_automatically(sslStream, out data);
                            string[] list_str = new string[2];
                            list_str[0] = data;
                            Console.WriteLine(data);
                            receive_data_automatically(sslStream, out data);
                            list_str[1] = data;
                            Console.WriteLine(data);
                            if (!check_existed_username(list_str[0]))
                            {
                                Int64 randomid = 0;
                                while (randomid <= 0 || check_existed_id(randomid))
                                {
                                    randomid = NextInt64(rand);
                                }
                                string id_string = randomid.ToString();
                                while (id_string.Length < 19) id_string = '0' + id_string;
                                using (SqlCommand command = new SqlCommand("insert into account values (@id, @username, @name, @pw, @state, @private, @number_of_contacts, @avatar)", sql))
                                {
                                    command.Parameters.AddWithValue("@id", id_string);
                                    command.Parameters.AddWithValue("@username", list_str[0]);
                                    command.Parameters.AddWithValue("@name", list_str[0]);
                                    command.Parameters.AddWithValue("@pw", Crypter.Blowfish.Crypt(list_str[1]));
                                    command.Parameters.AddWithValue("@state", 1);
                                    command.Parameters.AddWithValue("@private", 0);
                                    command.Parameters.AddWithValue("@number_of_contacts", 0);
                                    command.Parameters.AddWithValue("@avatar", DBNull.Value);
                                    command.ExecuteNonQuery();
                                }
                                sslStream.Write(Encoding.Unicode.GetBytes("1011")); // New account created

                            }
                            else
                            {
                                sslStream.Write(Encoding.Unicode.GetBytes("1111")); // Username exists
                            }
                        }
                        catch (Exception e)
                        {
                            Console.WriteLine(e.ToString());
                        }
                        finally
                        {
                            try
                            {
                                try
                                {
                                    sslStream.Dispose();
                                }
                                catch
                                {

                                }
                                c.Dispose();
                            } catch (Exception e)
                            {
                                Console.WriteLine();
                            }
                        }
                    } // sign up
                    else
                    {
                        try
                        {
                            try
                            {
                                sslStream.Dispose();
                            }
                            catch
                            {

                            }
                            c.Dispose();
                        } catch
                        {

                        }
                    } // release resources, dispose connection etc ..
                }
            } 
            catch (AuthenticationException e)
            {
                Console.WriteLine("Exception: {0}", e.Message);
                if (e.InnerException != null)
                {
                    Console.WriteLine("Inner exception: {0}", e.InnerException.Message);
                }
                Console.WriteLine("Authentication failed - closing the connection.");
                try
                {
                    sslStream.Dispose();
                    c.Dispose();
                } catch
                {

                }
                return;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
                try
                {
                    sslStream.Dispose();
                }
                catch
                {

                }
                try
                {
                    c.Dispose();
                }
                catch
                {

                }
            }
        }

        public static string ImageToString(string path)
        {
            if (path == null)
                throw new ArgumentNullException("path");
            Image im = Image.FromFile(path);
            MemoryStream ms = new MemoryStream();
            im.Save(ms, im.RawFormat);
            byte[] array = ms.ToArray();
            return Convert.ToBase64String(array);
        }
        public static Image StringToImage(string imageString)
        {

            if (imageString == null)
                throw new ArgumentNullException("imageString");
            byte[] array = Convert.FromBase64String(imageString);
            Image image = Image.FromStream(new MemoryStream(array));
            return image;
        }

        private static bool check_existed_username(string v)
        {
            string commandtext = "select top 1 id from account where username=@username";
            SqlCommand command = new SqlCommand(commandtext, sql);
            command.Parameters.AddWithValue("@username", v);
            using (SqlDataReader reader = command.ExecuteReader())
            {
                if (reader.Read())
                {
                    return true;
                }
                else return false;
            }
        }

        private static bool check_existed_id(long randomid)
        {
            if (randomid > 0)
            {
                string commandtext = "select top 1 id from account where id=@id";
                SqlCommand command = new SqlCommand(commandtext, sql);
                command.Parameters.AddWithValue("@id", randomid);
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        return true;
                    }
                    else return false;
                }
            }
            return true;
        }

        static void DisplaySecurityLevel(SslStream stream)
        {
            Console.WriteLine("Cipher: {0} strength {1}", stream.CipherAlgorithm, stream.CipherStrength);
            Console.WriteLine("Hash: {0} strength {1}", stream.HashAlgorithm, stream.HashStrength);
            Console.WriteLine("Key exchange: {0} strength {1}", stream.KeyExchangeAlgorithm, stream.KeyExchangeStrength);
            Console.WriteLine("Protocol: {0}", stream.SslProtocol);
        }
        static void DisplaySecurityServices(SslStream stream)
        {
            Console.WriteLine("Is authenticated: {0} as server? {1}", stream.IsAuthenticated, stream.IsServer);
            Console.WriteLine("IsSigned: {0}", stream.IsSigned);
            Console.WriteLine("Is Encrypted: {0}", stream.IsEncrypted);
        }
        static void DisplayStreamProperties(SslStream stream)
        {
            Console.WriteLine("Can read: {0}, write {1}", stream.CanRead, stream.CanWrite);
            Console.WriteLine("Can timeout: {0}", stream.CanTimeout);
        }
        static void DisplayCertificateInformation(SslStream stream)
        {
            Console.WriteLine("Certificate revocation list checked: {0}", stream.CheckCertRevocationStatus);

            X509Certificate localCertificate = stream.LocalCertificate;
            if (stream.LocalCertificate != null)
            {
                Console.WriteLine("Local cert was issued to {0} and is valid from {1} until {2}.",
                    localCertificate.Subject,
                    localCertificate.GetEffectiveDateString(),
                    localCertificate.GetExpirationDateString());
            }
            else
            {
                Console.WriteLine("Local certificate is null.");
            }
            // Display the properties of the client's certificate.
            X509Certificate remoteCertificate = stream.RemoteCertificate;
            if (stream.RemoteCertificate != null)
            {
                Console.WriteLine("Remote cert was issued to {0} and is valid from {1} until {2}.",
                    remoteCertificate.Subject,
                    remoteCertificate.GetEffectiveDateString(),
                    remoteCertificate.GetExpirationDateString());
            }
            else
            {
                Console.WriteLine("Remote certificate is null.");
            }
        }
    }
}
