package mancie.mancitiss.afriendserver;

public class FileToWrite {
    long size;
    // filestreams that can read/write files
    java.io.FileInputStream fis;
    java.io.FileOutputStream fos;

    public FileToWrite(){}
    // constructor that takes the file input stream
    public FileToWrite(long size, java.io.FileInputStream fis) {
        this.size = size;
        this.fis = fis;
    }
    // constructor that takes the file output stream
    public FileToWrite(long size, java.io.FileOutputStream fos) {
        this.size = size;
        this.fos = fos;
    }
    // construcotr that takes size only
    public FileToWrite(long size) {
        this.size = size;
    }
}
