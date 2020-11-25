package application.model.fxcircuitry;

import application.controller.MainframeController;
import application.model.circuitry.Component;
import application.model.quantity.Current;
import application.model.quantity.Resistance;
import application.model.util.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ParallelNodeWidget extends ComponentWidget {

    public ParallelNodeWidget() {
        super(new Component(new Current(0), new Resistance(0)));
    }

    public static ComponentPane create(CircuitPane master) {
        ParallelNodeWidget root = new ParallelNodeWidget();
        ComponentPane pane = new ComponentPane(root);

        Circle circle = new Circle(25, 25, 25, Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(4);
        pane.getChildren().add(circle);
        pane.setPrefHeight(50);
        pane.setPrefWidth(50);
        master.add(pane);
        ParallelCircle c = new ParallelCircle(
                new Point(pane.getLayoutX() + pane.getWidth() / 2, pane.getLayoutY() + pane.getHeight() / 2),
                master, pane
        );

        pane.setListener(((observable, oldValue, newValue) -> {
            double w = pane.getPrefWidth(), x = (double) newValue;
            c.setCenterX(x + w / 2);

        }), ((observable, oldValue, newValue) -> {
            double h = pane.getPrefHeight(), y = (double) newValue;
            c.setCenterY(y + h / 2);
        }), ((observable, oldValue, newValue) -> {
        }));

        pane.setC1(c);
        pane.setC2(c);

        pane.setPrefWidth(pane.getBoundsInLocal().getWidth());
        pane.setPrefHeight(pane.getBoundsInLocal().getHeight());

        MainframeController.currentOccurrence.getTabController().setMainNode(pane);

        return pane;
    }

    @Override
    String IMAGE_PATH() {
        return "";
    }
}
