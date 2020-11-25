package application;

import application.model.util.fxtools.ResizeHelper;
import application.splash.Splash;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;


public class Main extends Application {
    private static Stage stage;
    private static Image icon;
    private Splash splash;
    private double xOffset = 0;
    private double yOffset = 0;

    public static Stage getStage() {
        return stage;
    }

    public static Image getIcon() {
        return icon;
    }

    public static void fullScreen() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        icon = new Image(Main.class.getResource("resources/images/icons/phyton.png").toExternalForm());
        stage.getIcons().add(icon);
        //stage.getIcons().add(new Image(Main.class.getResource("/resources/images/icons/phyton.png").toExternalForm()));
        //stage.getIcons().add(new Image(("file:src/resources/images/icons/phyton.png")));
        splash();
        stage.show();
    }

    public void splash() throws IOException {
        splash = new Splash();
        splash.show();
        stage.setScene(splash.getSplashScene());
        splash.getSplashScene().getStylesheets().add(Main.class.getResource("view/stylesheets/splash.css").toExternalForm());
        splash.getProgresser().setOnFinished(this::endSplash);
    }

    public void endSplash(ActionEvent ex) {
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
            scene.getStylesheets().add(Main.class.getResource("view/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setMinHeight(((VBox) root).getMinHeight());
            stage.setMinWidth(((VBox) root).getMinWidth());
            stage.setTitle("phyton.workspace");
            fullScreen();
            ResizeHelper.addResizeListener(stage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
