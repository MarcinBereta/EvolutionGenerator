package gui;

import Configuration.Types.MapTypes;
import Configuration.WorldTypeParamaters;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mapElements.Animal;
import mapElements.MapEffects;
import mapElements.Vector2d;
import mapManager.*;

import java.io.IOException;
import java.util.*;

public class SimulationWindow implements AnimalMoveInteface{

    private int mapHeight=2;
    private int mapWidth=2;
    private int rowIndex = 0;
    int horizontal,vertical;
    final GridPane gridPane = new GridPane();
    private final MapSettings mapSettings = new MapSettings();
    private final SimulationEngine engine;
    private AbstractWorldMap map;
    private final ExportData exportData = new ExportData();
    private final XYChart.Series<Number, Number> animalsChartSeriesW1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> plantsChartSeriesW1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> avgEnergyChartSeriesW1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> avgLifeSpanChartSeriesW1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> freePlacesChartSeriesW1 = new XYChart.Series<>();

    private final XYChart.Series<Number, Number> mostPopularGenChartSeriesW1 = new XYChart.Series<>();
    private ArrayList<XYChart.Series<Number, Number>> chartSeriesArrW1;
    private final GridPane animalsContainer = new GridPane();
    private final GridPane genomeContainer = new GridPane();
    private final GridPane genContainer = new GridPane();
    private int DayCount = 0;
    Map<Circle, Vector2d> positions = new HashMap<>();

    private final LinkedList<Animal> animalsToObserve = new LinkedList<>();
    public SimulationWindow(Map<WorldTypeParamaters, Object> worldParams){

        for(Map.Entry<WorldTypeParamaters, Object> entry : worldParams.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());

            if(entry.getKey() == WorldTypeParamaters.MAP_HEIGHT){
                this.mapSettings.mapHeight= ((int) entry.getValue());
                this.mapHeight  = (int) entry.getValue();

            }
            if(entry.getKey() == WorldTypeParamaters.MAP_WIDTH){
                this.mapSettings.mapWidth = ((int) entry.getValue());
                this.mapWidth = (int) entry.getValue();

            }
            if(entry.getKey() == WorldTypeParamaters.ANIMAL_GEN_SIZE){
                this.mapSettings.genSize = (int) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.PLANT_PROFIT){
                this.mapSettings.plantProfit = (int) entry.getValue();
            }
            if(entry.getKey() == WorldTypeParamaters.START_ENERGY){
                this.mapSettings.startEnergy = (int) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.STARTING_GRASS){
                this.mapSettings.startGrass = (int) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.STARTING_ANIMALS){
                this.mapSettings.startingAnimals = (int) entry.getValue();
            }
            if(worldParams.get(WorldTypeParamaters.MAP_TYPE) == MapTypes.EARTH){
                this.map = new Earth(this.mapSettings);
            }else{
                this.map = new Hell(this.mapSettings);
            }
            if(entry.getKey() == WorldTypeParamaters.GEN_TYPE){
                this.mapSettings.genType = (MapEffects) entry.getValue();
            }
            if(entry.getKey() == WorldTypeParamaters.GRASS_TYPE){
                this.mapSettings.jungleType = (MapEffects) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.REPRODUCTION_COST){
                this.mapSettings.copulationEnergy = (int) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.DAILY_GRASS){
                this.mapSettings.dailyGrass = (int) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.DAY_COST){
                this.mapSettings.dayCost = (int) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.MOVE_TYPE){
                this.mapSettings.moveType = (MapEffects) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.REQUIRED_COPULATION_ENERGY){
                this.mapSettings.requiredCopulationEnergy = (int) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.MIN_GENS){
                this.mapSettings.minGens = (int) entry.getValue();
            }
            if (entry.getKey() == WorldTypeParamaters.MAX_GENS){
                this.mapSettings.maxGens = (int) entry.getValue();
            }

        }
        if(worldParams.get(WorldTypeParamaters.MAP_TYPE) == MapTypes.EARTH){
            this.map = new Earth(this.mapSettings);
        }else{
            this.map = new Hell(this.mapSettings);
        }

        Stage newWindow = new Stage();
        newWindow.setTitle("New Scene");
        this.engine = new SimulationEngine(map, mapSettings);
        engine.addObserver(this);
        Thread simulationEngineThread = new Thread(engine);
        simulationEngineThread.start();
        Image img = new Image("https://static.wixstatic.com/media/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png/v1/fill/w_637,h_800,al_c,q_90,enc_auto/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png");
        newWindow.getIcons().add(img);

        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button exportDataButton = new Button("Export data");
        exportDataButton.setDisable(true);
        Insets btnPadding = new Insets(30,30,20,30);

        startButton.setPadding(btnPadding);
        stopButton.setPadding(btnPadding);
        exportDataButton.setPadding(btnPadding);
        startButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,CornerRadii.EMPTY, Insets.EMPTY)));
        stopButton.setBackground(new Background(new BackgroundFill(Color.ORANGERED,CornerRadii.EMPTY, Insets.EMPTY)));
        exportDataButton.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE,CornerRadii.EMPTY, Insets.EMPTY)));

        HBox startStopButtons = new HBox(startButton, stopButton, exportDataButton);
        startStopButtons.setPadding(new Insets(0,0,0,30));
        HBox animalBox = new HBox();
        Circle animalCircle = new Circle();
        animalCircle.setFill(Color.BLUE);
        animalCircle.setRadius(10);
        animalBox.setPadding(new Insets(10,0,0,0));
        Label animalLabel = new Label("1-5 animals");
        animalBox.getChildren().addAll(animalCircle, animalLabel);

        Label newanimalLabel = new Label("Animal information displayed on mouse click:" );
        Label infoanimalLabel = new Label("Animal with the most popular gens: " );
        Label geninfolabel = new Label("Most popular gens: " );
        newanimalLabel.setFont(new Font("Arial", 16));
        newanimalLabel.setTextFill(Color.DARKRED);
        infoanimalLabel.setFont(new Font("Arial", 16));
        infoanimalLabel.setTextFill(Color.DARKRED);
        newanimalLabel.setPadding(new Insets(10,100,0,0));
        infoanimalLabel.setPadding(new Insets(30,100,0,0));
        geninfolabel.setPadding(new Insets(20,100,0,0));
        geninfolabel.setTextFill(Color.DARKRED);
        geninfolabel.setFont(new Font("Arial", 16));

        HBox grassBox = new HBox();
        Circle grassCircle = new Circle();
        grassCircle.setFill(Color.GREEN);
        grassCircle.setRadius(10);
        Label grassLabel = new Label("Grass");
        grassBox.setPadding(new Insets(10,0,0,0));
        grassBox.getChildren().addAll(grassCircle, grassLabel);

        HBox redanimalBox = new HBox();
        Circle redanimalCircle = new Circle();
        redanimalCircle.setFill(Color.RED);
        redanimalCircle.setRadius(10);
        Label redanimalLabel = new Label("+6 animals");
        redanimalBox.setPadding(new Insets(10,0,0,0));
        redanimalBox.getChildren().addAll(redanimalCircle, redanimalLabel);

        VBox legendBox = new VBox();
        Label legendLabel = new Label("Legend: ");
        legendLabel.setFont(new Font("Arial", 20));

        legendBox.getChildren().addAll(legendLabel,animalBox, grassBox, redanimalBox);

        HBox legendContainer = new HBox();
        genContainer.setPadding(new Insets(5, 100, 0, 0));
        animalsContainer.setPadding(new Insets(10, 100, 0, 0));
        genomeContainer.setPadding(new Insets(5, 100, 0, 0));
        legendContainer.getChildren().add(legendBox);
        VBox mainContainer = new VBox();
        legendBox.setPadding(new Insets(300,0,0,20));

        mainContainer.getChildren().addAll(startStopButtons, newanimalLabel,animalsContainer, geninfolabel,genContainer, infoanimalLabel,genomeContainer);
        mainContainer.setPadding(new Insets(0,0,0,55));
        HBox hBox = new HBox(this.gridPane, legendBox,mainContainer);
        Scene scene = new Scene(hBox, 1500, 800);


        animalsContainer.add(new Label("Animals: 2" ), 0,0);

        startButton.setOnAction(ev -> {
            this.engine.startSimulation();
            exportDataButton.setDisable(true);
        });


        stopButton.setOnAction(ev -> {
            this.engine.stopSimulation();
            exportDataButton.setDisable(false);
        });

        exportDataButton.setOnAction(ev -> {
            TextInputDialog dialog = new TextInputDialog("dataWorld1.csv");
            dialog.setTitle("Enter file name");
            dialog.setHeaderText("Enter the name of the file to export the data to:");
            dialog.setContentText("File name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String fileName = result.get();
                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION, "Successfully exported data to ./SimulationFiles/CSVFiles/" + fileName);
                alertSuccess.setTitle("Success!");
                Alert alertFail = new Alert(Alert.AlertType.INFORMATION, "Could not export the data. Check console for error log.");
                alertFail.setTitle("Failure!");
                try {
                    this.exportData.exportData(this.chartSeriesArrW1, fileName);
                    alertSuccess.show();
                } catch (IOException ex) {
                    System.out.println("Could not export the data. -> " + ex);
                    alertFail.show();
                }
            }
        });

        final NumberAxis xAxisW1 = new NumberAxis();
        final NumberAxis yAxisW1 = new NumberAxis();

        final LineChart<Number, Number> lineChartW1 = new LineChart<>(xAxisW1, yAxisW1);
        lineChartW1.setTitle("World Statistics");

        this.animalsChartSeriesW1.setName("Total animals");
        this.plantsChartSeriesW1.setName("Total plants");
        this.avgEnergyChartSeriesW1.setName("AVG Energy");
        this.avgLifeSpanChartSeriesW1.setName("AVG Lifespan of Dead Animals");
        this.freePlacesChartSeriesW1.setName("Total free places");
        this.mostPopularGenChartSeriesW1.setName("Most popular gen");

        lineChartW1.getData().add(this.animalsChartSeriesW1);
        lineChartW1.getData().add(this.plantsChartSeriesW1);
        lineChartW1.getData().add(this.avgEnergyChartSeriesW1);
        lineChartW1.getData().add(this.avgLifeSpanChartSeriesW1);
        lineChartW1.getData().add(this.freePlacesChartSeriesW1);
        lineChartW1.getData().add(this.mostPopularGenChartSeriesW1);

        this.chartSeriesArrW1 = new ArrayList<>() {
            {
                add(animalsChartSeriesW1);
                add(plantsChartSeriesW1);
                add(avgEnergyChartSeriesW1);
                add(avgLifeSpanChartSeriesW1);
                add(freePlacesChartSeriesW1);
                add(mostPopularGenChartSeriesW1);
            }
        };

        newWindow.setScene(scene);
        newWindow.show();
    }
    public void createGrid(){
        int xMin = 0;
        int xMax = mapWidth+1;
        int yMin = 0;
        int yMax = mapHeight+1;
        this.horizontal = xMax - xMin + 1;
        this.vertical = yMax - yMin + 1;

        for(int i = 0; i < xMax; i++){
            for(int j = 0; j < yMax; j++){
                if(i == 0 && j == 0){
                    Label label = new Label("X/Y");
                    if (150/xMax >3){
                    label.setFont(new Font((150/xMax-1)*2));}
                    else label.setFont(new Font(5));
                    gridPane.add(label, i, j);
                    GridPane.setHalignment(label, HPos.CENTER);
                }else if(i == 0){
                    Label label = new Label(Integer.toString(j));
                    if (150/xMax >3){
                        label.setFont(new Font((150/xMax-1)*2));}
                    else label.setFont(new Font(5));
                    gridPane.add(label, i, j);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                else if(j == 0){
                    Label label = new Label(Integer.toString(i));
                    if (150/xMax >3){
                        label.setFont(new Font((150/xMax-1)*2));}
                    else label.setFont(new Font(5));
                    gridPane.add(label, i, j);
                    GridPane.setHalignment(label, HPos.CENTER);

                }
                else{
                    int newx = i - 1;
                    int newy = yMax - j - 1;
                    VBox box = new VBox();
                    Circle circle = new Circle();
                    positions.put(circle, new Vector2d(newx, newy));
                    String[] guiType = map.getVisualisation(new Vector2d(newx, newy),positions, circle );
                    circle.setFill(Color.web(guiType[0]));
                    if (mapHeight> mapWidth){
                        circle.setRadius(350/yMax);}
                    else{
                        circle.setRadius(350/xMax);
                    }
                    circle.setOnMouseClicked(event -> {
                        System.out.println("Clicked on " + new Vector2d(newx, newy));
                        animalsToObserve.clear();
                        for(Animal myAnimal: map.getAnimalsAtPosition(new Vector2d(newx, newy))){
                            animalsToObserve.add(myAnimal);
                        }
                        this.generateList();
                    });

                    if (mapHeight> mapWidth){
                        box.setPrefWidth(700/xMax);
                        box.setPrefHeight(700/xMax);}
                    else{
                        box.setPrefWidth(700/yMax);
                        box.setPrefHeight(700/yMax);
                    }

                    box.getChildren().add(circle);
                    this.gridPane.add(box, i, j, 1, 1);
                    GridPane.setHalignment(box, HPos.CENTER);
                    GridPane.setValignment(box, VPos.CENTER);
                }
            }
        }
        for(int i = 0; i < yMax; i++){
            if (mapHeight > mapWidth){
                this.gridPane.getRowConstraints().add(new RowConstraints(700/yMax));}
            else{
                this.gridPane.getRowConstraints().add(new RowConstraints(700/xMax));
            }
        }
        for(int i = 0; i < xMax; i++){
            if (mapHeight > mapWidth){
                this.gridPane.getColumnConstraints().add(new ColumnConstraints(700/yMax));}
            else{
                this.gridPane.getColumnConstraints().add(new ColumnConstraints(700/xMax));
            };
        }
        this.gridPane.setGridLinesVisible(true);
        this.generateList();
    }
    private void generateList(){
        this.animalsContainer.getChildren().clear();
        this.animalsContainer.getRowConstraints().clear();
        this.animalsContainer.getColumnConstraints().clear();
        this.genomeContainer.getChildren().clear();
        this.genomeContainer.getRowConstraints().clear();
        this.genomeContainer.getColumnConstraints().clear();
        int rowNumber= 0;
        for(Animal myAnimal: animalsToObserve){
            Label animalLabel = new Label("Animal nr " + rowNumber + " at position " +myAnimal.getPosition() + " at age " +myAnimal.getAge() + " with  " + myAnimal.getEnergy() + " energy and  " + myAnimal.getChildrenCount() + " children " + (myAnimal.isDead()? " is dead" : " is alive"));
            animalLabel.setFont(new Font("Arial", 14));
            animalLabel.setTextFill(Color.PURPLE);
            this.animalsContainer.add(animalLabel, rowNumber, 0);
            rowNumber++;
        }
        this.genContainer.getChildren().clear();
        Label genslabel = new Label("Most popular gen: " + engine.getMostPopularGen());
        genslabel.setFont(new Font("Arial", 14));
        genslabel.setTextFill(Color.PURPLE);
        this.genContainer.add(genslabel, rowIndex, 0);

        for(String myAnimal : engine.animalsWithGenom()){
            Label label = new Label(myAnimal);
            label.setFont(new Font("Arial", 14));
            label.setTextFill(Color.PURPLE);
            this.genomeContainer.add(label, rowNumber, 0);
            rowNumber++;
        }
    }

    public void createMap(){
        this.gridPane.getChildren().clear();
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();
        this.gridPane.setGridLinesVisible(false);
        this.gridPane.setGridLinesVisible(true);
        createGrid();
    }

    public void move() {
        Platform.runLater(() -> {
            this.gridPane.getChildren().clear();
            this.gridPane.getRowConstraints().clear();
            this.gridPane.getColumnConstraints().clear();
            this.gridPane.setGridLinesVisible(false);
            createMap();
            //Chart data adding
            this.DayCount++;
            this.animalsChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.engine.countAnimals()));
            this.plantsChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.map.getTotalGrassAmount()));
            this.avgEnergyChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.engine.getAvgEnergy()));
            this.avgLifeSpanChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount, this.engine.getAvgLifeSpan()));
            this.freePlacesChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount,this.map.getNumberOfEmptyFields()));
            this.mostPopularGenChartSeriesW1.getData().add(new XYChart.Data<>(this.DayCount,this.engine.oneGenomePlease()));
        });
    }


}
