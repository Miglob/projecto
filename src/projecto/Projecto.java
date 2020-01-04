/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto;

import projecto.model.WebCrawler;
import projecto.model.WebCrawlerStatistics;
import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import projecto.model.DataDAO;
import projecto.model.DataDAOSerialization;
import projecto.model.Page;
import projecto.model.ShortestPath;
import projecto.model.TADDiGraph.MyDigraph;
import projecto.model.TADDiGraph.Vertex;
import projecto.model.WebCrawlerCaretaker;
import projecto.model.WebCrawlerOriginator;

/**
 *
 * @author Miglob
 */
public class Projecto extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            WebCrawler webcrawler = new WebCrawler();
            webcrawler.automaticModelCreation("https://www.google.pt", 40);

            new WebCrawlerStatistics(webcrawler.getDigraph()).printStatistics();

            projecto.model.Logger.getInstance().logRegistry("abc", "edf", "url", 3);

            DataDAO data = new DataDAOSerialization();

            webcrawler.iterativeModelCreation("https://www.google.com/search/about/gfhjgjg");

            System.out.println("\n\n***** bosta *****\n\n");

            ShortestPath sp = new ShortestPath(webcrawler.getDigraph(), webcrawler.getDigraph().getVertex(new Page("Google", "https://www.google.pt", true)), webcrawler.getDigraph().getVertex(new Page("Google Search - Stay in the Know with Your Google App", "https://www.google.com/search/about/", true)));
            Stack<Vertex> shortestPath = sp.getShortestPath();

            for (Vertex v : shortestPath) {
                System.out.println(v.toString());
            }
            /*
            WebCrawlerCaretaker caretaker = webcrawler.getCaretaker();
            data.saveData(caretaker);
            WebCrawlerCaretaker loadData = data.loadData();
            webcrawler.setCaretaker(loadData);
            new WebCrawlerStatistics(webcrawler.getDigraph()).printStatistics();
            webcrawler.undo();
            new WebCrawlerStatistics(webcrawler.getDigraph()).printStatistics();
            //loadData.saveState(new WebCrawlerOriginator(webcrawler.getDigraph()));
            /*Button btn = new Button();
            btn.setText("Say 'Hello World'")
            btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            System.out.println("Hello World!");
            }
            });
            StackPane root = new StackPane();
            root.getChildren().add(btn);
            Scene scene = new Scene(root, 300, 250);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();*/
        } catch (IOException ex) {
            Logger.getLogger(Projecto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
