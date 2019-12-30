/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import projecto.model.TADDiGraph.Digraph;

/**
 *
 * @author Miglob
 */
public class WebCrawlerMemento {
    
    private Digraph digraph;

    public WebCrawlerMemento(Digraph digraph) {
        this.digraph = digraph;
    }

    public Digraph getDigraph() {
        return digraph;
    }

    public void setDigraph(Digraph digraph) {
        this.digraph = digraph;
    }
    
    
}
