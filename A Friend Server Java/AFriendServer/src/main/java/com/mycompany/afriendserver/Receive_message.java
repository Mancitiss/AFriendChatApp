package com.mycompany.afriendserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;

import com.google.gson.Gson;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Receive_message implements Runnable {
    String ID;

    public Receive_message(String data) {
        this.ID = data;
    }

    @Override
    public void run() {
        try {
            java.io.PushbackInputStream s = new java.io.PushbackInputStream(Program.sessions.get(ID).DIS);
            String data;
            boolean keepreading = true;
            do {
                data = Tools.receive_unicode(s, 8);

                if (data != null && !data.isEmpty()) {
                    if (data!=null && data!="1904") System.out.println("Work: " + data);
                    // if (!data.equals("1904"))

                    String instruction = data;
                    switch (instruction) {
                        case "0708": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            boolean result = false;
                            try (PreparedStatement ps = Program.sql
                                    .prepareStatement("select top 1 seen from seen where id1=? and id2=?")) {
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
                                        .Queue_command(
                                                ("0708" + receiver_id + "1").getBytes(StandardCharsets.UTF_16LE));
                            } else {
                                Program.sessions.get(ID)
                                        .Queue_command(
                                                ("0708" + receiver_id + "0").getBytes(StandardCharsets.UTF_16LE));
                            }
                        }
                            break;
                        case "1234": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            String boolstr = Tools.receive_unicode(s, 2);
                            try (PreparedStatement ps = Program.sql.prepareStatement(
                                    "update top (1) seen set seen=? where id1=? and id2=?")) {
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
                                        "select top 1 count from friend where id1=? and id2=?")) {
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
                                                "select top 1 * from message where id1=? and id2=? and messagenumber=?")) {
                                            ps1.setString(1, p[0]);
                                            ps1.setString(2, p[1]);
                                            ps1.setLong(3, num);
                                            try (java.sql.ResultSet rs1 = ps1.executeQuery()) {
                                                if (rs1.next()) {
                                                    if (rs1.getByte("type") == 0 || rs1.getByte("type") == 3) {
                                                        messages.add(new MessageObject(rs1.getString("id1"),
                                                                rs1.getString("id2"), rs1.getLong("messagenumber"),
                                                                rs1.getTimestamp("timesent").getTime(),
                                                                rs1.getBoolean("sender"),
                                                                rs1.getString("message"), rs1.getByte("type")));
                                                    } else if (rs1.getByte("type") == 1 && (new File(
                                                            Program.img_path + p[0] + "_" + p[1] + "_" + num + ".png"))
                                                            .exists()) {
                                                        messages.add(
                                                                new MessageObject(rs1.getString("id1"),
                                                                        rs1.getString("id2"),
                                                                        rs1.getLong("messagenumber"),
                                                                        rs1.getTimestamp("timesent").getTime(),
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
                                                    .getBytes(StandardCharsets.UTF_16LE));
                                    // old messages sent
                                    System.out.println("6475 sent");
                                    if (Program.sessions.containsKey(ID)) {
                                        if (Program.sessions.get(ID).loaded > 1) {
                                            Program.sessions.get(ID).loaded -= 1;
                                        } else if (Program.sessions.get(ID).loaded == 1) {
                                            Program.sessions.get(ID)
                                                    .Queue_command("2411".getBytes(StandardCharsets.UTF_16LE));
                                            Program.sessions.get(ID).loaded -= 1;
                                            System.out.println("Sent 2411");
                                        }
                                    }
                                }
                            } else if (num > 1) {
                                int i = 0;
                                ArrayList<MessageObject> messages = new ArrayList<MessageObject>();
                                while (num > 0 && i < 20) {
                                    try (PreparedStatement ps = Program.sql.prepareStatement(
                                            "select top 1 * from message where id1=? and id2=? and messagenumber=?")) {
                                        ps.setString(1, p[0]);
                                        ps.setString(2, p[1]);
                                        ps.setLong(3, num);
                                        try (java.sql.ResultSet rs = ps.executeQuery()) {
                                            if (rs.next()) {
                                                if (rs.getByte("type") == 0 || rs.getByte("type") == 3) {
                                                    messages.add(new MessageObject(rs.getString("id1"),
                                                            rs.getString("id2"), rs.getLong("messagenumber"),
                                                            rs.getTimestamp("timesent", Program.tzCal).getTime(),
                                                            rs.getBoolean("sender"),
                                                            rs.getString("message"), rs.getByte("type")));
                                                } else if (rs.getByte("type") == 1 && (new File(
                                                        Program.img_path + p[0] + "_" + p[1] + num + ".png"))
                                                        .exists()) {
                                                    messages.add(
                                                            new MessageObject(rs.getString("id1"), rs.getString("id2"),
                                                                    rs.getLong("messagenumber"),
                                                                    rs.getTimestamp("timesent", Program.tzCal)
                                                                            .getTime(),
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
                                                .getBytes(StandardCharsets.UTF_16LE));

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
                                    ps.setLong(1, Long.parseLong(p[0]));
                                    ps.setLong(2, Long.parseLong(p[1]));
                                    // get a random negative number between -1000000000 and 0 (inclusive)
                                    ps.setLong(3, -Program.rand.nextInt(1000000000));
                                    ps.setString(4, now.toInstant().toString());
                                    ps.setBoolean(5, ID.equals(p[1]));
                                    ps.setNString(6, sqlmessage);
                                    ps.setByte(7, (byte) 0);

                                    if (ps.executeUpdate() >= 1) {
                                        success = true;

                                    }
                                }
                                if (success) {
                                    try (PreparedStatement another_ps = Program.sql.prepareStatement(
                                            "select top 1 * from message where id1=? and id2=? and timesent=? and sender=?")) {
                                        another_ps.setLong(1, Long.parseLong(p[0]));
                                        another_ps.setLong(2, Long.parseLong(p[1]));
                                        another_ps.setString(3, now.toInstant().toString());

                                        another_ps.setBoolean(4, ID.equals(p[1]));

                                        try (ResultSet rs = another_ps.executeQuery()) {
                                            if (rs.next()) {

                                                MessageObject msgobj = new MessageObject(
                                                        Tools.padleft(rs.getString("id1"), 19, '0'),
                                                        Tools.padleft(rs.getString("id2"), 19, '0'),
                                                        rs.getLong("messagenumber"),
                                                        rs.getTimestamp("timesent", Program.tzCal).getTime(),
                                                        rs.getBoolean("sender"), rs.getString("message"),
                                                        rs.getByte("type"));
                                                if (!ID.equals(receiver_id))
                                                    Program.sendToID(ID, msgobj);
                                                if (!Program.sendToID(receiver_id, msgobj)) {
                                                    Program.sessions.get(ID)
                                                            .Queue_command(("0404" + receiver_id)
                                                                    .getBytes(StandardCharsets.UTF_16LE));
                                                } else {
                                                    Program.sessions.get(ID)
                                                            .Queue_command(("2211" + receiver_id)
                                                                    .getBytes(StandardCharsets.UTF_16LE));
                                                }

                                            }
                                        } catch (SQLException sql) {

                                            sql.getNextException().printStackTrace();
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
                            System.out.println("Receiving image");
                            String receiver_id = Tools.receive_unicode(s, 38);
                            data = Tools.receive_ASCII_Automatically(s);
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
                                    ps.setString(4, now.toInstant().toString());
                                    ps.setBoolean(5, ID.equals(p[1]));
                                    ps.setNString(6, "");
                                    ps.setByte(7, (byte) 1);
                                    if (ps.executeUpdate() >= 1) {
                                        success = true;
                                    }
                                }
                                //System.out.println(success);
                                if (success) {
                                    try (PreparedStatement another_ps = Program.sql.prepareStatement(
                                            "select top 1 * from message where id1=? and id2=? and timesent=? and sender=?")) {
                                        another_ps.setString(1, p[0]);
                                        another_ps.setString(2, p[1]);
                                        another_ps.setString(3, now.toInstant().toString());
                                        another_ps.setBoolean(4, ID.equals(p[1]));
                                        try (ResultSet rs = another_ps.executeQuery()) {
                                            if (rs.next()) {
                                                try {
                                                    String img_message = data;
                                                    File temp = new File(Program.img_path + p[0] + "_" + p[1] + "_"
                                                            + rs.getLong("messagenumber") + ".png");
                                                    try (BufferedOutputStream fos = new BufferedOutputStream(
                                                            new FileOutputStream(temp))) {
                                                        fos.write(java.util.Base64.getDecoder().decode(img_message));
                                                    }
                                                    //System.out.println("Creating object");
                                                    MessageObject msgobj = new MessageObject(
                                                            Tools.padleft(rs.getString("id1"), 19, '0'),
                                                            Tools.padleft(rs.getString("id2"), 19, '0'),
                                                            rs.getLong("messagenumber"),
                                                            rs.getTimestamp("timesent", Program.tzCal).getTime(),
                                                            rs.getBoolean("sender"), img_message,
                                                            rs.getByte("type"));
                                                    //System.out.println("Sending image");
                                                    if (!ID.equals(receiver_id))
                                                        Program.sendToID(ID, msgobj);
                                                    if (!Program.sendToID(receiver_id, msgobj)) {
                                                        Program.sessions.get(ID).Queue_command(
                                                                ("0404" + receiver_id)
                                                                        .getBytes(StandardCharsets.UTF_16LE));
                                                        System.out.println("0404");
                                                    } else {
                                                        Program.sessions.get(ID).Queue_command(
                                                                ("2211" + receiver_id)
                                                                        .getBytes(StandardCharsets.UTF_16LE));
                                                        System.out.println("2211");
                                                    }

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
                        case "1903": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String p[] = Tools.compareIDs(ID, receiver_id);
                            String file = Tools.receive_Unicode_Automatically(s);
                            String length = Tools.receive_ASCII_Automatically(s);
                            boolean success = false;
                            try {
                                Timestamp now = new Timestamp(System.currentTimeMillis());
                                try (PreparedStatement ps = Program.sql
                                        .prepareStatement("insert into message values (?, ?, ?, ?, ?, ?, 3)")) {
                                    ps.setString(1, p[0]);
                                    ps.setString(2, p[1]);
                                    ps.setLong(3, -Program.rand.nextInt(1000000000));
                                    ps.setString(4, now.toInstant().toString());
                                    ps.setBoolean(5, ID.equals(p[1]));
                                    ps.setNString(6, file);
                                    if (ps.executeUpdate() >= 1) {
                                        success = true;
                                    }
                                }
                                //System.out.println(success);
                                if (success) {
                                    try (PreparedStatement ps2 = Program.sql.prepareStatement(
                                            "select top 1 * from message where id1 = ? and id2 = ? and timesent = ? and sender = ? and message = ? and type = 3")) {
                                        ps2.setString(1, p[0]);
                                        ps2.setString(2, p[1]);
                                        ps2.setString(3, now.toInstant().toString());
                                        ps2.setBoolean(4, ID.equals(p[1]));
                                        ps2.setNString(5, file);
                                        try (ResultSet rs = ps2.executeQuery()) {
                                            if (rs.next()) {
                                                try {
                                                    Program.files.put(p[0] + "_" + p[1] + "_"
                                                            + rs.getString("messagenumber") + ".",
                                                            new FileToWrite(Long.parseLong(length)));
                                                    Program.sessions.get(ID).Queue_command(
                                                            Tools.combine(
                                                                    ("1903" + receiver_id)
                                                                            .getBytes(StandardCharsets.UTF_16LE),
                                                                    Tools.data_with_ASCII_byte(
                                                                            rs.getString("messagenumber"))
                                                                            .getBytes(StandardCharsets.US_ASCII),
                                                                    Tools.data_with_unicode_byte(
                                                                            rs.getString("message"))
                                                                            .getBytes(StandardCharsets.UTF_16LE),
                                                                    Tools.data_with_ASCII_byte(Long.toString(
                                                                            Program.files.get(p[0] + "_" + p[1] + "_"
                                                                                    + rs.getString("messagenumber")
                                                                                    + ".").size))
                                                                            .getBytes(StandardCharsets.US_ASCII)));
                                                    MessageObject msgobj = new MessageObject(
                                                            Tools.padleft(rs.getString("id1"), 19, '0'),
                                                            Tools.padleft(rs.getString("id2"), 19, '0'),
                                                            rs.getLong("messagenumber"),
                                                            rs.getTimestamp("timesent", Program.tzCal).getTime(),
                                                            rs.getBoolean("sender"), rs.getString("message"),
                                                            rs.getByte("type"));
                                                    if (!ID.equals(receiver_id)){
                                                        Program.sendToID(ID, msgobj);
                                                    }
                                                    if (!Program.sendToID(receiver_id, msgobj)) {
                                                        Program.sessions.get(ID).Queue_command(
                                                                ("0404"+receiver_id).getBytes(StandardCharsets.UTF_16LE));
                                                    } else {
                                                        Program.sessions.get(ID).Queue_command(
                                                                ("2211"+receiver_id).getBytes(StandardCharsets.UTF_16LE));
                                                    }

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
                        } // ready to receive file from client
                            break;
                        case "1904": {
                            // suggest 1 line
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String num = Tools.receive_ASCII_Automatically(s);
                            // long messagenumber = Long.parseLong(num);
                            String offsetstr = Tools.receive_ASCII_Automatically(s);
                            long offset = Long.parseLong(offsetstr);
                            String received_byte_str = Tools.receive_ASCII_Automatically(s);
                            int received_byte = Integer.parseInt(received_byte_str);
                            byte[] databyte = Tools.receive_byte_array(s, received_byte);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            String filename = p[0] + "_" + p[1] + "_" + num + ".";
                            String filepath = Program.img_path + filename;
                            Program.sessions.get(ID).files_on_transfer.put(filename, true);
                            if (Program.sessions.containsKey(ID) &&
                                    Program.sessions.get(ID).files_on_transfer.containsKey(filename) &&
                                    Program.sessions.get(ID).files_on_transfer.get(filename) &&
                                    Program.files.containsKey(filename) && Program.files.get(filename).size > 0) {
                                boolean done = false;
                                while (!done) {
                                    try {
                                        if (Program.files.get(filename).fos != null) {
                                            if (Program.files.get(filename).fos.getChannel().isOpen()) {
                                                Program.files.get(filename).fos.getChannel().position(offset);
                                                Program.files.get(filename).fos.write(databyte, 0, received_byte);
                                                done = true;
                                                Program.files.get(filename).size -= received_byte;
                                                //System.out.println(
                                                //        "Writing file" + offsetstr + " " + received_byte_str);
                                                if (Program.files.get(filename).size <= 0) {
                                                    Program.files.get(filename).fos.close();
                                                    Program.files.get(filename).fos = null;
                                                    Program.files.remove(filename);
                                                    Program.sessions.get(ID).files_on_transfer.remove(filename);
                                                }
                                            }

                                        } else {
                                            // open filepath
                                            //System.out.println("Opening file");
                                            Program.files.get(filename).fos = new FileOutputStream(filepath);
                                            Program.files.get(filename).fos.getChannel().position(offset);
                                            Program.files.get(filename).fos.write(databyte, 0, received_byte);
                                            done = true;
                                            Program.files.get(filename).size -= received_byte;
                                            //System.out.println("Writing file" + offsetstr + " " + received_byte_str);
                                            if (Program.files.get(filename).size <= 0) {
                                                Program.files.get(filename).fos.close();
                                                Program.files.get(filename).fos = null;
                                                Program.files.remove(filename);
                                                Program.sessions.get(ID).files_on_transfer.remove(filename);
                                            }
                                        }
                                    } catch (Exception e) {
                                        if (e.getMessage().contains("being used by another process")) {
                                            Thread.sleep(100);
                                        } else {
                                            e.printStackTrace();
                                            FileToWrite f = Program.files.remove(filename);
                                            try {
                                                f.fis.close();
                                            } catch (Exception e2) {
                                            }
                                            f.fis = null;
                                            try {
                                                f.fos.close();
                                            } catch (Exception e2) {
                                            }
                                            f.fos = null;
                                            Program.sessions.get(ID).files_on_transfer.remove(filename);
                                            throw e;
                                        }
                                    }
                                }
                            } else if (Program.sessions.get(ID).files_on_transfer.containsKey(filename)
                                    && Program.sessions.get(ID).files_on_transfer.get(filename) == false) {
                                FileToWrite temp = Program.files.remove(filename);
                                if (temp != null) {
                                    try {
                                        temp.fis.close();
                                    } catch (Exception e) {
                                    }
                                    temp.fis = null;
                                    try {
                                        temp.fos.close();
                                    } catch (Exception e) {
                                    }
                                    temp.fos = null;
                                }
                                Program.sessions.get(ID).files_on_transfer.remove(filename);
                            }
                        }
                            break;
                        case "1905": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String num = Tools.receive_ASCII_Automatically(s);
                            System.out.println(receiver_id + " " + num + ".");
                            //System.out.println("Sending file");
                            Program.executor.execute(new Send_file(ID, receiver_id, num));
                        }
                            break;
                        case "2002": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            String messagenumber_str = Tools.receive_Unicode_Automatically(s);
                            long messagenumber = Long.parseLong(messagenumber_str);
                            try (PreparedStatement ps = Program.sql.prepareStatement(
                                    "delete top (1) from message where id1=? and id2=? and messagenumber=?")) {
                                ps.setString(1, p[0]);
                                ps.setString(2, p[1]);
                                ps.setLong(3, messagenumber);
                                ps.executeUpdate();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Program.handleException(ID, e.getMessage());
                            }
                            File f = new File(Program.img_path + p[0] + "_" + p[1] + "_" + messagenumber_str + ".png");
                            if (f.exists()) {
                                f.delete();
                            }
                            try {
                                String file = Program.img_path + p[0] + "_" + p[1] + "_" + messagenumber_str + ".";
                                File f1 = new File(file);
                                if (f1.exists()) {
                                    //
                                    String filename = p[0] + "_" + p[1] + "_" + messagenumber_str + ".";
                                    if (Program.sessions.containsKey(p[0])
                                            && Program.sessions.get(p[0]).files_on_transfer.containsKey(file)) {
                                        // set already existed file to false
                                        Program.sessions.get(p[0]).files_on_transfer.put(file, false);
                                    }
                                    // also set for p[1]
                                    if (Program.sessions.containsKey(p[1])
                                            && Program.sessions.get(p[1]).files_on_transfer.containsKey(file)) {
                                        // set already existed file to false
                                        Program.sessions.get(p[1]).files_on_transfer.put(file, false);
                                    }
                                    try {
                                        FileToWrite temp = Program.files.remove(filename);
                                        if (temp != null) {
                                            // close all resources with try catch
                                            try {
                                                temp.fis.close();
                                            } catch (Exception e) {
                                            }
                                            temp.fis = null;
                                            try {
                                                temp.fos.close();
                                            } catch (Exception e) {
                                            }
                                            temp.fos = null;
                                        }
                                    } catch (Exception e) {
                                    }
                                    Program.executor.execute(new Delete_file(file));
                                }
                            } catch (Exception e) {
                            }
                            if (Program.sessions.containsKey(receiver_id)) {
                                Program.sessions.get(receiver_id).Queue_command(
                                        ("2002" + ID + Tools.data_with_unicode_byte(messagenumber_str))
                                                .getBytes(StandardCharsets.UTF_16LE));
                            }
                        }
                            break;
                        case "2004": {
                            Program.shutdown(ID);
                        }
                            break;
                        case "0609": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            try (PreparedStatement ps = Program.sql
                                    .prepareStatement("select top 1 id, name, type from account where id=? and private=0")) {
                                ps.setLong(1, Long.parseLong(receiver_id));
                                try (ResultSet rs = ps.executeQuery()) {
                                    if (rs.next()) {
                                        int state = Program.sessions.containsKey(rs.getString("id")) ? 1 : 0;
                                        byte type = rs.getByte("type");
                                        Program.sessions.get(ID).Queue_command(
                                                Tools.combine(
                                                        "1609".getBytes(StandardCharsets.UTF_16LE),
                                                        Tools.data_with_unicode_byte(
                                                                Tools.padleft(String.valueOf(rs.getLong("id")), 19, '0')
                                                                        + " " + rs.getString("id") + " "
                                                                        + rs.getNString("name") + " " + state + " " + type)
                                                                .getBytes(StandardCharsets.UTF_16LE)));
                                    } else {
                                        Program.sessions.get(ID).Queue_command(
                                                "2609".getBytes(StandardCharsets.UTF_16LE));
                                    }
                                }
                            }
                        }
                            break;
                        case "0610":
                        {
                            String receiver_username = Tools.receive_Unicode_Automatically(s);
                            try(PreparedStatement ps = Program.sql.prepareStatement("select top 1 id, username, name from account where username=? and private=0"
                            )){
                                ps.setNString(1, receiver_username);
                                try(ResultSet rs = ps.executeQuery()){
                                    if(rs.next()){
                                        int state = Program.sessions.containsKey(rs.getString("id"))?1:0;
                                        Program.sessions.get(ID).Queue_command(
                                            Tools.combine(
                                                "1610".getBytes(StandardCharsets.UTF_16LE),
                                                Tools.data_with_unicode_byte(Tools.padleft(String.valueOf(rs.getLong("id")),
                                                19, '0') + " " + rs.getNString("username") + " " + rs.getNString("name") +
                                                " " + state).getBytes(StandardCharsets.UTF_16LE
                                                )
                                            )
                                        );
                                    }
                                    else {
                                        Program.sessions.get(ID).Queue_command(
                                        "2609".getBytes(StandardCharsets.UTF_16LE)
                                        );
                                    }
                                }
                            }
                        }
                        break;
                        case "0611":{
                            long randomid = 0;
                            while (randomid <=0 || Program.check_existed_id(randomid) || Program.check_existed_username(randomid+"")){
                                randomid = Program.rand.nextLong();
                            }
                            String id_string = Long.toString(randomid);
                            while (id_string.length() < 19){
                                id_string = "0" + id_string;
                            }
                            String name = "";
                            boolean get = false;
                            try(PreparedStatement command = Program.sql.prepareStatement("select top 1 name from account where id = ?")){
                                command.setLong(1, Long.parseLong(ID));
                                try(ResultSet rs = command.executeQuery()){
                                    if(rs.next()){
                                        name = rs.getString("name");
                                        get = true;
                                    }
                                }
                            }
                            if (get){
                                // insert into account values (id, username, name, pw, state, private, number_of_contacts, avatar)
                                try(PreparedStatement command = Program.sql.prepareStatement("insert into account values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){
                                    command.setLong(1, randomid);
                                    command.setNull(2, Types.NVARCHAR);
                                    command.setNString(3, "Group of " + name);
                                    // setNstring 4 to BCrypt hash password
                                    command.setNull(4, Types.NVARCHAR);
                                    command.setByte(5, (byte)1);
                                    command.setBoolean(6, false);
                                    command.setInt(7, 0);
                                    command.setNull(8, Types.VARCHAR);
                                    command.setNull(9, Types.NVARCHAR);
                                    command.setByte(10, (byte)1 );
                                    if (command.executeUpdate() > 0){
                                        Program.sessions.get(ID).Queue_command(
                                                Tools.combine(
                                                        "1611".getBytes(StandardCharsets.UTF_16LE),
                                                        Tools.data_with_unicode_byte(id_string).getBytes(StandardCharsets.UTF_16LE))
                                        );
                                    }
                                }
                            }                           
                        }
                        case "1060": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String path = Program.avatar_path + receiver_id + ".png";
                            File f = new File(path);
                            if (f.exists()) {
                                Program.sessions.get(ID).Queue_command(
                                        Tools.combine(
                                                ("1060" + receiver_id).getBytes(StandardCharsets.UTF_16LE),
                                                Tools.data_with_ASCII_byte(Tools.ImageToBASE64(path))
                                                        .getBytes(StandardCharsets.US_ASCII)));
                            }
                        }
                            break;
                        case "0601": {
                            String img_string = Tools.receive_ASCII_Automatically(s);
                            String tempFile = Program.avatar_path + ID + ".png";
                            // convert base64 string to image
                            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                                fos.write(Base64.getDecoder().decode(img_string));
                            } catch (Exception e) {
                            }
                        }
                            break;
                        case "4269": {
                            String opw = Tools.receive_Unicode_Automatically(s);
                            String npw = Tools.receive_Unicode_Automatically(s);
                            try (PreparedStatement ps = Program.sql
                                    .prepareStatement("select top 1 pw from account where id=?")) {
                                ps.setLong(1, Long.parseLong(ID));
                                try (ResultSet rs = ps.executeQuery()) {
                                    if (rs.next()) {
                                        if (BCrypt.checkpw(opw, rs.getNString("pw"))) {
                                            try (PreparedStatement ps2 = Program.sql
                                                    .prepareStatement("update top (1) account set pw=? where id=?")) {
                                                ps2.setNString(1, BCrypt.hashpw(npw, BCrypt.gensalt()));
                                                ps2.setLong(2, Long.parseLong(ID));
                                                ps2.executeUpdate();
                                                Program.sessions.get(ID).Queue_command(
                                                        "4269".getBytes(StandardCharsets.UTF_16LE));
                                            }
                                        } else {
                                            Program.sessions.get(ID).Queue_command(
                                                    "9624".getBytes(StandardCharsets.UTF_16LE));
                                        }
                                    }
                                }
                            }
                        }
                            break;
                        case "1508": {
                            try (PreparedStatement ps = Program.sql
                                    .prepareStatement("update top (1) account set private=1 where id=?")) {
                                ps.setLong(1, Long.parseLong(ID));
                                ps.executeUpdate();
                            }
                        }
                            break;
                        case "0508": {
                            try (PreparedStatement ps = Program.sql
                                    .prepareStatement("update top (1) account set private=0 where id=?")) {
                                ps.setLong(1, Long.parseLong(ID));
                                ps.executeUpdate();
                            }
                        }
                            break;
                        case "1012": {
                            String newname = Tools.receive_Unicode_Automatically(s);
                            try (PreparedStatement ps = Program.sql
                                    .prepareStatement("update top (1) account set name=? where id=?")) {
                                ps.setNString(1, newname);
                                ps.setLong(2, Long.parseLong(ID));
                                if (ps.executeUpdate() == 1) {
                                    Program.sessions.get(ID).Queue_command(
                                            "1012".getBytes(StandardCharsets.UTF_16LE));
                                }
                            }
                        }
                            break;
                        case "5859": {
                            String receiver_id = Tools.receive_unicode(s, 38);
                            String[] p = Tools.compareIDs(ID, receiver_id);
                            try (PreparedStatement ps = Program.sql
                                    .prepareStatement("delete top (1) from friend where id1=? and id2=?")) {
                                ps.setLong(1, Long.parseLong(p[0]));
                                ps.setLong(2, Long.parseLong(p[1]));
                                ps.executeUpdate();
                            }
                            Program.executor.execute(new Delete_conversation(p[0], p[1]));
                        }
                            break;
                        case "7351": {
                            String state_str = Tools.receive_unicode(s, 2);
                            byte state = Byte.parseByte(state_str);
                            Program.sessions.get(ID).status = state;
                        }
                            break;
                        default: {
                            keepreading = false;
                            Program.shutdown(ID);
                        }
                            break;
                    }
                } else {
                    keepreading = false;
                    Program.shutdown(ID);
                }
                int b = s.read();
                if (b == -1)
                    keepreading = false;
                else
                    s.unread(b);
            } while (keepreading);
        } catch (Exception e) {
            e.printStackTrace();

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
            //System.out.flush();
        }
    }
}
