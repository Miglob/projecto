/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

/**
 *
 * @author Miglob
 * 
 * Interface utilizada pelas classes Data para salvar e carregar determinado estado
 */
public interface DataDAO {
    
    public void saveData(WebCrawlerCaretaker data);
    public WebCrawlerCaretaker loadData();

}
