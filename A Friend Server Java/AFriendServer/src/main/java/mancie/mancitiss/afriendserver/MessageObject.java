package mancie.mancitiss.afriendserver;

public class MessageObject {
    public String id1;
    public String id2;
    public long messagenumber;
    // date - time object that contains year, month, day, hour, minute, second, and millisecond
    public long timesent;
    public boolean sender;
    public String message;
    public byte type;

    // constructor
    public MessageObject(String id1, String id2, long messagenumber, long timesent, boolean sender, String message, byte type) {
        this.id1 = id1;
        this.id2 = id2;
        this.messagenumber = messagenumber;
        this.timesent = timesent;
        this.sender = sender;
        this.message = message;
        this.type = type;
    }
    // default constructor
    public MessageObject() {
    }
}
