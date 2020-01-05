/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.controller;

import com.brunomnsilva.smartgraph.graph.Digraph;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import projecto.model.DataDAOJson;
import projecto.model.DataDAOSerialization;
import projecto.model.Page;
import projecto.model.ShortestPath;
import projecto.model.WebCrawler;
import projecto.model.WebCrawlerStatistics;
import projecto.view.MainScreen;

/**
 *
 * @author Miglob
 */
public class WebcrawlerController {
    
    private static final int DEFAULT_STOPPAGE_CRITERIA = 40;

    private WebCrawler webcrawler;
    private WebCrawlerStatistics statistics;

    private MainScreen mainScreen;

    public WebcrawlerController() {
        this(new WebCrawler());
    }

    public WebcrawlerController(WebCrawler webcrawler) {
        this.webcrawler = webcrawler;
        this.statistics = new WebCrawlerStatistics(webcrawler.getDigraph());
        this.mainScreen = new MainScreen(this);
    }

    public void updateWebcrawler() {
        this.mainScreen.update();
    }

    public void run() {
        mainScreen.show();
    }

    public Digraph getDigraph() {
        return this.webcrawler.getDigraph();
    }

    public void automatic(String url, int stoppageCriteria) {
        int stop = (stoppageCriteria > 0) ? stoppageCriteria : DEFAULT_STOPPAGE_CRITERIA;
        try {
            webcrawler.automaticModelCreation(url, stop);
        } catch (IOException ex) {
            Logger.getLogger(WebcrawlerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iterative(String url) {
        try {
            webcrawler.iterativeModelCreation(url);
        } catch (IOException ex) {
            Logger.getLogger(WebcrawlerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStatistics() {
        this.statistics = new WebCrawlerStatistics(webcrawler.getDigraph());
        return this.statistics.getStatistics();
    }
    
    public void saveSerialization(){
        new DataDAOSerialization().saveData(webcrawler.getCaretaker());
    }
    
    public void saveJson(){
        new DataDAOJson("data").saveData(webcrawler.getCaretaker());
    }
    
    public void loadSerialization(){
        DataDAOSerialization data = new DataDAOSerialization();
        this.webcrawler.setCaretaker(data.loadData());
    }
    
    public void loadJson(){
        DataDAOJson data = new DataDAOJson("data");
        this.webcrawler.setCaretaker(data.loadData());
    }
    
    public void undo(){
        this.webcrawler.undo();
    }
    
    public String getShortestPath(String source){
        ShortestPath sh = new ShortestPath(webcrawler.getDigraph(), webcrawler.getVertex(source), null);
        return sh.getShortestPaths();
    }
    
    public void clear(){
        this.webcrawler = new WebCrawler();
    }
}
