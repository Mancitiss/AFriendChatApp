package mancie.mancitiss.afriendserver;

import java.io.File;

public class Delete_file implements Runnable {

    private String file;

    public Delete_file(String file) {
        this.file = file;
    }

    @Override
    public void run() {
        boolean done = false;
        do{
            try{
                FileToWrite temp = Program.files.remove(file);
                if (temp != null){
                    try{
                        temp.fis.close();
                    }
                    catch(Exception e){}
                    try{
                        temp.fos.close();
                    }
                    catch(Exception e){}
                    temp.fis = null;
                    temp.fos = null;
                }
                // delete file
                File f = new File(file);
                if (f.exists()){
                    f.delete();
                }
                done = true;
            }
            catch (Exception e){
                if (e.getMessage().contains("is being used by")){
                    
                    synchronized(this){
                        try {
                            this.wait(100);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    e.printStackTrace();
                    done = true;
                }
            }
        } while (!done);
    }
}
