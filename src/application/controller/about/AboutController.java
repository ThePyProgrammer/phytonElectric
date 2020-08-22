package application.controller.about;

import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.event.*;

import java.net.URL;
import java.util.*;

public class AboutController implements Initializable {
    @FXML Label label1, label2, label3, label4;
    @FXML SplitPane split;
    String[] texts = {"Phyton was made for one reason. To help.",
            "Physics is difficult. Calculation is even harder. But that doesn't mean we can't help.",
            "With phyton, I try to integrate simulations that can help.",
            "This app was made during Covid-19, and strives to allow hands-on learning of electricity without having to go to the lab."};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().addAll(getLabelAnimation(label1, 1), getLabelAnimation(label2, 2),
                getLabelAnimation(label3, 3), getLabelAnimation(label4, 4));

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

    public void generateAboutMe(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/about/aboutMe.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("About the Programmer");
            stage.getIcons().add(new Image(("file:src/resources/images/icons/phyton.png")));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
