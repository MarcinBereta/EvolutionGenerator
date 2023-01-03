package gui;

import Configuration.ConfigFileStructure;
import Configuration.WorldTypeParamaters;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mapElements.Vector2d;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigureYourInputWindow{
    private final Map<WorldTypeParamaters, Spinner<Integer>> paramSpinners= new HashMap<>();

    private final TextField fileNameInputField = new TextField();
    private final Label errorMsg = new Label();

    public ConfigureYourInputWindow(){
        Stage newWindow = new Stage();
        Image img = new Image("https://static.wixstatic.com/media/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png/v1/fill/w_637,h_800,al_c,q_90,enc_auto/2cd43b_2373b379948d4e0cb910c593f7edb96e~mv2.png");
        newWindow.getIcons().add(img);
        newWindow.setTitle("Evolution Generator");
        Label titleLabel = new Label("File name: ");
        fileNameInputField.setPadding(new Insets(0,0,0,5));
        HBox fileNameContainer = new HBox(titleLabel, fileNameInputField);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        WorldTypeParamaters[] paramTypeValues = WorldTypeParamaters.values();
        HBox[] paramInputContainers = new HBox[paramTypeValues.length];
        for (int i = 0; i < paramTypeValues.length; i++){
            paramInputContainers[i] = createParamatersInput(paramTypeValues[i]);
            gridPane.add(paramInputContainers[i], i % 2, i / 2);
        }

        gridPane.add(new HBox(), 1, paramTypeValues.length / 2);

        Button submitButton = new Button("Create new configuration file");
        submitButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW,CornerRadii.EMPTY, Insets.EMPTY)));
        HBox.setHgrow(submitButton, Priority.ALWAYS);
        submitButton.setOnAction(event -> attemptToCreateNewFile());
        VBox container = new VBox(fileNameContainer);
        Label infoLabel = new Label("Settings meaning: ");
        Label infoLabel1 = new Label("Map type: 0 -> Earth, 1 -> Hell");
        Label infoLabel2 = new Label("Grass type: 0 -> Toxic, 1 -> Equator");
        Label infoLabel3 = new Label("Move Type: 0 -> Fullpredestination, 1 -> Bitofmaddness");
        Label infoLabel4 = new Label("Genome Type: 0 -> Smallcorrection, 1 -> Fullrandom");
        container.getChildren().addAll(gridPane);
        container.getChildren().addAll(errorMsg, submitButton, infoLabel,infoLabel1, infoLabel2, infoLabel3, infoLabel4);

        container.setSpacing(15);
        container.setPadding(new Insets(10,10,10, 10));
        container.setAlignment(Pos.CENTER);
        container.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        newWindow.setScene(new Scene(container));

        newWindow.show();
    }

    private HBox createParamatersInput(WorldTypeParamaters paramType){
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
        HBox mainBox = new HBox(label, spacingBox, spinner);
        mainBox.setAlignment(Pos.CENTER_LEFT);
        return mainBox;
    }

    private void showError(String message){
        errorMsg.setText(message);
        errorMsg.setTextFill(Color.RED);
        errorMsg.setFont(new Font(14));
    }

    private void showSuccess(String message){
        errorMsg.setText(message);
        errorMsg.setTextFill(Color.GREEN);
        errorMsg.setFont(new Font(14));
    }

    private void attemptToCreateNewFile(){
        String fileName = fileNameInputField.getText();
        if (Objects.equals(fileName, "")){
            showError("File must have a name");
            return;
        }
        String filePath = ConfigFileStructure.CONFIG_PATH + '/' + fileName + ".txt";
        File file = new File(filePath);

        try {
            if (!file.createNewFile()) {
                showError("There is already file " + filePath);
                return;
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (WorldTypeParamaters paramType: ConfigFileStructure.CONFIG_FILE_STRUCTURE) {
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