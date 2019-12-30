/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.util.ArrayList;
import projecto.model.Page;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import projecto.model.TADDiGraph.MyDigraph;
import projecto.model.TADDiGraph.Vertex;

/**
 * Classe que serve para implementar as estatisticas referentes ao modelo
 * obtido.
 *
 * @author Miglob
 */
public class WebCrawlerStatistics {

    private MyDigraph digraph;

    /**
     * Método construtor que serve para inicialializar o digrafo.
     *
     * @param digraph o digrafo a construir.
     */
    public WebCrawlerStatistics(MyDigraph digraph) {
        this.digraph = digraph;
    }

    private MyDigraph getDigraph() {
        return digraph;
    }

    private void setDigraph(MyDigraph digraph) {
        this.digraph = digraph;
    }

    private String listEdgesAndVertexs() {

        return "Lista de arestas e vértices:\n\n" + digraph.toString();
    }

    private String getNumberOfNotFoundPages() {

        // condição de teste é a página não existir
        Predicate<Vertex> predicate = v -> ((Page) v.element()).exists() == false;

        List<Vertex> list = (List<Vertex>) digraph.vertices().stream()
                .filter(predicate)
                .collect(Collectors.toList());

        return "Número de páginas não encontradas: " + list.size();
    }

    private String getMostReferencedPage() {

        Page mostReferencedPage = null;
        int numberOfMostReferences = 0;
        Vertex vertexAux = null;

        for (Object vertex : digraph.vertices()) {

            vertexAux = (Vertex) vertex;

            int currentSize = digraph.incidentEdges((Vertex) vertex).size();
            if (currentSize > numberOfMostReferences) {
                mostReferencedPage = (Page) vertexAux.element();
                numberOfMostReferences = currentSize;
            }
        }

        return "A página mais referenciada é " + mostReferencedPage.toString() + " com " + numberOfMostReferences + " referências.";
    }

    private List<Page> getFiveMostReferencedPages() {

        ArrayList<Page> processedList = new ArrayList<>();

        Page mostReferencedPage = null;
        int numberOfMostReferences = 0;
        Vertex vertexAux = null;

        while (processedList.size() < 5 && processedList.size() < digraph.vertices().size()) {
            for (Object vertex : digraph.vertices()) {

                if (!processedList.contains(vertex)) {
                    vertexAux = (Vertex) vertex;

                    int currentSize = digraph.incidentEdges((Vertex) vertex).size();
                    if (currentSize > numberOfMostReferences) {
                        mostReferencedPage = (Page) vertexAux.element();
                        numberOfMostReferences = currentSize;
                    }
                }
            }

            processedList.add(mostReferencedPage);
        }

        return processedList;
    }

    /**
     * Método que serve para imprimir as diferentes estatisticas que são criadas
     * nesta classe.
     */
    public void printStatistics() {

        String str = "****** Estatisticas ******";
        //numero de paginas visitadas
        str += "\n\n" + getNumberOfVisitedPages();
        //listagem de vertices e arestas        
        str += "\n\n" + listEdgesAndVertexs();

        //numero páginas não encontradas
        str += "\n\n" + getNumberOfNotFoundPages();

        //numero páginas mais referenciadas
        str += "\n\n" + getMostReferencedPage();

        System.out.println(str);
    }

    //numero de paginas visitadas
    private int getNumberOfVisitedPages() {

        return digraph.numVertices();

    }
}
