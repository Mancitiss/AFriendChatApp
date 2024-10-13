package mancie.mancitiss.afriendserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.google.gson.Gson;

/**
 *
 * @author Mancitiss
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static String avatar_path = "D:\\App\\AFriendServer\\avatar\\";
    public static String img_path = "D:\\App\\AFriendServer\\message\\";
    static ExecutorService executor = Executors.newCachedThreadPool();
    static Calendar tzCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

    public static Gson gson = new Gson();

    static ConcurrentHashMap<String, Client> sessions = new ConcurrentHashMap<String, Client>();
    static ConcurrentHashMap<String, FileToWrite> files = new ConcurrentHashMap<String, FileToWrite>();
    static ConcurrentHashMap<String, OTP> otps = new ConcurrentHashMap<String, OTP>();

    static Connection sql;
    static String cnurl;

    // object that can random long number
    static java.util.SplittableRandom rand = new java.util.SplittableRandom();
    static java.security.SecureRandom secure_rand = new java.security.SecureRandom();

    public static void main(String[] args) {
        try {
            String connectionUrl = "jdbc:sqlserver://" + System.getenv("DBServer") + ";"
                    + "databaseName=" + System.getenv("DBicatalog") + ";"
                    + "user=" + System.getenv("DBusername") + ";"
                    + "password=" + System.getenv("DBpassword") + ";"
                    + "loginTimeout=10;encrypt=true;trustServerCertificate=true";
            cnurl = connectionUrl;
            out.println(connectionUrl);
            System.setProperty("javax.net.ssl.keyStore", System.getenv("certpath"));
            System.setProperty("javax.net.ssl.keyStorePassword", System.getenv("certpass"));
            try (Connection sqlr = DriverManager.getConnection(connectionUrl)) {
                sql = sqlr;
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                // Schedule a task to run every 30 minutes
                Runnable task = () -> {
                    // check for expired OTPs and delete them
                    try {
                        for (String key : otps.keySet()) {
                            if (otps.get(key).time < System.currentTimeMillis()) {
                                otps.remove(key);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                // Initial delay of 0, and then repeat every 30 minutes
                executor.scheduleAtFixedRate(task, 0, 30, TimeUnit.MINUTES);

                ExecuteServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            out.println("Error: " + e.toString());
            // print error at which line
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                out.println(stackTraceElement.toString());
            }
        }
    }

    private static void ExecuteServer() throws IOException {
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try (SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(11111)) {
            // translate below line of code from C#
            // Console.WriteLine("Server at: {0}", IPAddress.Any);
            out.println("Server at: " + ss.getInetAddress());
            try {
                while (true) {
                    SSLSocket client = (SSLSocket) ss.accept();
                    System.out.println("Client connected: " + client.getInetAddress());
                    try {
                        // translate below line of code from C#
                        // ThreadPool.QueueUserWorkItem(Receive_from_socket_not_logged_in, client);
                        executor.execute(new Receive_from_socket_not_logged_in(client));
                    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    client = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void shutdown(String id) {
        
        try {
            sessions.get(id).stream.close();
        } catch (Exception e) {

        }
        try {
            sessions.get(id).DIS.close();
        } catch (Exception e) {

        }
        try {
            sessions.get(id).client.close();
        } catch (Exception e) {

        }
        try {
            byte state = sessions.get(id).status;
            sessions.remove(id);
            System.out.println(id + " quitted");
            String str_id = id;
            while (str_id.toCharArray()[0] == '0' && str_id.length() > 1) {
                str_id = str_id.substring(1);
            }
            try (PreparedStatement cmd = sql.prepareStatement("update top (1) account set state= ? where id= ?");) {
                cmd.setLong(2, Long.parseLong(str_id));
                cmd.setByte(1, state);
                cmd.executeUpdate();
                // "update top (1) account set state=@state where id=@id"
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void handleException(String ID, String se) {
        try {
            if (se.contains("open and available Connection")) {
                sql = DriverManager.getConnection(cnurl);
            } else if (se.contains("Execution Timeout Expired")) {
                sql = DriverManager.getConnection(cnurl);
            } else if (se.contains("was forcibly closed")) {
                shutdown(ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean check_existed_username(String username) {
        try (PreparedStatement cmd = sql.prepareStatement("select 1 from account where username= ?");) {
            cmd.setNString(1, username);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean check_existed_id(long id) {
        // also return true if id <= 0
        if (id <= 0) {
            return true;
        }
        try (PreparedStatement cmd = sql.prepareStatement("select 1 from account where id= ?");) {
            cmd.setLong(1, id);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendToID(String iD, MessageObject msgobj) {
        boolean success = false;
        if (sessions.containsKey(iD)){
            try{
                sessions.get(iD).Queue_command(("1901" + Tools.data_with_unicode_byte(gson.toJson(msgobj))).getBytes(StandardCharsets.UTF_16LE));
                success = true;
            } catch (Exception e){
                if (e.getMessage().contains("was forcibly closed")){
                    shutdown(iD);
                }
                else e.printStackTrace();
                success = false;
            }
        }
        return success;
    }
}
