package gui;

import Configuration.ConfigFileStructure;
import Configuration.ParameterValidator;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StartingWindow {
    private final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(listFiles(ConfigFileStructure.CONFIG_PATH)));
    private final Label errorMsg;
    public StartingWindow(){
        VBox root = new VBox();
        root.setStyle("-fx-background-color: lightblue;");
        Stage inputWindow = new Stage();

        Image img = new Image("https://static.wixstatic.com/media/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png/v1/fill/w_637,h_800,al_c,q_90,enc_auto/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png");
        inputWindow.getIcons().add(img);

        inputWindow.setTitle("Evolution Generator");
        VBox configFileSelectionVbox = createConfigFileSelection();
        errorMsg = new Label();
        errorMsg.setTextFill(Color.RED);
        Button submitButton = new Button("Create New Simulation");
        submitButton.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW,CornerRadii.EMPTY, Insets.EMPTY)));

        submitButton.setOnAction(actionEvent -> {
            String val = choiceBox.getValue();
            if (val == null){
                showError("Configuration file must be chosen");
                return;
            }
            tryToCreateSimulation(val);
        });

        VBox container = new VBox(configFileSelectionVbox, errorMsg, submitButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(30);
        container.setSpacing(20);
        container.setPadding(new Insets(12,12,12, 12));
        container.setAlignment(Pos.CENTER);
        container.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
        inputWindow.setScene(new Scene(container));
        inputWindow.setWidth(400);
        inputWindow.setHeight(400);
        inputWindow.show();
    }


    private VBox createConfigFileSelection(){
        Label titleLabel = new Label(("Evolution Generator"));
        titleLabel.setPadding(new Insets(0,0,10,0));
        titleLabel.setFont(new Font("Arial", 16));
        titleLabel.setTextFill(Color.DARKRED);
        Label choiceBoxLabel = new Label("Choose configuration file");
        choiceBoxLabel.setFont(new Font(14));
        Button RefreshButton = new Button("\uD83D\uDD04");
        RefreshButton.setBackground(new Background(new BackgroundFill(Color.GREEN,CornerRadii.EMPTY, Insets.EMPTY)));

        RefreshButton.setOnAction(event -> {
            choiceBox.getItems().clear();
            choiceBox.getItems().addAll(listFiles(ConfigFileStructure.CONFIG_PATH));
        });

        Button newConfigFileButton = new Button("New File");
        newConfigFileButton.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE,CornerRadii.EMPTY, Insets.EMPTY)));
        newConfigFileButton.setOnAction(event -> new ConfigureYourInputWindow());
        HBox choiceContainer = new HBox(choiceBox, RefreshButton, newConfigFileButton);
        VBox configFileSelection = new VBox(titleLabel,choiceBoxLabel, choiceContainer);
        choiceContainer.setAlignment(Pos.CENTER);
        configFileSelection.setAlignment(Pos.CENTER);
        return configFileSelection;
    }
    public List<String> listFiles(String dirPath) {
        try{
            return Stream.of(Objects.requireNonNull(new File(dirPath).listFiles()))
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toList());
        }
        catch (NullPointerException e){
            System.out.print(e.getMessage());
            return new ArrayList<>();
        }
    }


    private void tryToCreateSimulation(String ConfigFileName) {
        handleSimulationCreationResult(ParameterValidator.startNewSimulation(ConfigFileName));
    }

    private void showError(String message){
        errorMsg.setText(message);
        errorMsg.setTextFill(Color.RED);
    }
    private void showSuccess(){
        errorMsg.setText("Successfully created new simulation");
        errorMsg.setTextFill(Color.GREEN);
    }
    private void handleSimulationCreationResult(String creationResult){
        if (Objects.equals(creationResult, "")){
            showSuccess();
        }
        else{
            showError(creationResult);
        }
    }


}