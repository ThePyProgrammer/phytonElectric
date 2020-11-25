package application.model.util.fxtools;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TypingTransition extends Transition {
    String text;
    Label label;
    double speed;

    public TypingTransition(Label label, String text, double speed) {
        this.label = label;
        this.text = text;
        this.speed = speed;
        label.setText("");
        setCycleDuration(Duration.millis(50 * text.length()));
        setInterpolator(Interpolator.LINEAR);
    }

    public TypingTransition(Label label, String text) {
        this(label, text, 50);
    }

    public TypingTransition(Label label, double speed) {
        this(label, label.getText(), speed);
    }

    public TypingTransition(Label label) {
        this(label, 50);
    }

    @Override
    protected void interpolate(double frac) {
        final int length = text.length();
        final int n = Math.round(length * (float) frac);
        label.setText(text.substring(0, n));
    }
}
