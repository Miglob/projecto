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
 */
public class WebCrawlerMemento implements Serializable{
    
    private Digraph digraph;

    public WebCrawlerMemento(Digraph digraph) {
        this.digraph = (Digraph) digraph.clone();
    }

    public Digraph getDigraph() {
        return digraph;
    }

    public void setDigraph(Digraph digraph) {
        this.digraph = (Digraph) digraph.clone();;
    }
    
    
}
