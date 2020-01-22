/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import com.brunomnsilva.smartgraph.graph.Digraph;

/**
 *
 * @author Miglob
 * 
 * Classe que vai criar a representação do modelo no seu estado actual
 */
public class WebCrawlerOriginator {

    private Digraph currentDigraph;

    public WebCrawlerOriginator(Digraph currentDigraph) {
        if (currentDigraph != null) {
            this.currentDigraph = (Digraph) currentDigraph.clone();
        }
    }
/**
 * Método para se aceder ao digrafo no seu estado actual
 * @return uma cópia do digrafo no seu estado actual
 */
    public Digraph getCurrentDigraph() {
        //return currentDigraph;
        return (Digraph) currentDigraph.clone();
    }
/**
 * Método para fornecer um digrafo que representa o estado actual
 * @param currentDigraph o digrafo desejado
 */
    public void setCurrentDigraph(Digraph currentDigraph) {
        this.currentDigraph = (Digraph) currentDigraph.clone();
    }
/**
 * Método que cria um memento contendo a representação do seu estado actual
 * @return um memento que representa o estado actual do modelo
 */
    public WebCrawlerMemento createMemento() {

        return new WebCrawlerMemento(this.currentDigraph);
    }
/**
 * Método para fornecer o determinado estado do modelo que se deseja
 * @param memento o estado que se deseja do modelo
 */
    public void setMemento(WebCrawlerMemento memento) {

        this.currentDigraph = memento.getDigraph();
    }
}
