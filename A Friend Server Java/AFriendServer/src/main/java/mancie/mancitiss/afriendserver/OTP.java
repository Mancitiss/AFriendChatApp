package mancie.mancitiss.afriendserver;

public class OTP {
    public String otp;
    public long time;
    public OTP(String otp, long expire_time) {
        this.otp = otp;
        this.time = expire_time;
    }
    public OTP() {
    }
}
