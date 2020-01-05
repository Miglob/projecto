/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecto.view;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphProperties;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertexNode;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projecto.controller.WebcrawlerController;
import projecto.model.Page;

/**
 *
 * @author Miglob
 */
public class MainScreen extends Pane {

    private WebcrawlerController controller;

    private boolean keyPressed;

    private HBox hbox;
    private ScrollPane scrollPane;
    private VBox leftSideVBox;
    private VBox rightSideVBox;

    private SmartGraphPanel graphView;
    private SmartGraphProperties properties;
    private SmartPlacementStrategy strategy;

    private Label statisticsLabel;
    private Text statisticsText;
    private Label urlLabel;
    private TextField urlText;
    private Label modeLabel;
    private TextField stoppageCriteriaText;

    private Button btnGo;
    private Button btnUndo;
    private Button btnLoad;
    private Button btnSave;
    private Button btnShortestPath;
    private Button btnClear;

    private ToggleGroup tglMode;

    private RadioButton btnAutomatic;
    private RadioButton btnIterative;

    private Scene scene;
    private Stage stage;

    public MainScreen(WebcrawlerController controller) {
        this.controller = controller;

        this.hbox = new HBox();
        this.scrollPane = new ScrollPane();
        this.leftSideVBox = new VBox();
        this.rightSideVBox = new VBox();
        this.strategy = new SmartCircularSortedPlacementStrategy();
        this.properties = new SmartGraphProperties();
        this.graphView = (controller != null) ? new SmartGraphPanel(this.controller.getDigraph(), properties, strategy) : null;
        this.graphView.setAutomaticLayout(true);
        this.statisticsLabel = new Label("Estatísticas:");
        this.statisticsText = new Text("");
        this.urlLabel = new Label("URL:");
        this.urlText = new TextField();
        this.btnGo = new Button("Ok");
        this.btnLoad = new Button("Carregar");
        this.btnSave = new Button("Salvar");
        this.btnUndo = new Button("Desfazer");
        this.btnShortestPath = new Button("Caminho mais curto");
        this.tglMode = new ToggleGroup();
        this.btnAutomatic = new RadioButton("Automatico");
        this.btnIterative = new RadioButton("Iterativo");
        this.modeLabel = new Label("Modo:");
        this.stoppageCriteriaText = new TextField();
        this.keyPressed = false;
        this.btnClear = new Button("Limpar");

        setup();
    }

    private void setup() {
        this.hbox.setMinHeight(300);
        this.hbox.setMinWidth(200);
        this.hbox.setMaxHeight(Double.MAX_VALUE);
        this.hbox.setMaxWidth(Double.MAX_VALUE);
        this.hbox.setSpacing(10);
        this.hbox.setPadding(new Insets(20, 20, 20, 20));

        this.btnAutomatic.setToggleGroup(tglMode);
        this.btnIterative.setToggleGroup(tglMode);
        this.btnAutomatic.setSelected(true);

        HBox buttonsMode = new HBox(btnAutomatic, btnIterative);
        buttonsMode.setSpacing(10);

        setupGraphview();

        this.stoppageCriteriaText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    stoppageCriteriaText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }

        });

        this.leftSideVBox.setSpacing(10);
        this.leftSideVBox.setMinHeight(600);
        this.leftSideVBox.setMinWidth(800);
        this.leftSideVBox.setMaxHeight(Double.MAX_VALUE);
        this.leftSideVBox.setMaxWidth(Double.MAX_VALUE);

        this.leftSideVBox.getChildren().add(this.graphView);

        this.rightSideVBox.setSpacing(10);
        HBox temp = new HBox(this.urlText, this.stoppageCriteriaText, this.btnGo);
        temp.setSpacing(10);
        this.rightSideVBox.getChildren().add(this.urlLabel);
        this.rightSideVBox.getChildren().add(temp);
        this.rightSideVBox.getChildren().add(this.modeLabel);
        this.rightSideVBox.getChildren().add(buttonsMode);
        this.rightSideVBox.getChildren().add(this.btnLoad);
        this.rightSideVBox.getChildren().add(this.btnSave);
        this.rightSideVBox.getChildren().add(this.btnUndo);
        this.rightSideVBox.getChildren().add(this.btnShortestPath);
        this.rightSideVBox.getChildren().add(this.btnClear);

        this.rightSideVBox.getChildren().add(this.statisticsLabel);
        this.scrollPane.setContent(this.statisticsText);
        this.scrollPane.setPadding(new Insets(10));
        this.rightSideVBox.getChildren().add(this.scrollPane);

        this.hbox.getChildren().addAll(leftSideVBox, rightSideVBox);

        this.btnGo.setOnMouseClicked((event) -> {
            String url = urlText.getText();
            String inputStop = "";
            int stoppage;
            switch (((RadioButton) (tglMode.getSelectedToggle())).getText()) {
                case "Automatico":
                    inputStop = this.stoppageCriteriaText.getText();
                    stoppage = (inputStop.equals("")) ? 1 : Integer.parseInt(inputStop);
                    controller.automatic(url, stoppage);
                    break;
                case "Iterativo":
                    controller.iterative(url);
                    break;
                default:
                    inputStop = this.stoppageCriteriaText.getText();
                    stoppage = (inputStop.equals("")) ? 1 : Integer.parseInt(inputStop);
                    controller.automatic(url, stoppage);
            }

            update();
            this.statisticsText.setText(controller.getStatistics());
        });

        this.btnSave.setOnMouseClicked((event) -> {
            controller.saveSerialization();

        });

        this.btnLoad.setOnMouseClicked((event) -> {
            controller.loadSerialization();
            update();
        });

        this.btnUndo.setOnMouseClicked((event) -> {
            controller.undo();
            update();
        });

        this.btnShortestPath.setOnMouseClicked((event) -> {
            statisticsText.setText(controller.getShortestPath(urlText.getText()));
        });
        
        this.btnClear.setOnMouseClicked((event) -> {
            controller.clear();
            update();
        });

    }

    private void setupGraphview() {
        this.graphView.setMinHeight(600);
        this.graphView.setMinWidth(800);
        this.graphView.setMaxWidth(Double.MAX_VALUE);
        this.graphView.setMaxHeight(Double.MAX_VALUE);
        this.graphView.setVertexDoubleClickAction((graphVertex) -> {
            SmartGraphVertexNode vertex = (SmartGraphVertexNode) graphVertex;
            String url = ((Page) (vertex.getUnderlyingVertex().element())).getUrl();
            if (this.keyPressed) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {

                controller.iterative(url);

                update();
                this.statisticsText.setText(controller.getStatistics());
            }
        });
    }

    public void show() {
        this.scene = new Scene(this.hbox);
        this.scene.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.O) {
                this.keyPressed = true;
            }
        });

        this.scene.setOnKeyReleased((event) -> {
            this.keyPressed = false;
        });
        this.stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Webcrawler");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        //IMPORTANT - Called after scene is displayed so we can have width and height values
        this.graphView.init();
    }

    public void update() {
        this.graphView.setTheGraph(this.controller.getDigraph());
        this.graphView.update();
    }
}
