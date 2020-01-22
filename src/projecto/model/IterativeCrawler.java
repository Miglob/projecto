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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import projecto.model.TADDiGraph.InvalidVertexException;
import projecto.model.TADDiGraph.MyDigraph;
import static projecto.model.WebCrawler.openUrlAndGetLinks;
import static projecto.model.WebCrawler.openUrlAndGetPage;

/**
 *
 * @author Miglob
 */
public class IterativeCrawler {

    private static MyDigraph digraph;

    /**
     * Metodo que faz a criação iterativa do digrafo.
     *
     * @param url o url da página
     * @throws IOException excepções são lançadas se houver erros de
     * input/output.
     */
    public static void iterativeModelCreation(String url, WebCrawlerOriginator originator) throws IOException {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList();

        digraph = (MyDigraph) originator.getCurrentDigraph();

        String referencedUrl = null;
        Vertex inbound = null;
        Vertex outbound = null;
        Page page = null;
        Page referencedPage = null;

        // criar o primeiro nó
        queue.offer(url);
        page = openUrlAndGetPage(url);//se existe
        try {
            inbound = getDigraph().insertVertex(page);
        } catch (InvalidVertexException ex) {
            inbound = getDigraph().getVertex(page);
        } finally {
            visited.add(url);

            // a página existir significa que não houve nenhum erro a abrir/aceder à página
            if (page.exists()) {

                // vai buscar todas as referencias presentes na página
                Elements references = openUrlAndGetLinks(url);
                for (Element link : references) {
                    referencedUrl = link.attr("abs:href");

                    if (!visited.contains(referencedUrl)) {
                        queue.offer(referencedUrl);
                        // se a página ainda não foi visitada
                        // cria a ligação entre o nó(página) inicial e os nós(páginas) referidos
                        referencedPage = openUrlAndGetPage(referencedUrl);//testar
                        try {
                            outbound = getDigraph().insertVertex(referencedPage);
                        } catch (InvalidVertexException ex) {
                            outbound = getDigraph().getVertex(referencedPage);
                        } finally {
                            getDigraph().insertEdge(outbound, inbound, referencedUrl);
                            visited.add(referencedUrl);

                            Logger.getInstance().logRegistry(outbound.element().toString(), inbound.element().toString(), referencedUrl, references.size());
                        }
                    }
                }
            }
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
