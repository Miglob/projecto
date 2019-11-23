/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import projecto.TADDiGraph.Edge;
import projecto.TADDiGraph.MyDigraph;
import projecto.TADDiGraph.Vertex;

/**
 *
 * @author Miglob
 */
public class WebCrawler {

    private MyDigraph digraph;

    public WebCrawler() {
        this.digraph = new MyDigraph();
    }

    public MyDigraph getDigraph() {
        return digraph;
    }

    public void automaticModelCreation(String url, int numMaxPages) throws IOException {
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

            // os nós subsequentes já estão inseridos/criados, apenas o primenro é que é manualmente
            if (!firstTime) {
                inbound = getDigraph().getVertex(page);
                visited.add(url);
            }

            // a página existir significa que não houve nenhum erro a abrir/aceder à página
            if (page.exists()) {

                // vai buscar todas as referencias presentez na página
                Elements references = openUrlAndGetLinks(urlAddress);
                for (Element link : references) {
                    referencedUrl = link.attr("abs:href");

                    // se a página ainda não foi visitada
                    if (!visited.contains(referencedUrl)) {

                        // coloca na queue para er futuramente gerado
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

}
