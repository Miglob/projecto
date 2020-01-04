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
        if (currentDigraph != null) {
            this.currentDigraph = (Digraph) currentDigraph.clone();
        }
    }

    public Digraph getCurrentDigraph() {
        //return currentDigraph;
        return (Digraph) currentDigraph.clone();
    }

    public void setCurrentDigraph(Digraph currentDigraph) {
        this.currentDigraph = (Digraph) currentDigraph.clone();
    }

    public WebCrawlerMemento createMemento() {

        return new WebCrawlerMemento(this.currentDigraph);
    }

    public void setMemento(WebCrawlerMemento memento) {

        this.currentDigraph = memento.getDigraph();
    }
}
