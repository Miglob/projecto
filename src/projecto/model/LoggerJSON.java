/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Miglob
 */
public final class LoggerJSON extends Logger{
    
    private static LoggerJSON instance;
    
    private String path;
    
    private LoggerJSON(){
        
        super();
        this.path = "Logs.json";
    }
    
    public static LoggerJSON getInstance(){
        
        if(instance == null){
            instance = new LoggerJSON();
        }
        
        return instance;
    }

    @Override
    public void save() {
        
        try{
            
            Writer writer = new FileWriter(path);
            Gson gson = new GsonBuilder().create();
            gson.toJson(getLogList(), writer);
            writer.flush();
            writer.close();
            
        }catch(IOException ex){
            
            java.util.logging.Logger.getLogger(LoggerJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void load() {
        
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            Gson gson = new GsonBuilder().create();
            List<Log> list = gson.fromJson(bufferedReader, List.class);
            setLogList(list);
        }catch(IOException ex){
            java.util.logging.Logger.getLogger(LoggerJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
