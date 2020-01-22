/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.io.Serializable;
import com.brunomnsilva.smartgraph.graph.Digraph;

/**
 *
 * @author Miglob
 * 
 * Classe que guarda um estado do modelo clonando-o
 */
public class WebCrawlerMemento implements Serializable{
    
    private Digraph digraph;

    public WebCrawlerMemento(Digraph digraph) {
        this.digraph = (Digraph) digraph.clone();
    }
/**
 * Método que nos fornece o digrafo
 * @return o estado do modelo, do digrafo gerado
 */
    public Digraph getDigraph() {
        return digraph;
    }
/**
 * Método para criar o digrafo desejado
 * @param digraph o modelo desejado
 */
    public void setDigraph(Digraph digraph) {
        this.digraph = (Digraph) digraph.clone();
    }
    
    
}
