package com.mycompany.afriendserver;

import java.io.File;
import java.sql.PreparedStatement;

public class Delete_conversation implements Runnable {


    private String id1;
    private String id2;

    public Delete_conversation(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public void run() {
        try(PreparedStatement ps = Program.sql.prepareStatement("delete from message where id1=? and id2=?")){
            ps.setString(1, id1);
            ps.setString(2, id2);
            ps.executeUpdate();
        }
        catch(Exception e){
        }
        try(PreparedStatement ps = Program.sql.prepareStatement("delete top (1) from seen where id1=? and id2=?")){
            ps.setString(1, id1);
            ps.setString(2, id2);
            ps.executeUpdate();
        }
        catch(Exception e){
        }
        String files_to_delete = id1 + "_" + id2 + "_" +"*.*";
        if (files_to_delete.length() > 19){
            try{
                File files = new File(files_to_delete);
                //foreach in java
                for (File file : files.listFiles()){
                    try{
                        if (file.getAbsolutePath().length() > 19){
                            file.delete();
                        }
                    } catch (Exception e){
                        try{
                            if (e.getMessage().contains("is being used by")){
                                if (Program.sessions.containsKey(id1) && Program.sessions.get(id1).files_on_transfer.containsKey(file.getName())){
                                    Program.sessions.get(id1).files_on_transfer.put(file.getName(), false);
                                }
                                if (Program.sessions.containsKey(id2) && Program.sessions.get(id2).files_on_transfer.containsKey(file.getName())){
                                    Program.sessions.get(id2).files_on_transfer.put(file.getName(), false);
                                }
                                Program.executor.execute(new Delete_file(file.getAbsolutePath()));
                            }
                            else e.printStackTrace();
                        }
                        catch (Exception e1){
        
                        }
                    }
                }
            }
            catch (Exception e){
                
            }
        }
    }

}
