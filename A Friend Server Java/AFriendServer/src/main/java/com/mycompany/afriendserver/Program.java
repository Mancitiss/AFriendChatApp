package com.mycompany.afriendserver;

import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


/**
 *
 * @author Mancitiss
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static String avatar_path = "F:\\App\\AFriendServer\\avatar";
    public static String img_path = "F:\\App\\AFriendServer\\message";
    static ExecutorService executor = Executors.newCachedThreadPool();

    static ConcurrentHashMap<String, Client> sessions = new ConcurrentHashMap<String, Client>();

    static Connection sql;
    static String cnurl;

    // object that can random long number
    static java.util.Random rand = new java.util.Random();

    public static void main(String[] args) {
        try {
            String connectionUrl = "jdbc:sqlserver://" + System.getenv("DBServer") + ";"
                    + "database=" + System.getenv("DBicatalog") + ";"
                    + "user=" + System.getenv("DBusername") + ";"
                    + "password=" + System.getenv("DBpassword") + ";"
                    + "loginTimeout=10;";
            cnurl = connectionUrl;
            System.setProperty("javax.net.ssl.keyStore", "F:/Python Learning/web_cert2022/server.p12");
            System.setProperty("javax.net.ssl.keyStorePassword", "RoRo");
            try (Connection sqlr = DriverManager.getConnection(connectionUrl)) {
                sql = sqlr;
                ExecuteServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            out.println("Error: " + e.toString());
        }
    }

    private static void ExecuteServer() throws IOException {
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try (SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(11112)) {
            // translate below line of code from C#
            // Console.WriteLine("Server at: {0}", IPAddress.Any);
            out.println("Server at: " + ss.getInetAddress());
            try {
                while (true) {
                    SSLSocket client = (SSLSocket) ss.accept();
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
        System.out.println("{0} has quit" + id);
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

    public static void handleException(String data, String se) {
        try {
            if (se.contains("open and available Connection")) {
                sql = DriverManager.getConnection(cnurl);
            } else if (se.contains("Execution Timeout Expired")) {
                sql = DriverManager.getConnection(cnurl);
            } else if (se.contains("was forcibly closed")) {
                shutdown(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean check_existed_username(String username) {
        try (PreparedStatement cmd = sql.prepareStatement("select 1 from account where username= ?");) {
            cmd.setString(1, username);
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
}
