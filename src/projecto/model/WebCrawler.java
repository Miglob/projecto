/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import com.brunomnsilva.smartgraph.graph.Vertex;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import projecto.model.TADDiGraph.InvalidVertexException;
import projecto.model.TADDiGraph.MyDigraph;

/**
 * Classe que define o modelo usado para a criação do digrafo.
 *
 * @author Miglob
 */
public class WebCrawler {

    private MyDigraph digraph;
    private WebCrawlerOriginator originator;
    private WebCrawlerCaretaker caretaker;
    
    //public static final int MAX_LINKS = 99; // não espande mais que este nº

    public WebCrawler() {
        this.digraph = new MyDigraph();
        this.caretaker = new WebCrawlerCaretaker();
        this.originator = new WebCrawlerOriginator(this.digraph);
    }

    /**
     * Método que nos dá a "representação" do digrafo construído
     *
     * @return A "representação" do digrafo
     */
    public MyDigraph getDigraph() {
        return this.digraph;
    }

    /**
     * Metodo que faz a criação automatica do digrafo.
     *
     * @param url o url da página
     * @param numMaxPages o número máximo de páginas, vertices, que o modelo
     * deve ter.
     * @throws IOException excepções são lançadas se houver erros de
     * input/output.
     */
    public void automaticModelCreation(String url, int numMaxPages) throws IOException {
        
        AutomaticCrawler.automaticModelCreation(url, numMaxPages, originator);
        this.digraph = AutomaticCrawler.getDigraph();
    
        originator.setCurrentDigraph(AutomaticCrawler.getDigraph());
        caretaker.saveState(originator);
    }

    /**
     * Metodo que faz a criação iterativa do digrafo.
     *
     * @param url o url da página
     * @throws IOException excepções são lançadas se houver erros de
     * input/output.
     */
    public void iterativeModelCreation(String url) throws IOException {
        IterativeCrawler.iterativeModelCreation(url, originator);
        this.digraph = IterativeCrawler.getDigraph();
    
        originator.setCurrentDigraph(IterativeCrawler.getDigraph());
        caretaker.saveState(originator);
    }

    public static Page openUrlAndGetPage(String urlAddress) {

        Page page = null;

        try {
            Document doc = Jsoup.connect(urlAddress).get();
            page = new Page(doc.title(), urlAddress, true);

        } catch (Exception e) {
            page = new Page("404 - Page Not Found", urlAddress, false);
        } finally {
            return page;
        }
    }

    public static Elements openUrlAndGetLinks(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        return doc.select("a[href]");
    }

    /**
     * Método para retornar ao estado anterior do modelo
     */
    public void undo() {
        caretaker.restoreState(this.originator);
        this.digraph = (MyDigraph) originator.getCurrentDigraph();
    }

    /**
     * Método que nos fornece o caretaker
     *
     * @return uma instância do webcrawlercaretaker que representa o caretaker
     */
    public WebCrawlerCaretaker getCaretaker() {
        return caretaker;
    }

    /**
     * Método para criar o caretaker
     *
     * @param caretaker o caretaker
     */
    public void setCaretaker(WebCrawlerCaretaker caretaker) {
        this.caretaker = caretaker;
        caretaker.restoreState(this.originator);
        this.digraph = (MyDigraph) this.originator.getCurrentDigraph();
    }

    /**
     * Método que nos fornece o vertice através do url
     *
     * @param url o url da página
     * @return o vertice ligado ao url
     */
    public Vertex getVertex(String url) {
        Page page = openUrlAndGetPage(url);
        return this.digraph.getVertex(page);
    }
}
