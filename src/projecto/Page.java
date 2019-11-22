/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto;

/**
 *
 * @author Miglob
 */
public class Page {

    private String title;
    private String url;
    private boolean exists;

    public Page(String title, String url, boolean exists) {
        this.title = title;
        this.url = url;
        this.exists = exists;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean exists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getUrl() {
        return url;
    }

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

        return (page.getTitle() == this.getTitle() && page.getUrl() == this.getUrl());
    }
    
    @Override
    public String toString(){
        return "[" + this.title + "]";
    }
}
