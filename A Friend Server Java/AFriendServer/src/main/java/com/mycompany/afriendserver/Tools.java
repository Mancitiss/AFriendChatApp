package com.mycompany.afriendserver;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
    public static String receive_ASCII_Automatically(DataInputStream DIS){
        String result = "";
        try{
            result = receive_ASCII(DIS, 2);
            int bytesize = Integer.parseInt(result, 10);
            result = receive_ASCII(DIS, bytesize);
            bytesize = Integer.parseInt(result, 10);
            result = receive_ASCII(DIS, bytesize);
        } catch (Exception e){
            e.printStackTrace();
            result = "";
        }
        return result;
    }
    public static String receive_Unicode_Automatically(DataInputStream DIS){
        String result = "";
        try{
            result = receive_unicode(DIS, 4);
            int bytesize = Integer.parseInt(result, 10) * 2;
            result = receive_unicode(DIS, bytesize);
            bytesize = Integer.parseInt(result, 10);
            result = receive_unicode(DIS, bytesize);
        } catch (Exception e){
            e.printStackTrace();
            result = "";
        }
        return result;
    }
    public static String padleft(String s, int n, char c){
        while (s.length() < n){
            s = c + s;
        }
        return s;
    }
    public static String data_with_unicode_byte(String data){
        if (!data.isEmpty()){
            String databyte = Integer.toString(data.length()*2, 10);
            return padleft(Integer.toString(data.length(), 10), 2, '0') + databyte + data;
        }
        return "";
    }
    public static String data_with_ASCII_byte(String data){
        if (!data.isEmpty()){
            String databyte = Integer.toString(data.length(), 10);
            return padleft(Integer.toString(data.length(), 10), 2, '0') + databyte + data;
        }
        return "";
    }
    //combine n byte arrays
    public static byte[] combine(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        byte[] result = new byte[length];
        int offset = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static String[] compareIDs(String ID1, String ID2){
        if (ID1.compareTo(ID2) <= 0){
            return new String[]{ID1, ID2};
        } else {
            return new String[]{ID2, ID1};
        }
    }
    public static String ImageToBASE64(String string) {
        String result = "";
        File imagefile = new File(string);
        // check if file exists
        if (imagefile.exists()){
            try {
                // read file
                byte[] imagebyte = new byte[(int) imagefile.length()];
                try(DataInputStream DIS = new DataInputStream(new FileInputStream(imagefile))){
                    DIS.readFully(imagebyte);
                }
                // convert to base64
                result = Base64.getEncoder().encodeToString(imagebyte);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
