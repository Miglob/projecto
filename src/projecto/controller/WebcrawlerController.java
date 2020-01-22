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
import projecto.model.ShortestPath;
import projecto.model.WebCrawler;
import projecto.model.WebCrawlerStatistics;
import projecto.view.MainScreen;

/**
 * 
 * @author Miglob
 * 
 * Classe que sincroniza as acções do view com as acções que são realizadas pelo
 * model
 * 
 * 
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
/**
 * Método que nos regenera a informação que está na representação gráfica da aplicação
 */
    public void updateWebcrawler() {
        this.mainScreen.update();
    }
/**
 * Método usado para iniciar a representação gráfica da aplicação
 */
    public void run() {
        mainScreen.show();
    }
/**
 * Método que nos fornece uma instância do webcrawler que representa o digrafo
 * @return uma instancia do webcrawler que representa o digrafo
 */
    public Digraph getDigraph() {
        return this.webcrawler.getDigraph();
    }
/**
 * Metodo utilizado pelo controller para usar o modelo automático de geração de modelos
 * @param url um texto com o url do modelo desejado
 * @param stoppageCriteria um inteiro que representa o critério de paragem da expansão do modelo
 */
    public void automatic(String url, int stoppageCriteria) {
        int stop = (stoppageCriteria > 0) ? stoppageCriteria : DEFAULT_STOPPAGE_CRITERIA;
        try {
            webcrawler.automaticModelCreation(url, stop);
        } catch (IOException ex) {
            Logger.getLogger(WebcrawlerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Metodo utilizado pelo controller para usar o modelo iterativo de geração de modelos
 * @param url  um texto com o url do modelo desejado
 */
    public void iterative(String url) {
        try {
            webcrawler.iterativeModelCreation(url);
        } catch (IOException ex) {
            Logger.getLogger(WebcrawlerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Método usado para se ter em texto estatisticas sobre o modelo
 * @return um texto com as estatisticas do modelo
 */
    public String getStatistics() {
        this.statistics = new WebCrawlerStatistics(webcrawler.getDigraph());
        return this.statistics.getStatistics();
    }
/**
 * Método usado pelo controller para guardar o estado de um modelo em .txt
 */    
    public void saveSerialization(){
        new DataDAOSerialization().saveData(webcrawler.getCaretaker());
    }
/**
 *  Método usado pelo controller para guardar o estado de um modelo em .json
 */    
    public void saveJson(){
        new DataDAOJson("data").saveData(webcrawler.getCaretaker());
    }
/**
 *  Método usado pelo controller para carregar o estado de um modelo em .txt
 */    
    public void loadSerialization(){
        DataDAOSerialization data = new DataDAOSerialization();
        this.webcrawler.setCaretaker(data.loadData());
    }
/**
 * Método usado pelo controller para carregar o estado de um modelo em .json
 */    
    public void loadJson(){
        DataDAOJson data = new DataDAOJson("data");
        this.webcrawler.setCaretaker(data.loadData());
    }
/**
 * Método usado pelo controller para carregar o estado anterior de um modelo.
 */    
    public void undo(){
        this.webcrawler.undo();
    }
/**
 * Método usado pelo controller para acedermos ao caminho mais de um determinado modelo
 * @param source um texto com o vertice de destino
 * @return um texto com o caminho mais curto 
 */    
    public String getShortestPath(String source){
        ShortestPath sh = new ShortestPath(webcrawler.getDigraph(), webcrawler.getVertex(source), null);
        return sh.getShortestPaths();
    }
/**
 * Método que apaga o modelo actual
 */    
    public void clear(){
        this.webcrawler = new WebCrawler();
    }
}
