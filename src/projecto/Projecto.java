/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Miglob
 */
public class Projecto extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        try {
            new WebCrawler().automaticModelCreation("https://www.google.pt", 20);
//            new WebCrawler().bosta("https://www.google.pt");
            
            
            
            
            
            /*Button btn = new Button();
            btn.setText("Say 'Hello World'");
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
