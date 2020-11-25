package application.controller.about;

// javafx imports

import application.Main;
import application.controller.MainframeController;
import application.model.util.fxtools.TypingTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    Image image;
    @FXML
    Label label1, label2, label3, label4;
    @FXML
    SplitPane split;
    @FXML
    ImageView iv;
    @FXML
    Hyperlink aboutMe;
    String[] texts = {"I made phyton for one reason. To help.",
            "I realise that many people don't find physics easy, as per se. However, that doesn't mean I can't help you with that.",
            "With phyton, I try to integrate simulations that can help anyone understand electrical circuits, based entirely on the circuit diagrams that most physicsts are so familiar with.",
            "This app was made during Covid-19, and strives to allow hands-on learning of electricity without having to go to the lab."};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResourceBundle bundle = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
        label1.setText(bundle.getString("aboutlabel1"));
        label2.setText(bundle.getString("aboutlabel2"));
        label3.setText(bundle.getString("aboutlabel3"));
        label4.setText(bundle.getString("aboutlabel4"));
        aboutMe.setText(bundle.getString("aboutMe"));

        MainframeController.currentOccurrence.locale.addListener(((observable, oldValue, newValue) -> {
            ResourceBundle bund = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
            label1.setText(bund.getString("aboutlabel1"));
            label2.setText(bund.getString("aboutlabel2"));
            label3.setText(bund.getString("aboutlabel3"));
            label4.setText(bund.getString("aboutlabel4"));
            aboutMe.setText(bund.getString("aboutMe"));
        }));

        RotateTransition anim = new RotateTransition(Duration.millis(3000), iv);
        anim.setCycleCount(7);
        anim.setByAngle(360);
        anim.setInterpolator(Interpolator.LINEAR);
        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().addAll(
                new TypingTransition(label1, 1000), // label transitions for the code
                new TypingTransition(label2, 1000),
                new TypingTransition(label3, 1000),
                new TypingTransition(label4, 1000));
        sequence.setOnFinished(event -> {
            anim.stop();
            iv.setRotate(0);
        });
        anim.play();
        sequence.play();
    }


    public void generateAboutMe(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/about/aboutMe.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            ResourceBundle bundle = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
            stage.setTitle(bundle.getString("aboutMe"));
            stage.getIcons().add(Main.getIcon());
            stage.show();
        } catch (IOException e) {
        }
    }
}
