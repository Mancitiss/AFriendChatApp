/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.afriendserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.out;
import java.net.ServerSocket;
import java.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64.Encoder;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
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
    private static String avatar_path = "F:\\App\\AFriendServer\\avatar";
    private static String img_path = "F:\\App\\AFriendServer\\message";
    
    public static void main(String[] args){
        // TODO code application logic here
        try{
            System.setProperty("javax.net.ssl.keyStore", "F:/Python Learning/web_cert/server.p12");
            System.setProperty("javax.net.ssl.keyStorePassword","RoRo");
            SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket ss = (SSLServerSocket)ssf.createServerSocket(11112);
            SSLSocket client = (SSLSocket)ss.accept();
            out.println("Accepted Client!");
            DataOutputStream DOS = new DataOutputStream(client.getOutputStream());
            DataInputStream DIS = new DataInputStream(client.getInputStream());
            while(true){
                String receivedMessage = DIS.readUTF();
                out.println("Client said: " + receivedMessage);
                if(receivedMessage.equals("close")){
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
        } catch (Exception e){
            out.println("Error: " + e.toString());
        }
    }
    
}
