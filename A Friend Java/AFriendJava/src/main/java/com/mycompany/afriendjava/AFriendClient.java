package com.mycompany.afriendjava;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class AFriendClient {
    public static final String SERVER_ADDRESS = "mancitiss.duckdns.org";
    public static final int SERVER_PORT = 11111;

    private static String instruction;

    private static HashMap<String, List<MessageObject>> first = new HashMap<String, List<MessageObject>>();
    public static HashMap<String, AFile> files = new HashMap<String, AFile>();

    public static String tempName = null;
    public static String imgString = null;
    public static byte publicState = 0;

    public static SSLSocketFactory ssf = (SSLSocketFactory)SSLSocketFactory.getDefault();
    public static SSLSocket client;
    public static DataOutputStream dos;
    public static DataInputStream dis;
    public static Account user;

    public static boolean loginResult = true;

    private static AtomicReference<Integer> workerAdded = new AtomicReference<Integer>(0);
    private static AtomicReference<Integer> sendFileWorker = new AtomicReference<Integer>(0);
    private static AtomicReference<Integer> writeFileAdded = new AtomicReference<Integer>(0);
    public static ConcurrentLinkedQueue<byte[]> commands = new ConcurrentLinkedQueue<byte[]>();
    public static ConcurrentLinkedQueue<Slot> availableSlots = new ConcurrentLinkedQueue<Slot>();
    public static ConcurrentLinkedQueue<WriteCommand> writeCommands = new ConcurrentLinkedQueue<WriteCommand>();
    public static ConcurrentHashMap<String, Boolean> filesOnCancel = new ConcurrentHashMap<String, Boolean>();

    private static void addWriteThread(){
        if (writeFileAdded.getAndSet(1) == 0){
            try{
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try{
                            while(!writeCommands.isEmpty()){
                                WriteCommand wc = writeCommands.poll();
                                try{
                                    if (files.get(wc.file).fos != null){
                                        if (files.get(wc.file).fos.getChannel().isOpen()){
                                            files.get(wc.file).fos.getChannel().position(wc.offset);
                                            files.get(wc.file).fos.write(wc.databyte, 0, wc.received_byte);
                                            files.get(wc.file).size -= wc.received_byte;
                                            if (files.get(wc.file).size <= 0){
                                                files.get(wc.file).fos.close();
                                                files.get(wc.file).fos = null;
                                                files.remove(wc.file);
                                            }
                                        }
                                    }
                                    else {
                                        files.get(wc.file).fos = new FileOutputStream(wc.file);
                                        files.get(wc.file).fos.getChannel().position(wc.offset);
                                        files.get(wc.file).fos.write(wc.databyte, 0, wc.received_byte);
                                        files.get(wc.file).size -= wc.received_byte;
                                        if (files.get(wc.file).size == 0){
                                            files.get(wc.file).fos.close();
                                            files.get(wc.file).fos = null;
                                            files.remove(wc.file);
                                        }
                                        System.out.println("File " + wc.file + " written");
                                    }
                                }
                                catch (Exception e){
                                    if (e.toString().contains("being used by another process")){
                                        System.out.println("try again! Thread ID: " + Thread.currentThread().getId() + ", offset: " + wc.offset);
                                        writeCommands.add(wc);
                                    }
                                    else {
                                        System.out.println("Error: " + e.toString());
                                        files.get(wc.file).fos.close();
                                        files.remove(wc.file);
                                    }
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally{
                            writeFileAdded.set(0);
                        }
                    }
                }
                ).start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void queueSlot(String id, long num, String file, long length){
        availableSlots.add(new Slot(id, num, file, length));
        addSendFileThread();
    }

    public static void addSendFileThread(){
        if (0 == sendFileWorker.getAndSet(1)){
            try{
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        System.out.println("Start sending files");
                        try{
                            while(!availableSlots.isEmpty()){
                                Slot s = availableSlots.poll();
                                if (s == null) throw new Exception("Slot is null");
                                boolean fileFound = false;
                                while(!fileFound){
                                    String file = Program.mainform.panelChats.get(s.id).filesToSend.poll();
                                    if (file == null) throw new Exception("File is null");
                                    // check if file path exist and is a file
                                    File f = new File(file);
                                    if (f.exists() && f.isFile()){
                                        // get file name from path
                                        String fileName = file.substring(file.lastIndexOf(File.separator) + 1);
                                        // get file size
                                        long fileSize = f.length();
                                        if (fileName.equals(s.name) && fileSize == s.length){
                                            // send file
                                            System.out.println("Start sending file" + fileName);

                                            fileFound = true;
                                            try{
                                                try(FileInputStream fis = new FileInputStream(f)){
                                                    long offset = 0;
                                                    byte[] buffer = new byte[32768];
                                                    while (offset < fileSize){
                                                        if (filesOnCancel.containsKey(user.id + "_" + s.id + "_" + s.num) ){
                                                            filesOnCancel.remove(user.id + "_" + s.id + "_" + s.num);
                                                            break;
                                                        }
                                                        if (fileSize - offset > buffer.length){
                                                            int first_byte_expected = buffer.length;
                                                            int byte_expected = first_byte_expected;
                                                            int total_byte_received = 0;
                                                            int byte_received;
                                                            do{
                                                                byte_received = fis.read(buffer, total_byte_received, byte_expected);
                                                                if (byte_received > 0){
                                                                    total_byte_received += byte_received;
                                                                    byte_expected -= byte_received;
                                                                }
                                                                else break;
                                                            } while(byte_expected > 0 && byte_received > 0);
                                                            if (total_byte_received == first_byte_expected){
                                                                while (commands.size() > 10) Thread.sleep(25);
                                                                queueCommand(Tools.combine(
                                                                    "1904".getBytes(StandardCharsets.UTF_16LE), 
                                                                    s.id.getBytes(StandardCharsets.UTF_16LE),
                                                                    Tools.data_with_ASCII_byte(Long.toString(s.num)).getBytes(StandardCharsets.US_ASCII),
                                                                    Tools.data_with_ASCII_byte(Long.toString(offset)).getBytes(StandardCharsets.US_ASCII),
                                                                    Tools.data_with_ASCII_byte(Long.toString(byte_received)).getBytes(StandardCharsets.US_ASCII),
                                                                    buffer
                                                                ));
                                                            }
                                                            offset += total_byte_received;
                                                            try{
                                                                // this method is synchronized, its parameters must be volatile
                                                                Program.mainform.panelChats.get(s.id).messages.get(s.num).changeTextUpload((byte)(100 * offset/ fileSize));
                                                            }
                                                            catch (Exception ex){
                                                                ex.printStackTrace();
                                                            }
                                                        }
                                                        else
                                                        {
                                                            int first_byte_expected = (int) (fileSize - offset);
                                                            int byte_expectex = first_byte_expected;
                                                            byte[] final_buffer = new byte[first_byte_expected];
                                                            int total_byte_received = 0;
                                                            int received_byte;
                                                            do {
                                                                received_byte = fis.read(final_buffer, total_byte_received, byte_expectex);
                                                                if (received_byte > 0){
                                                                    total_byte_received += received_byte;
                                                                    byte_expectex -= received_byte;
                                                                }
                                                                else break;
                                                            } while (byte_expectex > 0 && received_byte > 0);
                                                            if (total_byte_received == first_byte_expected){
                                                                queueCommand(Tools.combine(
                                                                        "1904".getBytes(StandardCharsets.UTF_16LE),
                                                                        Tools.data_with_ASCII_byte(String.valueOf(s.num)).getBytes(StandardCharsets.US_ASCII),
                                                                        Tools.data_with_ASCII_byte(String.valueOf(offset)).getBytes(StandardCharsets.US_ASCII),
                                                                        Tools.data_with_ASCII_byte(String.valueOf(received_byte)).getBytes(StandardCharsets.US_ASCII),
                                                                        final_buffer
                                                                    )
                                                                );
                                                            }
                                                            offset += total_byte_received;
                                                            try
                                                            {
                                                                // this method is synchronized, its parameters must be volatile
                                                                Program.mainform.panelChats.get(s.id).messages.get(s.num).changeTextUpload(byte)(100 * offset / fileSize);
                                                            }
                                                            catch (Exception ex)
                                                            {
                                                                ex.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            catch (Exception e){
                                                e.printStackTrace();
                                            }
                                            System.out.println("File " + fileName + " sent");
                                        }
                                        else {
                                            Program.mainform.panelChats.get(s.id).filesToSend.add(file);
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally{
                            sendFileWorker.set(0);
                        }
                    }
                }).start();
            }
            catch (Exception e){
                e.printStackTrace();
                try{
                    sendFileWorker.set(0);
                }
                catch(Exception ex){}
            }
        }
    }

    public static void queueCommand(byte[] command){
        commands.add(command);
        ping();
        addWorkerThread();
    }

    private static void addWorkerThread(){
        if (0 == workerAdded.getAndSet(1)){
            try{
                new Thread(() -> {
                    try{
                        while(!commands.isEmpty()){
                            byte[] command = commands.poll();
                            dos.write(command);
                            ping();
                        }
                        ping();
                        workerAdded.set(0);
                        System.out.println("Worker thread stopped");
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }).start();
            }
            catch (Exception e){
                e.printStackTrace();
                try{
                    workerAdded.set(0);
                }
                catch (Exception ex){}
            }
        }
    }

    public static void ping(){
        try{
            try(SSLSocket client = (SSLSocket)ssf.createSocket(SERVER_ADDRESS, SERVER_PORT)){
                client.getOutputStream().write(Tools.combine("0012".getBytes(StandardCharsets.UTF_16LE), user.id.getBytes(StandardCharsets.US_ASCII)));
                System.out.println("Pinged");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void logOut(){
        if (user != null) user.state = 0;
        tempName = null;
        imgString = null;
        user = null;
        availableSlots = new ConcurrentLinkedQueue<Slot>();
        commands = new ConcurrentLinkedQueue<byte[]>();
        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
    }

    public static void changeName(){
        if (tempName != null && !tempName.isEmpty()){
            user.name = tempName;
        } 
        tempName = null;
    }

    public static void executeClient(){
        try{
            while (user != null && (user.state == 1 || user.state == 2)){
                try{
                    if (client.isConnected()){
                        receiveFromId(client);
                    }
                    else {
                        logOut();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            logOut();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean tryLogin(String username, String password){
        try {
            client = (SSLSocket) ssf.createSocket(SERVER_ADDRESS, SERVER_PORT);
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            queueCommand(("0010" + Tools.data_with_unicode_byte(username) + Tools.data_with_unicode_byte(password)).getBytes(StandardCharsets.UTF_16LE));
            receiveFromId(client);
            if (user == null){
                queueCommand("0004".getBytes(StandardCharsets.UTF_16LE));
                ping();
                throw new Exception("Login failed");
            }
            FormApplication.currentID = user.id;
            if (!loginResult){
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                dis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                dos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    public static void sendToId(String id, String myId, String str){
        if (myId.length() != 19 || id.length() != 19){
            return;
        }
        queueCommand(("1901"+Tools.data_with_unicode_byte(id+str)).getBytes(StandardCharsets.UTF_16LE));
    }

    private static void receiveFromId(SSLSocket client){
        try{
            String instruction = Tools.receive_ASCII(dis, 8);
            System.out.println(instruction);
            switch(instruction){
                // log in failed
                case "-200":{
                    loginResult = false;
                }
                break;

                // log in success
                case "0200":{
                    user = new Account();
                    user.id = Tools.receive_unicode(dis, 38);
                    user.name = Tools.receive_Unicode_Automatically(dis);
                    String priv = Tools.receive_unicode(dis, 10);
                    user.priv = Boolean.parseBoolean(priv);
                    user.state = 1;
                    // set initial private option (on or off) from here
                    // or not, if you don't have to
                    try{
                        // this method is synchronized, its parameters must be volatile
                        Program.mainform.formSettings.changeIncognitoMode(user.priv);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                } break;

                // this id is offline
                case "0404":{
                    String offline_id = Tools.receive_unicode(dis, 38);
                    try{
                        // this method is synchronized, its parameters must be volatile
                        Program.mainform.turnContactActiveState(offline_id, (byte)0);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                } break;

                // avatar received, not yet loaded
                case "0601":{
                    imgString = Tools.receive_ASCII_Automatically(dis);
                } break;

                // me seen
                case "0708":{
                    String id = Tools.receive_unicode(dis, 38);
                    String boolStr = Tools.receive_unicode(dis, 2);
                    if (boolStr.equals("0") && Program.mainform.panelChats.get(id).isLastMessageFromYou()){
                        // this method is synchronized, its parameters must be volatile
                        Program.mainform.contactItems.get(id).setUnread(false);
                    }
                    else if (boolStr.equals("0") && !Program.mainform.panelChats.get(id).isLastMessageFromYou()){
                        // this method is synchronized, its parameters must be volatile
                        Program.mainform.contactItems.get(id).setUnread(true);
                    }
                    else{
                        // this method is synchronized, its parameters must be volatile
                        Program.mainform.contactItems.get(id).setUnread(false);
                    }
                } break;

                // new account created successfully
                case "1011":{
                    System.out.println("new account created");
                } break;

                // name changed successfully
                case "1012":{
                    System.out.println("name changed");
                    changeName();
                    
                    // this method is synchronized, its parameters must be volatile
                    Program.mainform.formSettings.changeSettingsWarning("Name changed successfully!", new Color(37, 75, 133));
                } break;

                case "1060":{
                    String panelId = Tools.receive_unicode(dis, 38);
                    String friendAvatar = Tools.receive_Unicode_Automatically(dis);
                    if (friendAvatar!=null && !friendAvatar.isEmpty()){
                        byte[] avatar = Base64.getDecoder().decode(friendAvatar);
                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(avatar));
                        
                        // this method is synchronized, its parameters must be volatile
                        Program.mainform.setAvatar(panelId, img);
                    }
                } break;

                // 1111 username exists

                default:{

                }
                break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
