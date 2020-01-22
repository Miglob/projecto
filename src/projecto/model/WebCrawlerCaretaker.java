/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.io.Serializable;
import java.util.Stack;

/**
 *
 * @author Miglob
 * 
 * Classe responsável por armazenar numa pilha os mementos criados pela WebCrawlerOriginator
 */
public class WebCrawlerCaretaker implements Serializable {

    private Stack<WebCrawlerMemento> stackMementos;

    public WebCrawlerCaretaker() {

        this.stackMementos = new Stack<>();
    }
/**
 * Método que guarda numa pilha um determinado estado de um modelo
 * @param originator um estado de um modelo
 */
    public void saveState(WebCrawlerOriginator originator) {

        stackMementos.push(originator.createMemento());
    }
/**
 * Método que se utiliza para retornar ao estado que foi guardado
 * @param originator o estado que foi guardado na pilha de mementos
 */
    public void restoreState(WebCrawlerOriginator originator) {


        if (stackMementos.size() > 0) {
            WebCrawlerMemento memento = stackMementos.pop();
            originator.setMemento(memento);
        }

    }
}
