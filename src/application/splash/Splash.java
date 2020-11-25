package application.splash;

import application.Main;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class Splash {
    static Scene splash;
    static Rectangle rect = new Rectangle();
    private static ObservableList<String> texts = FXCollections.observableArrayList("Implementing MVC Framework...",
            "Creating 100+ Files...", "Reading Java API...", "Importing *...", "Generating classes...",
            "Generating interfaces...", "Generating enumerations...", "Extending classes...",
            "Implementing interfaces...", "Setting preferences...", "Initializing Current Flow...",
            "Introducing models to environment...", "Creating power sources...", "Creating components...",
            "Creating practical components...", "Catching all Pokemon...", "Parsing i18N.java...",
            "Translating into multiple languages...", "Seeking damages...", "Formatting Menubar...",
            "Creating JavaFX Controls...", "Initializing Drag and Drop...", "Making phyton.canvas...",
            "Deriving images...", "Finding logo...", "Generating logo...", "Loading FXML Files...",
            "Generating mainframe.fxml...", "Searching for JavaFX Widgets...", "Generating GUI...",
            "Generating About The Programmer page...", "Generating About phyton page...",
            "Generating autocomplete combobox...", "Generating sidebar...", "Styling the window...",
            "Initializing MainframeController...", "Reading phyton source...", "Calculating resistance...",
            "Restarting phyton...", "Parsing .exml files...", "Generating electrons...", "Annihilating fake electrons...",
            "Searching games...", "Failing in generating games...", "Finding other possible components...",
            "Threading everything together...", "Forming simulator...", "Reading everything...", "Ignoring magnetism...",
            "Excusing Quantum Physics for this session...", "Trying to understand PO...", "Failing to understand PO...",
            "Ignoring PO...", "Preparing Environment...", "Finally loading phyton.workspace...", "Completed.");
    final String IMAGE_URL = Main.class.getResource("resources/images/splash/lightning.gif").toExternalForm();
    final private SequentialTransition seqT = new SequentialTransition(), progress = new SequentialTransition(),
            fillerT = new SequentialTransition();
    ImageView iv;
    int scale = 30, dur = 800;
    private Pane pane, splashLayout;
    private ProgressBar loadProgress;
    private Label progressText, label;

    public Splash() {
        splash = new Scene((pane = new Pane()));
        pane.setStyle("-fx-background-color:black");
    }

    public void init() throws FileNotFoundException {
        rect = new Rectangle(100 - 2 * scale, 20, scale, scale);
        rect.setFill(Color.BLACK);

        label = new Label("phyton");
        label.setFont(new Font("Verdana", 40));
        label.setStyle("-fx-text-fill:white");
        label.setLayoutX(140);
        label.setLayoutY(70);

        iv = new ImageView(new Image(IMAGE_URL));
        iv.setFitWidth(400);
        iv.setFitHeight(300);
        iv.setX(300);
        iv.setY(0);

        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(800);
        progressText = new Label("Generating phyton.workspace...");
        progressText.setFont(new Font("System", 13));
        //progressText.setStyle("-fx-text-fill:white");
        splashLayout = new VBox(loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setEffect(new DropShadow());
        splashLayout.setStyle("-fx-border-radius: 5");
        splashLayout.setLayoutX(0);
        splashLayout.setLayoutY(240);
        splashLayout.setOpaqueInsets(new Insets(10));
        pane.getChildren().addAll(rect, label, iv, splashLayout);
    }

    public void show() throws FileNotFoundException {
        init();

        fillerT.getChildren().addAll(
                new FillTransition(Duration.millis(2000), rect, Color.BLACK, Color.CORNFLOWERBLUE),
                new FillTransition(Duration.millis(2000), rect, Color.CORNFLOWERBLUE, Color.DEEPSKYBLUE),
                new FillTransition(Duration.millis(2000), rect, Color.DEEPSKYBLUE, Color.MIDNIGHTBLUE),
                new FillTransition(Duration.millis(2000), rect, Color.MIDNIGHTBLUE, Color.BLACK)
        );
        fillerT.play();

        progress.getChildren().add(new Transition() {
            {
                setCycleDuration(Duration.millis(8000));
            }

            @Override
            protected void interpolate(double frac) {
                loadProgress.setProgress(frac);
                progressText.setText(texts.get((int) (frac * (texts.size() - 1))));
            }
        });
        progress.play();

        int[] rotins = {scale, 2 * scale, 3 * scale, 4 * scale, 5 * scale, -6 * scale, -5 * scale, -4 * scale, -3 * scale, -2 * scale};
        int x, y;
        for (int i : rotins) {
            RotateTransition rt = new RotateTransition(Duration.millis(dur), rect);
            rt.setByAngle(i / Math.abs(i) * 90);
            rt.setCycleCount(1);
            TranslateTransition pt = new TranslateTransition(Duration.millis(dur), rect);
            x = (int) (rect.getX() + Math.abs(i));
            y = (int) (rect.getX() + Math.abs(i) + (Math.abs(i) / i) * scale);
            pt.setFromX(x);
            pt.setToX(y);
            ParallelTransition pat = new ParallelTransition();
            pat.getChildren().addAll(pt, rt);
            pat.setCycleCount(1);
            seqT.getChildren().add(pat);
        }
        seqT.setNode(rect);
        seqT.play();
    }

    public Scene getSplashScene() {
        return splash;
    }

    public SequentialTransition getProgresser() {
        return progress;
    }
}
