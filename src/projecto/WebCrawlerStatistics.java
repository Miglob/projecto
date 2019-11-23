/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import projecto.TADDiGraph.MyDigraph;
import projecto.TADDiGraph.Vertex;

/**
 *
 * @author Miglob
 */
public class WebCrawlerStatistics {

    private MyDigraph digraph;

    public WebCrawlerStatistics(MyDigraph digraph) {
        this.digraph = digraph;
    }

    private  MyDigraph getDigraph() {
        return digraph;
    }

    private  void setDigraph(MyDigraph digraph) {
        this.digraph = digraph;
    }
    
    private String listEdgesAndVertexs(){
        
        return "Lista de arestas e vértices:\n\n" + digraph.toString();
    }    

    private  String getNumberOfNotFoundPages() {

        // condição de teste é a página não existir
        Predicate<Vertex> predicate = v -> ((Page) v.element()).exists() == false;

        List<Vertex> list = (List<Vertex>) digraph.vertices().stream()
                .filter(predicate)
                .collect(Collectors.toList());

        return "Número de páginas não encontradas: " + list.size();
    }

    private  String getMostReferencedPage() {

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
    
    public void printStatistics(){
        
        String str = "****** Estatisticas ******";
        
        //listagem de vertices e arestas        
        str += "\n\n" + listEdgesAndVertexs();
        
        //numero páginas não encontradas
        str += "\n\n" + getNumberOfNotFoundPages();
        
        //numero páginas não encontradas
        str += "\n\n" + getMostReferencedPage();
        
        System.out.println(str);
    }
}
