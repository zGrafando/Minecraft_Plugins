package com.grafando.teleportation.commands;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TeleportationLogs {
    private File Logfile;
    private File Logfolder;

    public File createLogfile(){
        try {
            Logfolder = new File("C:\\Users\\Pius\\Desktop\\Plugin\\Server\\Plugins\\PluginLogs");
            Logfolder.mkdir();
            Logfile = new File("C:\\Users\\Pius\\Desktop\\Plugin\\Server\\Plugins\\PluginLogs\\Teleportation_Logs.txt");
            Logfile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return Logfile;
    }

    public void writeLogRecord(Player subject, Player object){
        try{
            FileWriter logger = new FileWriter(createLogfile());
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String coordsSubject = "("+subject.getLocation().getX()+" "+subject.getLocation().getY()+
                    " "+subject.getLocation().getZ()+")";
            String coordsObject = "("+object.getLocation().getX()+" "+object.getLocation().getY()+
                    " "+object.getLocation().getZ()+")";
            logger.write("Teleport at"+String.valueOf(timeStamp)+": "+
                    String.valueOf(subject.getPlayerListName())+coordsSubject+" -> "+
                    String.valueOf(object.getPlayerListName()+coordsObject));
            logger.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
