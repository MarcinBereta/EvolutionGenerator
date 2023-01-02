package gui;

import Configuration.Config;
import Configuration.ParameterValidator;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigurationWindow {
    private final ChoiceBox<String> choiceBox =
            new ChoiceBox<>(FXCollections.observableArrayList(listFiles(Config.CONFIG_DIR_PATH)));
    private final Label errorMsg;

    private final Spinner<Integer> epochCountSpinner = new Spinner<>(1, 10000, 100);
    private final Spinner<Double> epochDurationSpinner = new Spinner<>(0.1, 5, 0.5, 0.1);
    public ConfigurationWindow(){
        Stage inputWindow = new Stage();
        Image img = new Image("https://static.wixstatic.com/media/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png/v1/fill/w_637,h_800,al_c,q_90,enc_auto/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png");
        inputWindow.getIcons().add(img);
        inputWindow.setTitle("Configuration window");
        VBox configFileSelectionVbox = createConfigFileSelection();
        errorMsg = new Label();
        errorMsg.setTextFill(Color.RED);
        Button submitButton = new Button("Create New Simulation");
        submitButton.setOnAction(actionEvent -> {
            String val = choiceBox.getValue();
            if (val == null){
                showError("Configuration file must be chosen");
                return;
            }
            attemptToCreateSimulation(val);
        });
        HBox epochCountContainer = createParamInput("Epoch count:", epochCountSpinner);
        HBox epochDurationContainer = createParamInput("Epoch Duration:", epochDurationSpinner);
        VBox container = new VBox(
                configFileSelectionVbox,
                epochCountContainer,
                epochDurationContainer,
                errorMsg,
                submitButton
        );
        container.setAlignment(Pos.CENTER);
        container.setSpacing(30);
        container.setSpacing(20);
        container.setPadding(new Insets(12,12,12, 12));
        container.setAlignment(Pos.CENTER);
        inputWindow.setScene(new Scene(container));
        inputWindow.setWidth(400);
        inputWindow.setHeight(400);
        inputWindow.show();
    }

    private HBox createParamInput(String labelMsg, Spinner spinner){
        Label label = new Label(labelMsg);
        HBox spacingBox = new HBox();
        HBox.setHgrow(spacingBox, Priority.ALWAYS);
        spinner.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(spinner, Priority.NEVER);
        HBox mainBox = new HBox(
                label,
                spacingBox,
                spinner
        );

        mainBox.setAlignment(Pos.CENTER_LEFT);
        return mainBox;
    }

    private VBox createConfigFileSelection(){
        Label choiceBoxLabel = new Label("Choose configuration file");
        Button RefreshButton = new Button("\uD83D\uDD04");
        RefreshButton.setOnAction(event -> {
            choiceBox.getItems().clear();
            choiceBox.getItems().addAll(listFiles(Config.CONFIG_DIR_PATH));
        });
        Button newConfigFileBtn = new Button("New File");
        newConfigFileBtn.setOnAction(event -> new InputConfigurationWindow());
        HBox choiceContainer = new HBox(choiceBox, RefreshButton, newConfigFileBtn);
        VBox configFileSelection = new VBox(choiceBoxLabel, choiceContainer);
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

    private void showError(String message){
        errorMsg.setText(message);
        errorMsg.setTextFill(Color.RED);
    }
    private void showSuccess(){
        errorMsg.setText("Successfully created new simulation");
        errorMsg.setTextFill(Color.GREEN);
    }


    private void attemptToCreateSimulation(String ConfigFileName) {
            handleSimulationCreationResult(
                    ParameterValidator.startNewSimulation(
                            ConfigFileName,
                            epochCountSpinner.getValue(),
                            epochDurationSpinner.getValue()
                    )
            );
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