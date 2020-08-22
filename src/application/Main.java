package application;

import application.splash.Splash;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.IOException;


public class Main extends Application {
    private Stage stage;
    private Splash splash;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(("file:src/resources/images/icons/phyton.png")));
        splash();
        stage.show();
    }

    public void fullScreen() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }

    public void splash() throws IOException {
        splash = new Splash();
        splash.show();
        stage.setScene(splash.getSplashScene());
        splash.getSplashScene().getStylesheets().add(Main.class.getResource("view/style/splash.css").toExternalForm());
        splash.getProgresser().setOnFinished(this::endSplash);
    }

    public void endSplash(ActionEvent ex) {
        splash.stopAllThreads();
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(1600),
                new KeyValue(splash.getSplashScene().getRoot().opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished(this::loadFXML);
        timeline.play();
    }

    public void loadFXML(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/mainframe.fxml"));

            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("view/style/style.css").toExternalForm());
            stage.setScene(scene);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
