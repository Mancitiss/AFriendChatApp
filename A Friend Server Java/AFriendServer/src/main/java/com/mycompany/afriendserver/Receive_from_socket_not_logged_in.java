package com.mycompany.afriendserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.net.ssl.SSLSocket;

public class Receive_from_socket_not_logged_in implements Runnable {
    SSLSocket client;

    public Receive_from_socket_not_logged_in(SSLSocket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            // create streams from client
            DataInputStream DIS = new DataInputStream(client.getInputStream());
            DataOutputStream DOS = new DataOutputStream(client.getOutputStream());
            String data = Tools.receive_unicode(DIS, 8);
            if (data != null && !data.isEmpty()) {
                String instruction = data;
                if (instruction == "0012") {
                    data = Tools.receive_ASCII(DIS, 19);
                    DIS.close();
                    DOS.close();
                    client.close();
                    boolean pass = false;
                    if (Program.sessions.containsKey(data) && Program.sessions.get(data).is_waited.getAndSet(1) == 1) {
                        pass = true;
                    }
                    int h = 0;
                    while (!pass && h++ < 20 && !Program.sessions.containsKey(data)) {
                        synchronized (this) {
                            this.wait(1000);
                        }
                    }
                    if (!pass && Program.sessions.containsKey(data)){
                        try{
                            boolean do_work = false;
                            while(Program.sessions.get(data).is_locked.getAndSet(1) == 1){
                                synchronized (this){
                                    this.wait(1000);
                                }
                            }
                            if (Program.sessions.get(data).client.isConnected()){
                                if (Program.sessions.get(data).client.getInputStream().available() > 0) {
                                    try{
                                        do_work = true;
                                        // ThreadPool.QueueUserWorkItem(Receive_message, data);
                                        (new Thread(new Receive_message(data))).start();
                                    } catch (Exception e){
                                        Program.handleException(data, e.toString());
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                Program.shutdown(data);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
