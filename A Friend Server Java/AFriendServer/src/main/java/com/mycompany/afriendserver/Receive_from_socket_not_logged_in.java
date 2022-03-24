package com.mycompany.afriendserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Base64;

import javax.net.ssl.SSLSocket;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Receive_from_socket_not_logged_in implements Runnable {
    SSLSocket client;

    public Receive_from_socket_not_logged_in(SSLSocket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream DIS = null;
        DataOutputStream DOS = null;
        try {
            // create streams from client
            DIS = new DataInputStream(client.getInputStream());
            DOS = new DataOutputStream(client.getOutputStream());
            System.out.println(2);
            String data = Tools.receive_unicode(DIS, 8);
            System.out.println(3);
            if (data != null && !data.isEmpty()) {
                String instruction = data;
                System.out.println(instruction);
                if (instruction.equals("0012")) {
                    data = Tools.receive_ASCII(DIS, 19);
                    try{
                        DIS.close();
                    } catch (Exception e) {}
                    try{
                        DOS.close();
                    } catch (Exception e) {}
                    try{
                        client.close();
                    } catch (Exception e) {}
                    System.out.println("All closed, ID = " + data);
                    boolean pass = false;
                    if (Program.sessions.containsKey(data) && Program.sessions.get(data).is_waited.getAndSet(1) == 1) {
                        pass = true;
                    }
                    int h = 0;
                    while (!pass && h++ < 20 && !Program.sessions.containsKey(data)) {
                        /*
                        synchronized (this) {
                            this.wait(1000);
                        }*/
                        Thread.sleep(1000);
                        System.out.println("pausing 1");
                    }
                    System.out.println("pass pause 1");
                    if (!pass && Program.sessions.containsKey(data)){
                        try{
                            boolean do_work = false;
                            while(Program.sessions.get(data).is_locked.getAndSet(1) == 1){
                                /*
                                synchronized (this){
                                    this.wait(1000);
                                }*/
                                Thread.sleep(1000);
                                System.out.println("pausing 2");
                            }
                            System.out.println("pass pause 2");
                            if (Program.sessions.get(data).client.isConnected()){
                                if (Program.sessions.get(data).client.getInputStream().available() > 0) {
                                    try{
                                        do_work = true;
                                        // ThreadPool.QueueUserWorkItem(Receive_message, data);
                                        System.out.println("Before executor: " + data);
                                        Program.executor.execute(new Receive_message(data));
                                    } catch (Exception e){
                                        Program.handleException(data, e.toString());
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                Program.shutdown(data);
                            }
                            if (!do_work) Program.sessions.get(data).is_locked.set(0);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    System.out.println("quit ping");
                }
                else if (instruction.equals("0010")) {
                    System.out.println(4);
                    String[] lst_str = new String[2];
                    lst_str[0] = Tools.receive_Unicode_Automatically(DIS);
                    lst_str[1] = Tools.receive_Unicode_Automatically(DIS);
                    // print lst_str
                    System.out.println(lst_str[0]);
                    System.out.println(lst_str[1]);
                    try (PreparedStatement cmd = Program.sql.prepareStatement("select top 1 id, name, pw, avatar, private, state from account where username=?");){
                        cmd.setString(1, lst_str[0]);
                        try (ResultSet rs = cmd.executeQuery()){
                            if (rs.next()){
                                if (BCrypt.checkpw(lst_str[1], rs.getString("pw"))){
                                    String id = rs.getString("id");
                                    String str_id = new String(id);
                                    while(id.length() < 19){
                                        id = "0" + id;
                                    }
                                    String name = rs.getString("name");
                                    String namebyte = Integer.toString(name.getBytes(StandardCharsets.UTF_16LE).length);
                                    byte mystate = rs.getByte("state");
                                    String namebytelen = Integer.toString(namebyte.length());
                                    while(namebytelen.length() < 2){
                                        namebytelen = "0" + namebytelen;
                                    }
                                    String priv = Boolean.toString(rs.getBoolean("private"));
                                    while (priv.length() < 5){
                                        priv = priv + " ";
                                    }
                                    System.out.println(priv);
                                    DOS.write(("0200" + id + namebytelen + namebyte + name + priv).getBytes(StandardCharsets.UTF_16LE));
                                    System.out.println("Before dictionaries");
                                    try{
                                        if (Program.sessions.containsKey(id)){
                                            try{
                                            Program.sessions.get(id).is_locked.set(1);
                                            Program.sessions.get(id).stream.write("2004".getBytes(StandardCharsets.UTF_16LE));
                                            System.out.println("User logged in from another device");
                                            } 
                                            catch (Exception iknow){
                                            }
                                            finally {
                                                Program.shutdown(id);
                                            }
                                        }
                                        Client client = new Client();
                                        client.loaded = 0;
                                        client.loopnum = 0;

                                        System.out.println("got id");

                                        long id_int = rs.getLong("id");
                                        try(PreparedStatement friendCommand = Program.sql.prepareStatement("select id1, id2 from friend where id1=? or id2=?")){
                                            friendCommand.setLong(1, id_int);
                                            friendCommand.setLong(2, id_int);
                                            try(ResultSet friendResult = friendCommand.executeQuery()){
                                                while(friendResult.next()){
                                                    client.loaded += 1;
                                                    long friendid = friendResult.getLong("id1");
                                                    if (friendid == id_int){
                                                        friendid = friendResult.getLong("id2");
                                                    }

                                                    try(PreparedStatement friendCommand2 = Program.sql.prepareStatement("select top 1 id, name from account where id=?")){
                                                        friendCommand2.setLong(1, friendid);
                                                        try(ResultSet friendResult2 = friendCommand2.executeQuery()){
                                                            if (friendResult2.next()){
                                                                int state = Program.sessions.containsKey(friendResult2.getString("id")) ? 1 : 0;
                                                                String datasend = Tools.padleft(friendResult2.getString("id"), 19, '0') + " " + friendResult2.getString("id") + " " + friendResult2.getString("name") + " " + state;
                                                                DOS.write(
                                                                        Tools.combine(
                                                                            "1609".getBytes(StandardCharsets.UTF_16LE),
                                                                            Tools.data_with_unicode_byte(datasend).getBytes(StandardCharsets.UTF_16LE)
                                                                        )
                                                                    );
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (client.loaded == 0){
                                            DOS.write("2411".getBytes(StandardCharsets.UTF_16LE));
                                        }
                                        String avt = Program.avatar_path + id + ".png";
                                        try{
                                            // check if file at path avt exists
                                            File file = new File(avt);
                                            // if file exists, read file in BASE64 encoding
                                            if (file.exists()){
                                                String encoded = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(avt)));
                                                // combine the string "0601" as unicode and the BASE64 encoded string as ASCII into 1 byte array
                                                DOS.write(Tools.combine("0601".getBytes(StandardCharsets.UTF_16LE), (Tools.data_with_ASCII_byte(encoded)).getBytes(StandardCharsets.US_ASCII)));
                                            }
                                            
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        while(Program.sessions.containsKey(id)){
                                            this.wait(1000);
                                        }
                                        client.client = this.client;
                                        client.stream = DOS;
                                        client.DIS = DIS;
                                        client.is_locked.set(0);
                                        client.id = id;
                                        client.status = mystate;
                                        Program.sessions.put(id, client);
                                        client.stream.write(("7351" + Byte.toString(mystate)).getBytes(StandardCharsets.UTF_16LE));
                                        
                                        System.out.println("User joined");
                                    } 
                                    catch (Exception e){
                                        e.printStackTrace();
                                        Program.shutdown(str_id);
                                        try{
                                            DIS.close();
                                        } catch (Exception e3){
                                        }
                                        try{
                                            DOS.close();
                                        } catch (Exception e2){
                                        }
                                        try{
                                            client.close();
                                        } catch (Exception e1){
                                        }
                                    } 
                                    client = null;
                                    DIS = null;
                                    DOS = null;
                                }
                                else { // wrong password
                                    try{
                                        try{
                                            DOS.write("-200".getBytes(StandardCharsets.UTF_16LE));
                                        } catch (Exception e){
                                        }
                                        try{
                                            DIS.close();
                                        } catch (Exception e){
                                        }
                                        try{
                                            DOS.close();
                                        } catch (Exception e){
                                        }
                                        try{
                                            client.close();
                                        } catch (Exception e){
                                        }
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                } // wrong password
                            } 
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                        try{
                            DIS.close();
                        } catch (Exception e1){
                        }
                        try{
                            DOS.close();
                        } catch (Exception e2){
                        }
                        try{
                            client.close();
                        } catch (Exception e3){
                        }
                    }
                } else if (instruction.equals("0011")){
                    try{
                        String[] lst_str = new String[2];
                        lst_str[0] = Tools.receive_Unicode_Automatically(DIS);;
                        System.out.println(lst_str[0]);
                        lst_str[1] = Tools.receive_Unicode_Automatically(DIS);
                        System.out.println(lst_str[1]);
                        if (!Program.check_existed_username(lst_str[0])){
                            long randomid = 0;
                            while (randomid <=0 || Program.check_existed_id(randomid)){
                                randomid = Program.rand.nextLong();
                            }
                            String id_string = Long.toString(randomid);
                            while (id_string.length() < 19){
                                id_string = "0" + id_string;
                            }
                            // insert into account values (id, username, name, pw, state, private, number_of_contacts, avatar)
                            try(PreparedStatement command = Program.sql.prepareStatement("insert into account values (?, ?, ?, ?, ?, ?, ?, ?)")){
                                command.setLong(1, randomid);
                                command.setString(2, lst_str[0]);
                                command.setString(3, lst_str[0]);
                                // setstring 4 to BCrypt hash password
                                command.setString(4, BCrypt.hashpw(lst_str[1], BCrypt.gensalt()));
                                command.setByte(5, (byte)1);
                                command.setBoolean(6, false);
                                command.setInt(7, 0);
                                command.setNull(8, Types.VARCHAR);
                                command.executeUpdate();
                            }
                            DOS.write("1011".getBytes(StandardCharsets.UTF_16LE));
                        } else {
                            DOS.write("1111".getBytes(StandardCharsets.UTF_16LE));
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        // close all possible resource with try catch
                        try{
                            try{
                                DIS.close();
                            } catch (Exception e){
                            }
                            try{
                                DOS.close();
                            } catch (Exception e){
                            }
                            try{
                                client.close();
                            } catch (Exception e){
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // close all possible resouces with try catch
            try {
                DIS.close();
            } catch (Exception e1) {
            }
            try {
                DOS.close();
            } catch (Exception e1) {
            }
            try {
                client.close();
            } catch (Exception e1) {
            }
        }
    }

}
