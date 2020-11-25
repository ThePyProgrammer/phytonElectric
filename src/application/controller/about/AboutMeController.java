package application.controller.about;

import application.controller.MainframeController;
import application.model.util.fxtools.TypingTransition;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutMeController implements Initializable {
    @FXML
    Label label1, label2, label3;
    @FXML
    SplitPane split;
    @FXML
    ImageView nush;
    String[] texts = {"Hi.", "My name is Prannaya Gupta,", "and I am a student in NUS High School."};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResourceBundle bundle = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
        label1.setText(bundle.getString("aboutMelabel1"));
        label2.setText(bundle.getString("aboutMelabel2"));
        label3.setText(bundle.getString("aboutMelabel3"));

        MainframeController.currentOccurrence.locale.addListener(((observable, oldValue, newValue) -> {
            ResourceBundle bund = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
            label1.setText(bund.getString("aboutMelabel1"));
            label2.setText(bund.getString("aboutMelabel2"));
            label3.setText(bund.getString("aboutMelabel3"));
        }));

        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().addAll(new TypingTransition(label1, 100), // label transitions for the code
                new TypingTransition(label2, 100),
                new TypingTransition(label3, 100), getImageViewFade(nush),
                new Transition() {
                    {
                        setCycleDuration(Duration.millis(1000));
                    }

                    @Override
                    protected void interpolate(double frac) {
                        split.setDividerPositions(0.4 * frac);
                    }
                });
        split.setDividerPositions(0.0);
        sequence.play();
    }

    public Animation getImageViewFade(ImageView img) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(2000));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(img);
        return fade;
    }
}
