package application.model.fxcircuitry;

import application.controller.MainframeController;
import application.model.circuitry.Component;
import application.model.circuitry.Resistor;
import application.model.quantity.Resistance;
import application.model.util.Point;

public class ResistorWidget extends ComponentWidget {
    private static final Resistance R = new Resistance(1);
    private static Resistor temp;
    private final Resistor resistor;


    public ResistorWidget() {
        super(generateResistor());
        resistor = temp;
    }

    public static Resistor generateResistor() {
        temp = new Resistor(R);
        return temp;
    }

    public static ComponentPane<ResistorWidget> create(CircuitPane master) {
        ResistorWidget root = new ResistorWidget();
        ComponentPane<ResistorWidget> pane = new ComponentPane<>(root);
        master.add(pane);
        ComponentCircle c1 = new ComponentCircle(new Point(pane.getLayoutX() - 10, pane.getLayoutY() + pane.getHeight() / 2), master, pane.getComponent().getLeft(), pane.getComponent().getRight(), pane),
                c2 = new ComponentCircle(new Point(pane.getLayoutX() + pane.getWidth() + 10, pane.getLayoutY() + pane.getHeight() / 2), master, pane.getComponent().getRight(), pane.getComponent().getLeft(), pane);

        pane.setListener(((observable, oldValue, newValue) -> {
            double w = pane.getPrefWidth(), wo = pane.getWidth(), theta = pane.getWidget().getRotate(), x = (double) newValue;
            c1.setCenterX(x + w / 2 - (w / 2 + 10) * cos(theta));
            c2.setCenterX(x + w / 2 + (w / 2 + 10) * cos(theta));

        }), ((observable, oldValue, newValue) -> {
            double h = pane.getPrefHeight(), w = pane.getPrefWidth(), theta = pane.getWidget().getRotate(), y = (double) newValue;
            c1.setCenterY(y + h / 2 - (w / 2 + 10) * sin(theta));
            c2.setCenterY(y + h / 2 + (w / 2 + 10) * sin(theta));
        }), ((observable, oldValue, newValue) -> {
            double h = pane.getPrefHeight(), w = pane.getPrefWidth(), wo = pane.getWidth(), theta = (double) newValue, x = pane.getLayoutX(), y = pane.getLayoutY();
            c1.setCenterX(x + w / 2 - (w / 2 + 10) * cos(theta));
            c2.setCenterX(x + w / 2 + (w / 2 + 10) * cos(theta));
            c1.setCenterY(y + h / 2 - (w / 2 + 10) * sin(theta));
            c2.setCenterY(y + h / 2 + (w / 2 + 10) * sin(theta));
            c1.toFront();
            c2.toFront();
        }));

        pane.setC1(c1);
        pane.setC2(c2);

        pane.setPrefWidth(pane.getBoundsInLocal().getWidth());
        pane.setPrefHeight(pane.getBoundsInLocal().getHeight());

        MainframeController.currentOccurrence.getTabController().setMainNode(pane);

        return pane;
    }

    private static double sin(double degrees) {
        return Math.sin(Math.toRadians(degrees));
    }

    private static double cos(double degrees) {
        return Math.cos(Math.toRadians(degrees));
    }

    String IMAGE_PATH() {
        return "resources/images/widgets/resistor.png";
    }

    public Component getComponent() {
        return resistor;
    }
}
