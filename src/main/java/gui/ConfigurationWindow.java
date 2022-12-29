package gui;

import Configuration.Config;
import Configuration.ParameterValidator;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
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
    private final CheckBox csvCheckBox = new CheckBox("Save simulation statistics in CSV file");
    private final TextField csvDirectoryPathField = new TextField(Config.CSV_FILES_DIR_PATH);
    private final TextField csvFileNameField = new TextField();
    private VBox csvPathSelection;
    private final Spinner<Integer> epochCountSpinner = new Spinner<>(1, 10000, 100);
    private final Spinner<Double> epochDurationSpinner = new Spinner<>(0.1, 5, 0.5, 0.1);
    public ConfigurationWindow(){
        Stage inputWindow = new Stage();
        Image img = new Image("https://static.wixstatic.com/media/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png/v1/fill/w_637,h_800,al_c,q_90,enc_auto/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png");
        inputWindow.getIcons().add(img);
        inputWindow.setTitle("Configuration window");
        VBox configFileSelectionVbox = createConfigFileSelection();
        VBox csvBox = createCSVInputField(inputWindow);
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
                csvBox, epochCountContainer,
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

    private VBox createCSVInputField(Stage mainConfigWindow){
        //Upper box
        Label textFieldLabel = new Label("Select Directory: ");
        Button browseButton = new Button("Browse");
        HBox upperSpacingBox = new HBox();
        HBox.setHgrow(upperSpacingBox, Priority.ALWAYS);
        browseButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory");
            File directory = directoryChooser.showDialog(mainConfigWindow);
            if (directory != null) {
                csvDirectoryPathField.setText(directory.getAbsolutePath());
            }
        });
        HBox csvHBoxUpper = new HBox(textFieldLabel, upperSpacingBox, csvDirectoryPathField, browseButton);
        HBox lowerSpacingBox = new HBox();
        HBox.setHgrow(lowerSpacingBox, Priority.ALWAYS);
        HBox csvHBoxLower = new HBox(new Label ("File name: "), lowerSpacingBox, csvFileNameField);
        csvPathSelection = new VBox(csvHBoxUpper, csvHBoxLower);
        csvPathSelection.setAlignment(Pos.CENTER);
        csvPathSelection.setSpacing(10);
        VBox mainVBox = new VBox(csvCheckBox);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(20);
        csvCheckBox.selectedProperty().addListener(
                (ObservableValue <? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
                    if (!oldVal && newVal){
                        mainVBox.getChildren().add(csvPathSelection);
                    } else if (!newVal && oldVal){
                        mainVBox.getChildren().remove(csvPathSelection);
                    }
                });

        return mainVBox;
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

    private String getFullCsvFilePath() throws IllegalArgumentException{
        String csvDirPath = csvDirectoryPathField.getText();
        File directory = new File(csvDirPath);
        if (!directory.isDirectory() || !directory.exists()) {
            throw new IllegalArgumentException("Given directory for saving csv file is not valid");
        }
        String csvFileName = csvFileNameField.getText();
        if (Objects.equals(csvFileName, "")){
            throw new IllegalArgumentException("Csv file has to have a name");
        }
        String csvFilePath = csvDirPath + '/' + csvFileName + ".csv";
        File csvFile = new File(csvFilePath);
        if (csvFile.exists()){
            throw new IllegalArgumentException("There already is file: " + csvFilePath);
        }
        return csvFilePath;
    }

    private void attemptToCreateSimulation(String ConfigFileName) {
        if (csvCheckBox.isSelected()){
            try{
                String csvFilePath = getFullCsvFilePath();
                handleSimulationCreationResult(
                        ParameterValidator.startNewSimulation(
                                ConfigFileName,
                                csvFilePath,
                                epochCountSpinner.getValue(),
                                epochDurationSpinner.getValue()
                        )
                );
            }
            catch (IllegalArgumentException e){
                showError(e.getMessage());
            }
        }
        else{
            handleSimulationCreationResult(
                    ParameterValidator.startNewSimulation(
                            ConfigFileName,
                            epochCountSpinner.getValue(),
                            epochDurationSpinner.getValue()
                    )
            );
        }
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