package com.mycompany.afriendserver;

import java.io.DataInputStream;
import java.nio.charset.StandardCharsets;

public class Tools {
    public static String receive_ASCII(DataInputStream DIS, int length) {
        byte[] buffer = new byte[length];
        try {
            DIS.readFully(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return string from byte array as unicode
        return new String(buffer, StandardCharsets.US_ASCII);
    }
    public static String receive_unicode(DataInputStream DIS, int length) {
        byte[] buffer = new byte[length];
        try {
            DIS.readFully(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return string from byte array as unicode
        return new String(buffer, StandardCharsets.UTF_16);
    }
}
