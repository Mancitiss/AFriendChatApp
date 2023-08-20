package mancie.mancitiss.afriendserver;

import java.sql.Timestamp;
import java.time.ZoneId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class test {
    private static class MyObject{
        Timestamp time;
        MyObject(Timestamp time){
            this.time = time;
        }
    }
    public static void main(String[] args) {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        System.out.println(t);
        System.out.println(t.getTime());
        System.out.println(t.toString());
        System.out.println(t.toInstant());
        //MyObject m = new MyObject(t);
        // set time format in nanoseconds for gsonbuilder
        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        //String json = gson.toJson(m);
        //System.out.println(json);
    }
}
