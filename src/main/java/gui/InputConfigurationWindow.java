package gui;

import Configuration.Config;
import Configuration.WorldParamType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mapElements.Vector2d;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InputConfigurationWindow {
    private final Map<WorldParamType, Spinner<Integer>> paramSpinners= new HashMap<>();

    private final TextField fileNameInputField = new TextField();
    private final Label errorMsg = new Label();

    public InputConfigurationWindow(){
        Stage newWindow = new Stage();
        newWindow.setTitle("Create new simulation configuration file");
        HBox spacingBox = new HBox();
        HBox.setHgrow(spacingBox, Priority.ALWAYS);
        HBox fileNameContainer = new HBox(
                new Label("File name: "),
                spacingBox,
                fileNameInputField
        );
        WorldParamType[] paramTypeValues = WorldParamType.values();
        HBox[] paramInputContainers = new HBox[paramTypeValues.length];
        for (int i = 0; i < paramTypeValues.length; i++){
            paramInputContainers[i] = createParamInput(paramTypeValues[i]);
        }
        Button submitButton = new Button("Create new configuration file");
        HBox.setHgrow(submitButton, Priority.ALWAYS);
        submitButton.setOnAction(event -> attemptToCreateNewFile());
        VBox container = new VBox(fileNameContainer);
        container.getChildren().addAll(paramInputContainers);
        container.getChildren().addAll(errorMsg, submitButton);
        container.setSpacing(15);
        container.setPadding(new Insets(10,10,10, 10));
        container.setAlignment(Pos.CENTER);
        newWindow.setScene(new Scene(container));

        newWindow.show();
    }

    private HBox createParamInput(WorldParamType paramType){
        Label label = new Label(paramType.toString() + ':');
        label.setAlignment(Pos.CENTER_RIGHT);
        HBox spacingBox = new HBox();
        HBox.setHgrow(spacingBox, Priority.ALWAYS);
        Vector2d paramValueRange = paramType.getValueRange();
        Spinner<Integer> spinner = new Spinner<>(
                paramValueRange.x,
                paramValueRange.y,
                paramType.getDefaultValue()
        );
        paramSpinners.put(paramType, spinner);
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

    private void showError(String message){
        errorMsg.setText(message);
        errorMsg.setTextFill(Color.RED);
    }

    private void showSuccess(String message){
        errorMsg.setText(message);
        errorMsg.setTextFill(Color.GREEN);
    }

    private void attemptToCreateNewFile(){
        String fileName = fileNameInputField.getText();
        if (Objects.equals(fileName, "")){
            showError("File has to have name");
            return;
        }
        String filePath = Config.CONFIG_DIR_PATH + '/' + fileName + ".txt";
        File file = new File(filePath);

        try {
            if (!file.createNewFile()) {
                showError("There already is file " + filePath);
                return;
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (WorldParamType paramType: Config.CONFIG_FILE_STRUCTURE) {
                bw.write(paramType.getKey() + ' ' + paramSpinners.get(paramType).getValue());
                bw.newLine();
            }

            bw.close();
            showSuccess("File: " + filePath + " created successfully");
        }
        catch (IOException e) {
            showError(e.getMessage());
        }
    }

}