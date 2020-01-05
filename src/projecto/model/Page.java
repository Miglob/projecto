/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.io.Serializable;

/**
 * Classe que cria uma pagina, vertice, sendo-lhe dada como atributos um titulo, um url e 
 * um atributo para ver se a pagina existe
 * 
 * 
 * @author Miglob
 */
public class Page implements Serializable{

    private String title;
    private String url;
    private boolean exists;
/**
 * 
 * @param title o titulo da página
 * @param url o endereço da página
 * @param exists um booleano para ver se a página existe
 */
    public Page(String title, String url, boolean exists) {
        this.title = title;
        this.url = url;
        this.exists = exists;
    }
/**
 * Método para nos dar o titulo da página
 * 
 * @return Retorna o titulo da página
 */
    public String getTitle() {
        return title;
    }
/**
 * Método para criar o titulo de uma página
 * 
 * @param title uma String que representa o titulo da página
 */
    public void setTitle(String title) {
        this.title = title;
    }
/**
 * Método que através de um booleano nos diz se a página existe
 * 
 * @return true se a página existir
 */
    public boolean exists() {
        return exists;
    }
/**
 * Método para definir se uma página existe.
 * 
 * @param exists um booleano para a definição da existência da página.
 */
    public void setExists(boolean exists) {
        this.exists = exists;
    }
/**
 * Método para obtermos a url de uma página.
 * 
 * @return Retorna uma String que nos dá o url de uma certa página.
 */
    public String getUrl() {
        return url;
    }
/**
 * Método para definir uma url de uma página.
 * 
 * @param url  uma String que representa a url de uma página.
 */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Page page = (Page) obj;

        return (page.getTitle().equals(title) && page.getUrl().equals(url));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getTitle().hashCode();
        result = 31 * result + this.getUrl().hashCode();
        return result;
    }
    
    @Override
    public String toString(){
        return "[" + this.title + "]" + " - " + this.url;
    }
}
