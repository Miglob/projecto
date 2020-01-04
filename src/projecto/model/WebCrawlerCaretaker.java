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
 */
public class WebCrawlerCaretaker implements Serializable{

    private Stack<WebCrawlerMemento> stackMementos;

    public WebCrawlerCaretaker() {

        this.stackMementos = new Stack<>();
    }

    public void saveState(WebCrawlerOriginator originator) {

        stackMementos.push(originator.createMemento());
    }

    public void restoreState(WebCrawlerOriginator originator) {
        
        System.out.println("----------------------------antes" + stackMementos.size());
        
            WebCrawlerMemento memento = stackMementos.pop();
            originator.setMemento(memento);            
            
        System.out.println("----------------------------depois" + stackMementos.size());
    }
}
