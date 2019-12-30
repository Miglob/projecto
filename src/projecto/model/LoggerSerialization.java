/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Miglob
 */
public final class LoggerSerialization extends Logger{

    private static LoggerSerialization instance;
    
    private String path;
    
    private LoggerSerialization(){
        
        super();
        this.path = "Log.dat";
    }
    
    public static LoggerSerialization getInstance(){
        
        if(instance == null){
            instance = new LoggerSerialization();
        }
        return instance;
    }

    @Override
    public void save() {
        
        try{
            
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(getLogList());
            objectOutputStream.close();
            fileOutputStream.close();
            
        }catch(IOException ex){
            
            java.util.logging.Logger.getLogger(LoggerJSON.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void load() {

        try{
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            setLogList((List<Log>) inputStream.readObject());
            inputStream.close();
            fileInputStream.close();
            
        }catch(IOException ex){
            
            java.util.logging.Logger.getLogger(LoggerJSON.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoggerSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
