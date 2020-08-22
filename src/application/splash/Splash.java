package application.splash;

import javafx.animation.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.*;

public class Splash {
    static Scene splash;
    static Rectangle rect = new Rectangle();
    private Pane pane, splashLayout;
    private ProgressBar loadProgress;
    private Label progressText, label;
    ImageView iv, iv2, iv3;
    final String IMAGE_URL2 = "src/resources/images/lightning.gif", IMAGE_URL = "src/resources/images/lightning2.gif",
            IMAGE_URL3 = "src/resources/images/lightning4.gif";
    final private SequentialTransition seqT = new SequentialTransition(), progress = new SequentialTransition(),
            fillerT = new SequentialTransition();
    int scale = 30, dur = 800;
    Thread proganim, shapeanim, filleranim;

    private ObservableList<String> texts = FXCollections.observableArrayList("Implementing MVC Framework...",
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

    public Splash() {
        splash = new Scene((pane = new Pane()));
        pane.setStyle("-fx-background-color:black");
    }

    public void init() throws FileNotFoundException {
        rect = new Rectangle(100-2*scale, 20, scale, scale);
        rect.setFill(Color.BLACK);

        label = new Label("phyton");
        label.setFont(new Font("Verdana", 40));
        label.setStyle("-fx-text-fill:white");
        label.setLayoutX(140);
        label.setLayoutY(70);

        iv = new ImageView(new Image(new FileInputStream(IMAGE_URL2)));
        iv.setFitWidth(400);
        iv.setFitHeight(300);
        iv.setX(300);
        iv.setY(0);

        iv2 = new ImageView(new Image(new FileInputStream(IMAGE_URL)));
        iv2.setFitWidth(500);
        iv2.setFitHeight(240);
        iv2.setX(100);
        iv2.setY(240);

        iv3 = new ImageView(new Image(new FileInputStream(IMAGE_URL3)));
        iv3.setFitWidth(400);
        iv3.setFitHeight(240);
        iv3.setX(0);
        iv3.setY(240);
        iv3.setVisible(false);

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

        FillTransition ft = new FillTransition(Duration.millis(2000), rect, Color.BLACK, Color.AQUAMARINE);
        //Thread filler = new Thread(() -> ft.play());
        //filler.start();
        //while(filler.isAlive()) {}
        (filleranim = new Thread(filler)).start();
        (shapeanim = new Thread(shapeMover)).start();
        (proganim = new Thread(progresser)).start();

    }

    private Runnable filler = () -> {
        fillerT.getChildren().addAll(
                new FillTransition(Duration.millis(2000), rect, Color.BLACK, Color.CORNFLOWERBLUE),
                new FillTransition(Duration.millis(2000), rect, Color.CORNFLOWERBLUE, Color.DEEPSKYBLUE),
                new FillTransition(Duration.millis(2000), rect, Color.DEEPSKYBLUE, Color.MIDNIGHTBLUE),
                new FillTransition(Duration.millis(2000), rect, Color.MIDNIGHTBLUE, Color.BLACK)
        );
        fillerT.play();
    };

    private Runnable progresser = () -> {
        progress.getChildren().add(new Transition() {
            { setCycleDuration(Duration.millis(8000)); }
            @Override
            protected void interpolate(double frac) {
                loadProgress.setProgress(frac);
                progressText.setText(texts.get((int)(frac*(texts.size()-1))));
                if((int)(frac*4) == 1 && !iv3.isVisible()) {
                    iv3.setVisible(true);
                    iv3.opacityProperty().set(0);
                    Timeline timeline = new Timeline();
                    KeyFrame key = new KeyFrame(Duration.millis(1000),
                            new KeyValue(iv3.opacityProperty(), 1));
                    timeline.getKeyFrames().add(key);
                    Thread t = new Thread(() -> timeline.play());
                    t.start();
                    // iv3.setVisible(true);
                }
            }
        });
        progress.play();
    };

    private Runnable shapeMover = () -> {
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
    };

    public Scene getSplashScene() { return splash; }

    public SequentialTransition getSequentialTransition() { return seqT; }

    public SequentialTransition getProgresser() { return progress; }

    public Thread getProganim() {
        return proganim;
    }

    public Thread getShapeanim() {
        return shapeanim;
    }

    public void stopAllThreads() {
        proganim.interrupt();
        shapeanim.interrupt();
        filleranim.interrupt();
    }
}
