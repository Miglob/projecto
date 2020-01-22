/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Miglob
 * 
 * Classe DAO que gere os dados guardados em ficheiro no formato Json.
 * Implementa a interface DataDAO
 */
public class DataDAOJson implements DataDAO {

    private String basePath;

    public DataDAOJson(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void saveData(WebCrawlerCaretaker data) {

        String path = basePath + ".json";
        try {
            Writer wr = new FileWriter(path);
            Gson gson = new GsonBuilder().create();
            gson.toJson(data, wr);
            wr.close();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public WebCrawlerCaretaker loadData() {

        String path = basePath + ".json";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Gson gson = new GsonBuilder().create();
            WebCrawlerCaretaker wcc = gson.fromJson(br, WebCrawlerCaretaker.class);
            br.close();

            return wcc;

        } catch (IOException ex) {
            System.err.println(ex.getMessage());

        }
        return null;

    }

}
