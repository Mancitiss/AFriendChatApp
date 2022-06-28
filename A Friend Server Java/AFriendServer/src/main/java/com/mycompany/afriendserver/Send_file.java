package com.mycompany.afriendserver;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class Send_file implements Runnable {

    private String ID;
    private String receiver_id;
    private String num;

    public Send_file(String iD, String receiver_id, String num) {
        this.ID = iD;
        this.receiver_id = receiver_id;
        this.num = num;
    }

    @Override
    public void run() {
        System.out.println("Send_file");
        String[] p = Tools.compareIDs(ID, receiver_id);
        String file = Program.img_path + p[0] + "_" + p[1] + "_" + num + ".";
        if (Program.sessions.containsKey(ID) && Program.sessions.get(ID).files_on_transfer.containsKey(file)) return;
        Program.sessions.get(ID).files_on_transfer.put(file, true);
        try {
            File f = new File(file);
            if (f.exists()){
                System.out.println("File exists");
                try(FileInputStream fis = new FileInputStream(f)){
                    long filesize = f.length();
                    System.out.println("File size: " + filesize);
                    Program.sessions.get(ID).Queue_command(
                        Tools.combine(
                            ("1905" + receiver_id).getBytes(StandardCharsets.UTF_16LE),
                            Tools.data_with_ASCII_byte(num).getBytes(StandardCharsets.US_ASCII),
                            Tools.data_with_ASCII_byte(String.valueOf(filesize)).getBytes(StandardCharsets.US_ASCII)
                        )
                    );
                    long offset = 0;
                    byte[] buffer = new byte[32768];
                    while(offset < filesize){
                        if (filesize - offset > buffer.length){
                            int first_byte_expected = buffer.length;
                            int byte_expectex = first_byte_expected;
                            int total_byte_received = 0;
                            int received_byte;
                            do {
                                received_byte = fis.read(buffer, total_byte_received, byte_expectex);
                                if (received_byte > 0){
                                    total_byte_received += received_byte;
                                    byte_expectex -= received_byte;
                                }
                                else break;
                            } while (byte_expectex > 0 && received_byte > 0);
                            while (Program.sessions.get(ID).commands.size() > 5) {
                                try{
                                    Thread.sleep(30);
                                }
                                catch(Exception e){}
                            }
                            if (total_byte_received == first_byte_expected && Program.sessions.get(ID).files_on_transfer.get(file)){
                                Program.sessions.get(ID).Queue_command(
                                    Tools.combine(
                                        ("1904" + receiver_id).getBytes(StandardCharsets.UTF_16LE),
                                        Tools.data_with_ASCII_byte(num).getBytes(StandardCharsets.US_ASCII),
                                        Tools.data_with_ASCII_byte(String.valueOf(offset)).getBytes(StandardCharsets.US_ASCII),
                                        Tools.data_with_ASCII_byte(String.valueOf(received_byte)).getBytes(StandardCharsets.US_ASCII),
                                        buffer
                                    )
                                );
                            }
                            else {
                                Program.sessions.get(ID).Queue_command(
                                    Tools.combine(
                                        ("1904" + receiver_id).getBytes(StandardCharsets.UTF_16LE),
                                        Tools.data_with_ASCII_byte(num).getBytes(StandardCharsets.US_ASCII),
                                        Tools.data_with_ASCII_byte("-1").getBytes(StandardCharsets.US_ASCII)
                                    )
                                );
                                break;
                            }
                            offset += total_byte_received;
                        }
                        else {
                            int first_byte_expected = (int) (filesize - offset);
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
                            if (total_byte_received == first_byte_expected && Program.sessions.get(ID).files_on_transfer.get(file)){
                                Program.sessions.get(ID).Queue_command(
                                    Tools.combine(
                                        ("1904" + receiver_id).getBytes(StandardCharsets.UTF_16LE),
                                        Tools.data_with_ASCII_byte(num).getBytes(StandardCharsets.US_ASCII),
                                        Tools.data_with_ASCII_byte(String.valueOf(offset)).getBytes(StandardCharsets.US_ASCII),
                                        Tools.data_with_ASCII_byte(String.valueOf(received_byte)).getBytes(StandardCharsets.US_ASCII),
                                        final_buffer
                                    )
                                );
                            }
                            else {
                                Program.sessions.get(ID).Queue_command(
                                    Tools.combine(
                                        ("1904" + receiver_id).getBytes(StandardCharsets.UTF_16LE),
                                        Tools.data_with_ASCII_byte(num).getBytes(StandardCharsets.US_ASCII),
                                        Tools.data_with_ASCII_byte("-1").getBytes(StandardCharsets.US_ASCII)
                                    )
                                );
                                break;
                            }
                            offset += total_byte_received;
                        }
                    }
                }                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                Program.sessions.get(ID).files_on_transfer.remove(file);
            } catch (Exception e){
            }
        }
    }

}
