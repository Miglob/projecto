/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import projecto.model.Page;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import projecto.model.TADDiGraph.MyDigraph;
import projecto.model.TADDiGraph.Vertex;

/**
 * Classe que define o modelo usado para a criação do digrafo.
 *
 * @author Miglob
 */
public class WebCrawler {

    private MyDigraph digraph;
    private WebCrawlerOriginator originator;
    private WebCrawlerCaretaker caretaker;

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

        this.digraph = (MyDigraph) originator.getCurrentDigraph();
        
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList();

        String referencedUrl = null;
        Vertex inbound = null;
        Vertex outbound = null;
        Page page = null;
        Page referencedPage = null;
        String urlAddress = null;
        boolean firstTime = true;

        // criar o primeiro nó
        queue.offer(url);
        page = openUrlAndGetPage(url);//se existe
        inbound = getDigraph().insertVertex(page);
        visited.add(url);

        // a primeira página já foi visitada
        int currentNumberOfVisitedPages = 1;

        while (!queue.isEmpty() && currentNumberOfVisitedPages < numMaxPages) {
            urlAddress = queue.poll();
            page = openUrlAndGetPage(urlAddress);//repete 1ª vez

            // os nós subsequentes já estão inseridos/criados, apenas o primeiro é que é manualmente
            if (!firstTime) {
                inbound = getDigraph().getVertex(page);
                visited.add(url);
            }

            // a página existir significa que não houve nenhum erro a abrir/aceder à página
            if (page.exists()) {

                // vai buscar todas as referencias presentes na página
                Elements references = openUrlAndGetLinks(urlAddress);
                for (Element link : references) {
                    referencedUrl = link.attr("abs:href");

                    // se a página ainda não foi visitada
                    if (!visited.contains(referencedUrl)) {

                        // coloca na queue para ser futuramente gerado
                        queue.offer(referencedUrl);
                        if (currentNumberOfVisitedPages >= numMaxPages) {
                            break;
                        } else {
                            // cria a ligação entre o nó(página) inicial e os nós(páginas) referidos
                            referencedPage = openUrlAndGetPage(referencedUrl);//testar
                            outbound = getDigraph().insertVertex(referencedPage);
                            getDigraph().insertEdge(outbound, inbound, referencedUrl);
                            visited.add(referencedUrl);

                            currentNumberOfVisitedPages++;
                        }
                    }
                }
            }
            firstTime = false;
        }
        MyDigraph digraph1 = getDigraph();
        originator.setCurrentDigraph(this.digraph);
        caretaker.saveState(originator);
    }

//    public void automaticModelCreation(String url, int numMaxPages) throws IOException {
//        Set<String> visited = new HashSet<>();
//        Queue<String> queue = new LinkedList();
//
//        int currentNumberOfVisitedPages = 0;
//
//        Page page = null;
//        String urlAddress = url;
//        
//        queue.offer(urlAddress);
//        visited.add(urlAddress);
//        
//
//        String referencedUrl = null;
//
//        Vertex outbound = null;
//        Page referencedPage = null;
//
//        while (!queue.isEmpty() && currentNumberOfVisitedPages < numMaxPages) {
//            urlAddress = queue.poll();
//            page = openUrlAndGetPage(urlAddress);
//            Vertex inbound = getDigraph().insertVertex(page);
//
//            if (page.exists()) {
//
//                Elements references = openUrlAndGetLinks(urlAddress);
//                for (Element link : references) {
//                    referencedUrl = link.attr("abs:href");
//
//                    if (!visited.contains(referencedUrl)) {
//
//                        visited.add(referencedUrl);
//                        queue.offer(referencedUrl);
//                        if (currentNumberOfVisitedPages >= numMaxPages) {
//                            break;
//                        } else {
//                            referencedPage = openUrlAndGetPage(referencedUrl);
//                            if (referencedPage.exists()) {
//                                outbound = getDigraph().insertVertex(referencedPage);
//                                getDigraph().insertEdge(outbound, inbound, referencedUrl);
//                            }
//                            currentNumberOfVisitedPages++;
//                        }
//                    }
//                }
//            }
//        }
//    }
    public void iterativeModelCreation(String url) throws IOException {

        this.digraph = (MyDigraph) originator.getCurrentDigraph();
        
        String referencedUrl = null;
        Vertex inbound = null;
        Vertex outbound = null;
        Page page = null;
        Page referencedPage = null;

        // criar o primeiro nó
        page = openUrlAndGetPage(url);//se existe
        inbound = getDigraph().insertVertex(page);

        // a página existir significa que não houve nenhum erro a abrir/aceder à página
        if (page.exists()) {

            // vai buscar todas as referencias presentes na página
            Elements references = openUrlAndGetLinks(url);
            for (Element link : references) {
                referencedUrl = link.attr("abs:href");

                // se a página ainda não foi visitada
                // cria a ligação entre o nó(página) inicial e os nós(páginas) referidos
                referencedPage = openUrlAndGetPage(referencedUrl);//testar
                outbound = getDigraph().insertVertex(referencedPage);
                getDigraph().insertEdge(outbound, inbound, referencedUrl);
            }
        }
        
        MyDigraph digraph1 = getDigraph();
        originator.setCurrentDigraph(this.digraph);
        caretaker.saveState(originator);

    }

    private Page openUrlAndGetPage(String urlAddress) {

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

    private Elements openUrlAndGetLinks(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        return doc.select("a[href]");
    }

    public void undo(){
        caretaker.restoreState(this.originator);
        this.digraph = (MyDigraph) originator.getCurrentDigraph();
    }
    
    public WebCrawlerCaretaker getCaretaker(){
        return caretaker;
    }
    
    public void setCaretaker(WebCrawlerCaretaker caretaker){
        this.caretaker = caretaker;
        caretaker.restoreState(this.originator);
        this.digraph = (MyDigraph) this.originator.getCurrentDigraph();
    }
}
