/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Miglob
 */
public class Log implements Serializable{
    
    private LocalDateTime timestamp;
    private String destinationPageTitle;
    private String originPageTitle;
    private String url;
    private int numberOfHyperlinks;

    public Log(String destinationPageTitle, String originPageTitle, String url, int numberOfHyperlinks) {
        this.timestamp = LocalDateTime.now();
        this.destinationPageTitle = destinationPageTitle;
        this.originPageTitle = originPageTitle;
        this.url = url;
        this.numberOfHyperlinks = numberOfHyperlinks;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDestinationPageTitle() {
        return destinationPageTitle;
    }

    public void setDestinationPageTitle(String destinationPageTitle) {
        this.destinationPageTitle = destinationPageTitle;
    }

    public String getOriginPageTitle() {
        return originPageTitle;
    }

    public void setOriginPageTitle(String originPageTitle) {
        this.originPageTitle = originPageTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumberOfHyperlinks() {
        return numberOfHyperlinks;
    }

    public void setNumberOfHyperlinks(int numberOfHyperlinks) {
        this.numberOfHyperlinks = numberOfHyperlinks;
    }

    @Override
    public String toString() {
        return  getFormattedTimeStamp() + "\t|\t" + destinationPageTitle +"\t|\t" + url + "\t|\t" + originPageTitle + "\t|\t" + numberOfHyperlinks;
    }
    
    
    private String getFormattedTimeStamp(){
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        String formattedTimeStamp = timestamp.format(dateTimeFormatter);
        
        return formattedTimeStamp;
    }
    
    
}
