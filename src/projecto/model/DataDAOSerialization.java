/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miglob
 */
public class DataDAOSerialization implements DataDAO {

    private WebCrawlerCaretaker wcc;
    private final static String FILENAME = "coisa.dat";

    @Override
    public WebCrawlerCaretaker loadData() {
        try {
            FileInputStream fileIn = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.wcc = (WebCrawlerCaretaker) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException ex) {
            Logger.getLogger(DataDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return wcc;
        }
    }

    @Override
    public void saveData(WebCrawlerCaretaker data) {

        FileOutputStream fileOut = null;
        this.wcc = data;
        
        try{
            fileOut = new FileOutputStream(FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.wcc);
            out.close();
            fileOut.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataDAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
