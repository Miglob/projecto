/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miglob
 */
public abstract class Logger implements LoggerDAO{

    private List<Log> logList;

    public Logger() {
        this.logList = new ArrayList<>();
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    
    public void clear() {
        this.logList.clear();
    }

    public void registerLog(String destinationPageTitle, String url, String originPageTitle, int numberOfHyperlinks) {

        if (destinationPageTitle != null && url != null && originPageTitle != null) {
            
            Log log = new Log(destinationPageTitle, originPageTitle, url, numberOfHyperlinks);
            logList.add(log); //refac
            save();
        }
    }
    
    public void registerLog(Log log){
        
        if(log != null){
            logList.add(log);
            save();
        }
    }
    
    @Override
    public abstract void save();
}
