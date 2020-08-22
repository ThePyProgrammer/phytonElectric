package application.model.fxcircuitry;

import application.Main;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class CircuitryFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        CellWidget node = new CellWidget();
        Pane pane = new Pane();
        pane.setPrefSize(600, 600);
        pane.getChildren().add(node);
        node.setTranslateX(300);
        node.setTranslateY(300);
        //node.setX(300);
        //node.setY(300);


        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        // System.out.println("Width, Height: "+node.getWidth()+" "+node.getHeight());
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
