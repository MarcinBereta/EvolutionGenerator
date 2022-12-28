package gui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mapElements.Vector2d;
import mapManager.*;

public class SimulationWindow implements AnimalMoveInteface {

    int horizontal,vertical,width=40, height=40;
    Vector2d lowerLeft, upperRight;
    final GridPane gridPane = new GridPane();
    private MapSettings mapSettings;
    private SimulationEngine engine;
    private AbstractWorldMap map;
    public void createGrid(){
        //Example
        int xMin = 0;
        int xMax = mapSettings.mapWidth+1;
        int yMin = 0;
        int yMax = mapSettings.mapHeight+1;
        this.horizontal = xMax - xMin + 1;
        this.vertical = yMax - yMin + 1;

        for(int i = 0; i < xMax; i++){
            for(int j = 0; j < yMax; j++){
                if(i == 0 && j == 0){
                    Label label = new Label("X/Y");
                    gridPane.add(label, i, j);
                    GridPane.setHalignment(label, HPos.CENTER);
                }else if(i == 0){
                    Label label = new Label(Integer.toString(j));
                    gridPane.add(label, i, j);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else if(j == 0){
                    Label label = new Label(Integer.toString(i));
                    gridPane.add(label, i, j);
                    GridPane.setHalignment(label, HPos.CENTER);

                }
                else{
                    int newx = 0 + i - 1;
                    int newy = yMax - j - 1;
                    String[] guiType = map.getVisualisation(new Vector2d(newx, newy));
                    VBox box = new VBox();
                    box.setAlignment(Pos.CENTER);
                    Label label = new Label(guiType[1]);
                    label.setBackground(new Background(new BackgroundFill(Color.web(guiType[0]), CornerRadii.EMPTY, Insets.EMPTY)));
                    box.setPrefWidth(40);
                    box.setPrefHeight(40);
                    box.getChildren().add(label);
                    if(label != null){
                        this.gridPane.add(box, i, j, 1, 1);
                        GridPane.setHalignment(box, HPos.CENTER);
                        GridPane.setValignment(box, VPos.CENTER);
                    }
                }
            }
        }
        for(int i = 0; i < yMax; i++){
            this.gridPane.getRowConstraints().add(new RowConstraints(40));
        }
        for(int i = 0; i < xMax; i++){
            this.gridPane.getColumnConstraints().add(new ColumnConstraints(40));
        }
        this.gridPane.setGridLinesVisible(true);
    }

    public void createMap(){
        this.gridPane.getChildren().clear();
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();
        this.gridPane.setGridLinesVisible(false);
        this.gridPane.setGridLinesVisible(true);
        createGrid();

    }
    public SimulationWindow(){
        this.mapSettings = new MapSettings();
        Stage newWindow = new Stage();
        newWindow.setTitle("New Scene");
        TextField textField = new TextField("Enter your name here");
        Button button = new Button("OK");
        VBox container = new VBox(textField, button);
        container.setSpacing(15);
        container.setAlignment(Pos.CENTER);
        this.map = new Hell(mapSettings);
        this.engine = new SimulationEngine(map, mapSettings);
        engine.addObserver(this);
        Thread simulationEngineThread = new Thread(engine);
        simulationEngineThread.start();
        HBox hBox = new HBox(this.gridPane);
        Scene scene = new Scene(hBox, 600, 600);
        newWindow.setScene(scene);
        newWindow.show();
    }
    public void move() {
        Platform.runLater(() -> {
            this.gridPane.getChildren().clear();
            this.gridPane.getRowConstraints().clear();
            this.gridPane.getColumnConstraints().clear();
            this.gridPane.setGridLinesVisible(false);
            createMap();
        });
    }
}