package application.controller.about;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.*;

public class AboutMeController implements Initializable {
    @FXML Label label1, label2, label3, label4, label5, label6;
    @FXML SplitPane split;
    String[] texts = {"Hi.", "My name is Prannaya Gupta,", "and I am a student in NUS High School.", "I enjoy coding.", "I hope you truly do enjoy it...", "...as much as I do."};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().addAll(getLabelAnimation(label1, 1), getLabelAnimation(label2, 2),
                getLabelAnimation(label3, 3), getLabelAnimation(label4, 4), getLabelAnimation(label5, 5),
                getLabelAnimation(label6, 6),
                new Transition() {
                    { setCycleDuration(Duration.millis(1000)); }
                    @Override protected void interpolate(double frac) { split.setDividerPositions(0.4*frac); }
                });
        split.setDividerPositions(0.0);
        new Thread(() -> sequence.play()).start();
    }

    public Animation getLabelAnimation(Label label, int ind) {
        label.setText("");
        final String str = texts[ind-1];

        final Animation animation = new Transition() {
            { setCycleDuration(Duration.millis(100*str.length())); }

            protected void interpolate(double frac) {
                final int length = str.length();
                final int n = Math.round(length * (float) frac);
                label.setText(str.substring(0, n));
            }
        };
        return animation;
    }
}
