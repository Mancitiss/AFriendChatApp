package com.mycompany.afriendserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
                        case "0708": {
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
                                Program.sessions.get(ID)
                                        .Queue_command(("0708" + receiver_id + "1").getBytes(StandardCharsets.UTF_16));
                            } else {
                                Program.sessions.get(ID)
                                        .Queue_command(("0708" + receiver_id + "0").getBytes(StandardCharsets.UTF_16));
                            }
                        }
                            break;
                        case "1234": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            String boolstr = Tools.receive_unicode(s, 2);
                            try (PreparedStatement ps = Program.sql.prepareStatement(
                                    "update top (1) seen set seen=@bool where id1=@id1 and id2=@id2")) {
                                if (boolstr.equals("0")) {
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
                        case "6475": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            data = Tools.receive_Unicode_Automatically(s);
                            long num = Long.parseLong(data);
                            if (num == 0) {
                                try (PreparedStatement ps = Program.sql.prepareStatement(
                                        "select top 1 count from friend where id1=@id1 and id2=@id2")) {
                                    ps.setString(1, p[0]);
                                    ps.setString(2, p[1]);
                                    try (java.sql.ResultSet rs = ps.executeQuery()) {
                                        if (rs.next()) {
                                            num = rs.getLong(1);
                                        }
                                    }
                                    int i = 0;
                                    ArrayList<MessageObject> messages = new ArrayList<MessageObject>();
                                    while (num > 0 && i < 20) {
                                        try (PreparedStatement ps1 = Program.sql.prepareStatement(
                                                "select top 1 * from message where id1=@id1 and id2=@id2 and messagenumber=@messagenumber")) {
                                            ps1.setString(1, p[0]);
                                            ps1.setString(2, p[1]);
                                            ps1.setLong(3, num);
                                            try (java.sql.ResultSet rs1 = ps1.executeQuery()) {
                                                if (rs1.next()) {
                                                    if (rs1.getByte("type") == 0 || rs1.getByte("type") == 3) {
                                                        messages.add(new MessageObject(rs1.getString("id1"),
                                                                rs1.getString("id2"), rs1.getLong("messagenumber"),
                                                                rs1.getTimestamp("timesent"), rs1.getBoolean("sender"),
                                                                rs1.getString("message"), rs1.getByte("type")));
                                                    } else if (rs1.getByte("type") == 1 && (new File(
                                                            Program.img_path + p[0] + "_" + p[1] + num + ".png"))
                                                            .exists()) {
                                                        messages.add(
                                                                new MessageObject(rs1.getString("id1"),
                                                                        rs1.getString("id2"),
                                                                        rs1.getLong("messagenumber"),
                                                                        rs1.getTimestamp("timesent"),
                                                                        rs1.getBoolean("sender"),
                                                                        Tools.ImageToBASE64(Program.img_path + p[0]
                                                                                + "_" + p[1] + "_" + num + ".png"),
                                                                        rs1.getByte("type")));
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
                                    // send to id
                                    Program.sessions.get(ID)
                                            .Queue_command(("6475" + receiver_id + Tools.data_with_unicode_byte(json))
                                                    .getBytes(StandardCharsets.UTF_16));
                                    // old messages sent
                                    System.out.println("Old messages sent");
                                    if (Program.sessions.containsKey(ID)) {
                                        if (Program.sessions.get(ID).loaded > 1) {
                                            Program.sessions.get(ID).loaded -= 1;
                                        } else if (Program.sessions.get(ID).loaded == 1) {
                                            Program.sessions.get(ID)
                                                    .Queue_command("2411".getBytes(StandardCharsets.UTF_16));
                                            Program.sessions.get(ID).loaded -= 1;
                                        }
                                    }
                                }
                            } else if (num > 1) {
                                int i = 0;
                                ArrayList<MessageObject> messages = new ArrayList<MessageObject>();
                                while (num > 0 && i < 20) {
                                    try (PreparedStatement ps = Program.sql.prepareStatement(
                                            "select top 1 * from message where id1=@id1 and id2=@id2 and messagenumber=@messagenumber")) {
                                        ps.setString(1, p[0]);
                                        ps.setString(2, p[1]);
                                        ps.setLong(3, num);
                                        try (java.sql.ResultSet rs = ps.executeQuery()) {
                                            if (rs.next()) {
                                                if (rs.getByte("type") == 0 || rs.getByte("type") == 3) {
                                                    messages.add(new MessageObject(rs.getString("id1"),
                                                            rs.getString("id2"), rs.getLong("messagenumber"),
                                                            rs.getTimestamp("timesent"), rs.getBoolean("sender"),
                                                            rs.getString("message"), rs.getByte("type")));
                                                } else if (rs.getByte("type") == 1 && (new File(
                                                        Program.img_path + p[0] + "_" + p[1] + num + ".png"))
                                                        .exists()) {
                                                    messages.add(
                                                            new MessageObject(rs.getString("id1"), rs.getString("id2"),
                                                                    rs.getLong("messagenumber"),
                                                                    rs.getTimestamp("timesent"),
                                                                    rs.getBoolean("sender"),
                                                                    Tools.ImageToBASE64(Program.img_path + p[0] + "_"
                                                                            + p[1] + "_" + num + ".png"),
                                                                    rs.getByte("type")));
                                                }
                                            }
                                        }
                                        num -= 1;
                                        i += 1;
                                    }
                                }
                                // JSON serialize messages
                                String json = Program.gson.toJson(messages);
                                // send to id
                                Program.sessions.get(ID)
                                        .Queue_command(("6475" + receiver_id + Tools.data_with_unicode_byte(json))
                                                .getBytes(StandardCharsets.UTF_16));
                                System.out.println("Old messages sent");
                            }
                        }
                            break;
                        case "1901": {
                            data = Tools.receive_Unicode_Automatically(s);
                            String receiver_id = data.substring(0, 19);
                            data = data.substring(19);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            String sqlmessage = data;
                            try {
                                boolean success = false;
                                Timestamp now = new Timestamp(System.currentTimeMillis());
                                try (PreparedStatement ps = Program.sql.prepareStatement(
                                        "insert into message (id1, id2, messagenumber, timesent, sender, message, type) values (?, ?, ?, ?, ?, ?, ?)")) {
                                    ps.setString(1, p[0]);
                                    ps.setString(2, p[1]);
                                    // get a random negative number between -1000000000 and 0 (inclusive)
                                    ps.setLong(3, -Program.rand.nextInt(1000000000));
                                    ps.setTimestamp(4, now);
                                    ps.setBoolean(5, true);
                                    ps.setString(6, sqlmessage);
                                    ps.setByte(7, (byte) 0);
                                    if (ps.executeUpdate() >= 1) {
                                        success = true;
                                    }
                                }
                                if (success) {
                                    try (PreparedStatement another_ps = Program.sql.prepareStatement(
                                            "select top 1 * from message where id1=? and id2=? and timesent=? and sender=?")) {
                                        another_ps.setString(1, p[0]);
                                        another_ps.setString(2, p[1]);
                                        another_ps.setTimestamp(3, now);
                                        another_ps.setBoolean(4, ID.equals(p[1]));
                                        try (ResultSet rs = another_ps.executeQuery()) {
                                            if (rs.next()) {
                                                MessageObject msgobj = new MessageObject(
                                                        Tools.padleft(rs.getString("id1"), 19, '0'),
                                                        Tools.padleft(rs.getString("id2"), 19, '0'),
                                                        rs.getLong("messagenumber"), rs.getTimestamp("timesent"),
                                                        rs.getBoolean("sender"), rs.getString("message"),
                                                        rs.getByte("type"));
                                                if (!ID.equals(receiver_id))
                                                    Program.sendToID(ID, msgobj);
                                                if (!Program.sendToID(receiver_id, msgobj)) {
                                                    Program.sessions.get(ID)
                                                            .Queue_command("0404".getBytes(StandardCharsets.UTF_16));
                                                } else {
                                                    Program.sessions.get(ID)
                                                            .Queue_command("2211".getBytes(StandardCharsets.UTF_16));
                                                }
                                                System.out.println("Sent");
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Program.handleException(ID, e.getMessage());
                            }
                        }
                            break;
                        case "1902": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            data = Tools.receive_Unicode_Automatically(s);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            try {
                                boolean success = false;
                                Timestamp now = new Timestamp(System.currentTimeMillis());
                                try (PreparedStatement ps = Program.sql.prepareStatement(
                                        "insert into message (id1, id2, messagenumber, timesent, sender, message, type) values (?, ?, ?, ?, ?, ?, ?)")) {
                                    ps.setString(1, p[0]);
                                    ps.setString(2, p[1]);
                                    // get a random negative number between -1000000000 and 0 (inclusive)
                                    ps.setLong(3, -Program.rand.nextInt(1000000000));
                                    ps.setTimestamp(4, now);
                                    ps.setBoolean(5, true);
                                    ps.setString(6, "");
                                    ps.setByte(7, (byte) 1);
                                    if (ps.executeUpdate() >= 1) {
                                        success = true;
                                    }
                                }
                                if (success) {
                                    try (PreparedStatement another_ps = Program.sql.prepareStatement(
                                            "select top 1 * from message where id1=? and id2=? and timesent=? and sender=?")) {
                                        another_ps.setString(1, p[0]);
                                        another_ps.setString(2, p[1]);
                                        another_ps.setTimestamp(3, now);
                                        another_ps.setBoolean(4, ID.equals(p[1]));
                                        try (ResultSet rs = another_ps.executeQuery()) {
                                            if (rs.next()) {
                                                try {
                                                    String img_message = data;
                                                    File temp = new File(Program.img_path + p[0] + "_" + p[1] + "_"
                                                            + rs.getLong("messagenumber") + ".png");
                                                    try(BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(temp))) {
                                                        fos.write(java.util.Base64.getDecoder().decode(img_message));
                                                    }
                                                    MessageObject msgobj = new MessageObject(
                                                            Tools.padleft(rs.getString("id1"), 19, '0'),
                                                            Tools.padleft(rs.getString("id2"), 19, '0'),
                                                            rs.getLong("messagenumber"), rs.getTimestamp("timesent"),
                                                            rs.getBoolean("sender"), img_message,
                                                            rs.getByte("type"));
                                                    if (!ID.equals(receiver_id))
                                                        Program.sendToID(ID, msgobj);
                                                    if (!Program.sendToID(receiver_id, msgobj)) {
                                                        Program.sessions.get(ID).Queue_command(
                                                                "0404".getBytes(StandardCharsets.UTF_16));
                                                    } else {
                                                        Program.sessions.get(ID).Queue_command(
                                                                "2211".getBytes(StandardCharsets.UTF_16));
                                                    }
                                                    System.out.println("Sent");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Program.handleException(ID, e.getMessage());
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
