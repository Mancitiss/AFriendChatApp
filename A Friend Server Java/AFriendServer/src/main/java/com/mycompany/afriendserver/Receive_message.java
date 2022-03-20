package com.mycompany.afriendserver;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Receive_message implements Runnable {
    String ID;

    public Receive_message(String data) {
        this.ID = data;
    }

    @Override
    public void run() {
        try {
            java.io.DataInputStream s = Program.sessions.get(ID).DIS;
            String data;
            do {
                data = Tools.receive_unicode(s, 8);
                if (data != null && !data.isBlank()) {
                    // if (data!=null && data!="1904") Console.WriteLine("Work: " + data);
                    if (!data.equals("1904"))
                        System.out.println("Work: " + data);
                    String instruction = data;
                    switch (instruction) {
                        case "0708": 
                        {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            boolean result = false;
                            try (PreparedStatement ps = Program.sql
                                    .prepareStatement("select top 1 seen from seen where id1=@id1 and id2=@id2")) {
                                ps.setString(1, p[0]);
                                ps.setString(2, p[1]);
                                try (java.sql.ResultSet rs = ps.executeQuery()) {
                                    if (rs.next()) {
                                        result = rs.getBoolean(1);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (result) {
                                Program.sessions.get(ID).Queue_command(("0708" + receiver_id + "1").getBytes(StandardCharsets.UTF_16));
                            } else {
                                Program.sessions.get(ID).Queue_command(("0708" + receiver_id + "0").getBytes(StandardCharsets.UTF_16));
                            }
                        }
                        break;
                        case "1234":
                        {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            String boolstr = Tools.receive_unicode(s, 2);
                            try(PreparedStatement ps = Program.sql.prepareStatement("update top (1) seen set seen=@bool where id1=@id1 and id2=@id2")){
                                if (boolstr.equals("0")){
                                    ps.setBoolean(1, false);
                                } else {
                                    ps.setBoolean(1, true);
                                }
                                ps.setString(2, p[0]);
                                ps.setString(3, p[1]);
                                ps.executeUpdate();
                            }
                        }
                        break;
                        case "6475":
                        {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            data = Tools.receive_Unicode_Automatically(s);
                            long num = Long.parseLong(data);
                            if (num == 0){
                                try(PreparedStatement ps = Program.sql.prepareStatement("select top 1 count from friend where id1=@id1 and id2=@id2")){
                                    ps.setString(1, p[0]);
                                    ps.setString(2, p[1]);
                                    try(java.sql.ResultSet rs = ps.executeQuery()){
                                        if (rs.next()){
                                            num = rs.getLong(1);
                                        }
                                    }
                                    int i = 0;
                                    ArrayList<MessageObject> messages = new ArrayList<MessageObject>();
                                    while(num > 0 && i < 20){
                                        try(PreparedStatement ps1 = Program.sql.prepareStatement("select top 1 * from message where id1=@id1 and id2=@id2 and messagenumber=@messagenumber")){
                                            ps1.setString(1, p[0]);
                                            ps1.setString(2, p[1]);
                                            ps1.setLong(3, num);
                                            try(java.sql.ResultSet rs1 = ps1.executeQuery()){
                                                if (rs1.next()){
                                                    if (rs1.getByte("type") == 0 || rs1.getByte("type") == 3){
                                                        messages.add(new MessageObject(rs1.getString("id1"), rs1.getString("id2"), rs1.getLong("messagenumber"), new java.util.Date((rs1.getTimestamp("timesent").getTime())), rs1.getBoolean("sender"), rs1.getString("message"), rs1.getByte("type")));
                                                    } else if (rs1.getByte("type") == 1 && (new File(Program.img_path + p[0] + "_" + p[1] + num + ".png")).exists()){
                                                        messages.add(new MessageObject(rs1.getString("id1"), rs1.getString("id2"), rs1.getLong("messagenumber"), new java.util.Date((rs1.getTimestamp("timesent").getTime())), rs1.getBoolean("sender"), Tools.ImageToBASE64(Program.img_path + p[0] + "_" + p[1] + "_" + num + ".png"), rs1.getByte("type")));
                                                    }
                                                }
                                            }
                                            num -= 1;
                                            i += 1;
                                        }
                                    }
                                    // JSON serialize messages
                                    Gson gson = new Gson();
                                    String json = gson.toJson(messages);
                                    // continue here
                                }
                            } else {

                            }
                        }
                        break;

                    }
                } else {
                    Program.shutdown(ID);
                    System.out.println("Received strange signal, shutting down (2)");
                }
            } while (s.available() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Work quitted");
            try {
                Program.handleException(this.ID, e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (Program.sessions.containsKey(this.ID)) {
                    Program.sessions.get(this.ID).is_locked.set(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
