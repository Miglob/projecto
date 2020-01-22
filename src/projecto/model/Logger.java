/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.model;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 *
 * @author  Miguel Lobato
 * 
 * Classe responsável por registar em ficheiro os registos dos modelos criados.
 * A classe foi projectada como um Singleton devido à necessidade de ter um único 
 * acesso aos dados e tornar a opção de registar para o ficheiro log.
 */
public class Logger {

    private static final Logger INSTANCE = new Logger();
    private final String FILE_NAME;
    private PrintWriter writter;

    private Logger() {
        this.FILE_NAME = "log.txt";
    }

    /**
     * Método que devolve a instância única da classe Logger
     *
     * @return A instância da classe Logger
     */
    public static Logger getInstance() {

        return INSTANCE;
    }

    /**
     * Método que faz o registo da execução de um modelo
     * @param destinationPageTitle um texto com a página de destino.
     * @param originPageTitle um texto com a página de origem.
     * @param url um texto com o url da página
     * @param numberOfHyperlinks um inteiro com o número de links da página
     */
    public void logRegistry(String destinationPageTitle, String originPageTitle, String url, int numberOfHyperlinks) {

        Log log = new Log(destinationPageTitle, originPageTitle, url, numberOfHyperlinks);
        
            openWritter();

            writter.append(log.toString() + "\n");

            writter.close();
        
    }


    private void openWritter() {

        try {
            File log = new File(FILE_NAME);
            if (!log.exists()) {

                log.createNewFile();
            }
            writter = new PrintWriter(new FileWriter(log, true));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
