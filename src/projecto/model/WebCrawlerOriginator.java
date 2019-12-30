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
public class WebCrawlerOriginator {
    
    private Digraph currentDigraph;

    public WebCrawlerOriginator(Digraph currentDigraph) {
        this.currentDigraph = currentDigraph;
    }

    public Digraph getCurrentDigraph() {
        return currentDigraph;
    }

    public void setCurrentDigraph(Digraph currentDigraph) {
        this.currentDigraph = currentDigraph;
    }
    
    public WebCrawlerMemento createMemento(){
        
        return new WebCrawlerMemento(this.currentDigraph);
    }
    
    public void setMemento(WebCrawlerMemento memento){
        
        this.currentDigraph = memento.getDigraph();
    }
}
