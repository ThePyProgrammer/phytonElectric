package application.model.util.fxtools;

import application.model.fxcircuitry.ComponentPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class AngleChooser extends AnglePicker {
    private ComponentPane node;

    public AngleChooser() {
        setForegroundPaint(Color.WHITE);
        setBackgroundPaint(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(214, 214, 214)),
                new Stop(0.5, Color.rgb(206, 206, 206)),
                new Stop(1.0, Color.rgb(195, 195, 195)))
        );
        setIndicatorPaint(Color.rgb(97, 97, 97));
        setTextPaint(Color.rgb(26, 26, 26));
    }

    public void bind(ComponentPane node) {
        if (this.node != null) {
            this.node.getWidget().rotateProperty().unbindBidirectional(angleProperty());
            angleProperty().removeListener(this.node.getListenerRotate());
        }

        this.node = node;
        setAngle(this.node.getWidget().getRotate());
        if (this.node.getWidget().getRotate() == 0) setAngle(360);
        this.node.getWidget().rotateProperty().bindBidirectional(angleProperty());
        angleProperty().addListener(this.node.getListenerRotate());
    }
}
