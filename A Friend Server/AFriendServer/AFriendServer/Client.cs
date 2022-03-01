using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Security;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Collections.Concurrent;
using System.Threading;

namespace AFriendServer
{
    internal class Client
    {
        internal TcpClient client;
        internal SslStream stream;
        internal string id;
        internal int is_locked;
        internal int is_waited;
        internal int loaded;
        internal int loopnum;
        internal ConcurrentQueue<byte[]> commands;
        internal ConcurrentDictionary<string, bool> files_on_transfer = new ConcurrentDictionary<string, bool>();
        private Int32 workeradded;

        internal byte status = 1;
        
        internal Client() 
        {
            commands = new ConcurrentQueue<byte[]>();
            workeradded = 0;
            is_waited = 0;
        }

        internal void Queue_command(byte[] command)
        {
            commands.Enqueue(command);
            Add_worker_thread();
        }

        private void Add_worker_thread()
        {
            if (0 == Interlocked.Exchange(ref workeradded, 1))
            {
                ThreadPool.QueueUserWorkItem(Send_commands);
            }
        }

        private void Send_commands(object state)
        {
            try
            {
                while (commands.TryDequeue(out byte[] command))
                {
                    stream.Write(command);
                }
            }
            catch (Exception se)
            {
                //Console.WriteLine(se.ToString());
                Program.shutdown(id);
            }
            finally
            {
                try
                {
                    Interlocked.Exchange(ref workeradded, 0);
                } catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
        }
    }
}
