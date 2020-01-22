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
import projecto.model.Logger;
import projecto.model.Page;
import projecto.model.TADDiGraph.InvalidVertexException;
import projecto.model.TADDiGraph.MyDigraph;
import projecto.model.WebCrawler;
import projecto.model.WebCrawlerCaretaker;
import projecto.model.WebCrawlerOriginator;

/**
 *
 * @author Miglob
 */
public class AutomaticCrawler {

    private static MyDigraph digraph;

    /**
     * Metodo que faz a criação automatica do digrafo.
     *
     * @param url o url da página
     * @param numMaxPages o número máximo de páginas, vertices, que o modelo
     * deve ter.
     * @throws IOException excepções são lançadas se houver erros de
     * input/output.
     */
    public static void automaticModelCreation(String url, int numMaxPages, WebCrawlerOriginator originator) throws IOException {

        digraph = (MyDigraph) originator.getCurrentDigraph();

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
        page = WebCrawler.openUrlAndGetPage(url);//se existe
        inbound = digraph.insertVertex(page);
        visited.add(url);

        // a primeira página já foi visitada
        int currentNumberOfVisitedPages = 1;

        while (!queue.isEmpty() && currentNumberOfVisitedPages < numMaxPages) {
            urlAddress = queue.poll();
            page = WebCrawler.openUrlAndGetPage(urlAddress);//repete 1ª vez

            // os nós subsequentes já estão inseridos/criados, apenas o primeiro é que é manualmente
            if (!firstTime) {
                inbound = digraph.getVertex(page);
                visited.add(url);
            }

            // a página existir significa que não houve nenhum erro a abrir/aceder à página
            if (page.exists()) {

                // vai buscar todas as referencias presentes na página
                Elements references = WebCrawler.openUrlAndGetLinks(urlAddress);
                for (Element link : references) {
                    referencedUrl = link.attr("abs:href");

                    // se a página ainda não foi visitada
                    if (!visited.contains(referencedUrl)) {

                        // coloca na queue para ser futuramente gerado
                        queue.offer(referencedUrl);
                        if (currentNumberOfVisitedPages >= numMaxPages /*|| currentNumberOfVisitedPages > WebCrawler.MAX_LINKS*/) {
                            break;
                        } else {
                            // cria a ligação entre o nó(página) inicial e os nós(páginas) referidos
                            referencedPage = WebCrawler.openUrlAndGetPage(referencedUrl);//testar
                            try {
                                outbound = digraph.insertVertex(referencedPage);
                            } catch (InvalidVertexException ex) {
                                outbound = digraph.getVertex(referencedPage);
                            } finally {

                                digraph.insertEdge(outbound, inbound, referencedUrl);
                                visited.add(referencedUrl);

                                currentNumberOfVisitedPages++;

                                Logger.getInstance().logRegistry(outbound.element().toString(), inbound.element().toString(), referencedUrl, references.size());
                            }
                        }
                    }
                }
            }
            firstTime = false;
        }
    }

    /**
     * Método que nos dá a "representação" do digrafo construído
     *
     * @return A "representação" do digrafo
     */
    public static MyDigraph getDigraph() {
        return digraph;
    }
}
