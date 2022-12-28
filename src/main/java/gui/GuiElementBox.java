package gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import mapElements.IMapElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    Image image;
    ImageView imageView;
    VBox vBox;

    public GuiElementBox(IMapElement mapElement) throws FileNotFoundException{
        try {
            this.image = new Image(new FileInputStream(mapElement.getView()));
            this.imageView = new ImageView(this.image);
            this.imageView.setFitWidth(20);
            this.imageView.setFitHeight(20);
            this.vBox = new VBox(this.imageView);
            this.vBox.setAlignment(Pos.CENTER);
        }
        catch(FileNotFoundException exception){
            throw new FileNotFoundException("File was not found." + exception);
        }
    }
    public VBox getvBox(){
        return this.vBox;
    }

}