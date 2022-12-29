package gui;

import Configuration.WorldParamType;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import mapElements.Vector2d;
import mapManager.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SimulationWindow implements AnimalMoveInteface{

    int horizontal,vertical,width=40, height=40;
    Vector2d lowerLeft, upperRight;
    final GridPane gridPane = new GridPane();
    private MapSettings mapSettings;
    private SimulationEngine engine;
    private AbstractWorldMap map;
    private final ExportData exportData = new ExportData();
    private final XYChart.Series<Number, Number> animalsChartSeriesW1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> plantsChartSeriesW1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> avgEnergyChartSeriesW1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> avgKidsChartSeriesW1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> avgLifeSpanChartSeriesW1 = new XYChart.Series<>();
    private ArrayList<XYChart.Series<Number, Number>> chartSeriesArrW1;
    private int DayCount = 0;

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
                    Circle circle = new Circle();
                    circle.setFill(Color.web(guiType[0]));
                    circle.setRadius(20);
                    box.setPrefWidth(40);
                    box.setPrefHeight(40);
                    box.getChildren().add(circle);
                    this.gridPane.add(box, i, j, 1, 1);
                    GridPane.setHalignment(box, HPos.CENTER);
                    GridPane.setValignment(box, VPos.CENTER);
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
    public SimulationWindow(Map<WorldParamType, Object> worldParams){
        for(Map.Entry<WorldParamType, Object> entry : worldParams.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());

        }
        if((int) worldParams.get(WorldParamType.MAP_VARIANT) == 0){
            this.map = new Earth(this.mapSettings);
        }else{
            this.map = new Hell(this.mapSettings);
        }
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
        Image img = new Image("https://static.wixstatic.com/media/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png/v1/fill/w_637,h_800,al_c,q_90,enc_auto/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png");
        newWindow.getIcons().add(img);

        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button exportDataButton = new Button("Export current chart data");
        exportDataButton.setDisable(true);
        Insets btnPadding = new Insets(30,30,20,30);

        startButton.setPadding(btnPadding);
        stopButton.setPadding(btnPadding);
        exportDataButton.setPadding(btnPadding);

        HBox startStopButtons = new HBox(startButton, stopButton, exportDataButton);
        HBox animalBox = new HBox();
        Circle animalCircle = new Circle();
        animalCircle.setFill(Color.BLUE);
        animalCircle.setRadius(10);
        Label animalLabel = new Label("Animal");
        animalBox.getChildren().addAll(animalCircle, animalLabel);

        HBox grassBox = new HBox();
        Circle grassCircle = new Circle();
        grassCircle.setFill(Color.GREEN);
        grassCircle.setRadius(10);
        Label grassLabel = new Label("Grass");
        grassBox.getChildren().addAll(grassCircle, grassLabel);

        VBox legendBox = new VBox();
        legendBox.getChildren().addAll(animalBox, grassBox);
        legendBox.setLayoutX(400);
        legendBox.setLayoutY(400);
        HBox legendContainer = new HBox();
        legendContainer.setLayoutX(400); // ustaw pozycję X na 400
        legendContainer.setLayoutY(400); // ustaw pozycję Y na 400
        legendContainer.getChildren().add(legendBox);

        HBox hBox = new HBox(this.gridPane, legendBox,startStopButtons);
        Scene scene = new Scene(hBox, 1000, 600);

        startButton.setOnAction(ev -> {
            this.engine.startSimulation();
            exportDataButton.setDisable(true);
        });


        stopButton.setOnAction(ev -> {
            this.engine.stopSimulation();
            exportDataButton.setDisable(false);
        });

        exportDataButton.setOnAction(ev -> {
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION, "Successfully exported data to ./CSVFiles/dataWorld1.csv");
            alertSuccess.setTitle("Success!");
            Alert alertFail = new Alert(Alert.AlertType.INFORMATION, "Could not export the chart data. Check console for error log.");
            alertFail.setTitle("Failure!");
            try {
                this.exportData.exportDataFromChartSeries(this.chartSeriesArrW1, "dataWorld1.csv");
                alertSuccess.show();
            } catch (IOException ex) {
                System.out.println("Could not export the chart data. -> " + ex);
                alertFail.show();
            }
        });

        final NumberAxis xAxisW1 = new NumberAxis();
        final NumberAxis yAxisW1 = new NumberAxis();

        final LineChart<Number, Number> lineChartW1 = new LineChart<>(xAxisW1, yAxisW1);
        lineChartW1.setTitle("World 1. Statistics");

        this.animalsChartSeriesW1.setName("Total animals");
        this.plantsChartSeriesW1.setName("Total plants");
        this.avgEnergyChartSeriesW1.setName("AVG Energy");
        this.avgKidsChartSeriesW1.setName("AVG Children of Alive Animals");
        this.avgLifeSpanChartSeriesW1.setName("AVG Lifespan of Dead Animals");

        lineChartW1.getData().add(this.animalsChartSeriesW1);
        lineChartW1.getData().add(this.plantsChartSeriesW1);
        lineChartW1.getData().add(this.avgEnergyChartSeriesW1);
        lineChartW1.getData().add(this.avgKidsChartSeriesW1);
        lineChartW1.getData().add(this.avgLifeSpanChartSeriesW1);

        this.chartSeriesArrW1 = new ArrayList<>() {
            {
                add(animalsChartSeriesW1);
                add(plantsChartSeriesW1);
                add(avgEnergyChartSeriesW1);
                add(avgKidsChartSeriesW1);
                add(avgLifeSpanChartSeriesW1);
            }
        };

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
            this.DayCount++;
            this.animalsChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.engine.countAnimals()));
            this.plantsChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.map.getTotalGrassAmount()));
            this.avgEnergyChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.engine.getAvgEnergy()));
            this.avgKidsChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.engine.getAvgChildrenAmount()));
            this.avgLifeSpanChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.engine.getAvgLifeSpan()));

        });
    }


}