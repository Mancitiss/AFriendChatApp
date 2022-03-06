package com.mycompany.afriendserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author Mancitiss
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    private static String avatar_path = "F:\\App\\AFriendServer\\avatar";
    private static String img_path = "F:\\App\\AFriendServer\\message";
    static ExecutorService executor = Executors.newCachedThreadPool();

    static ConcurrentHashMap<String, Client> sessions = new ConcurrentHashMap<String, Client>();

    static Connection sql;

    public static void main(String[] args) {
        // TODO code application logic here
        try {
            String connectionUrl
                    = "jdbc:sqlserver://" + System.getenv("DBServer") + ";"
                    + "database=" + System.getenv("DBicatalog") + ";"
                    + "user=" + System.getenv("DBusername") + ";"
                    + "password=" + System.getenv("DBpassword") + ";"
                    + "loginTimeout=10;";

            System.setProperty("javax.net.ssl.keyStore", "F:/Python Learning/web_cert/server.p12");
            System.setProperty("javax.net.ssl.keyStorePassword", "RoRo");
            try ( Connection sqlr = DriverManager.getConnection(connectionUrl)) {
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
        SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(11112);
        SSLSocket client = (SSLSocket) ss.accept();
        out.println("Accepted Client!");
        DataOutputStream DOS = new DataOutputStream(client.getOutputStream());
        DataInputStream DIS = new DataInputStream(client.getInputStream());
        while (true) {
            String receivedMessage = DIS.readUTF();
            out.println("Client said: " + receivedMessage);
            if (receivedMessage.equals("close")) {
                DOS.writeUTF("bye");
                break;
            } else {
                DOS.writeUTF("U said: " + receivedMessage);
            }
        }
        DOS.close();
        DIS.close();
        client.close();
        ss.close();
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
        byte state = sessions.get(id).status;
        Client temp = sessions.remove(id);
        String str_id = id;
        while (str_id.toCharArray()[0] == '0' && str_id.length() > 1) {
            str_id = str_id.substring(1);
        }
        try ( PreparedStatement cmd = sql.prepareStatement("update top (1) account set state= ? where id= ?");) {
            cmd.setLong(2, Long.parseLong(str_id));
            cmd.setByte(1, state);
            cmd.executeUpdate();
            //"update top (1) account set state=@state where id=@id"
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
