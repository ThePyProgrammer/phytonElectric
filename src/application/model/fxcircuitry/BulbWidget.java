package application.model.fxcircuitry;

import application.controller.MainframeController;
import application.model.circuitry.Bulb;
import application.model.circuitry.Component;
import application.model.util.Point;

public class BulbWidget extends ComponentWidget {
    private static Bulb temp;
    private final Bulb bulb;


    public BulbWidget() {
        super(genBulb());
        bulb = temp;
        // setStyle(" -fx-border-style: dotted; -fx-border-color: black; -fx-border-radius: 4px;");
    }

    private static Bulb genBulb() {
        return temp = new Bulb();
    }

    public static ComponentPane<BulbWidget> create(CircuitPane master) {
        BulbWidget root = new BulbWidget();
        ComponentPane<BulbWidget> pane = new ComponentPane<>(root);
        master.add(pane);


        ComponentCircle c1 = new ComponentCircle(new Point(pane.getLayoutX() - 10, pane.getLayoutY() + pane.getHeight() / 2), master, pane.getComponent().getLeft(), pane.getComponent().getRight(), pane),
                c2 = new ComponentCircle(new Point(pane.getLayoutX() + pane.getWidth() + 10, pane.getLayoutY() + pane.getHeight() / 2), master, pane.getComponent().getRight(), pane.getComponent().getLeft(), pane);

        // c1.centerXProperty().bind(root.layoutXProperty());

        pane.setListener(((observable, oldValue, newValue) -> {
            double w = pane.getPrefWidth(), theta = pane.getWidget().getRotate(), x = (double) newValue;
            c1.setCenterX(x + w / 2 - (w / 2 + 10) * cos(theta));
            c2.setCenterX(x + w / 2 + (w / 2 + 10) * cos(theta));

        }), ((observable, oldValue, newValue) -> {
            double h = pane.getPrefHeight(), w = pane.getPrefWidth(), theta = pane.getWidget().getRotate(), y = (double) newValue;
            c1.setCenterY(y + h / 2 - (w / 2 + 10) * sin(theta));
            c2.setCenterY(y + h / 2 + (w / 2 + 10) * sin(theta));
        }), ((observable, oldValue, newValue) -> {
            double h = pane.getPrefHeight(), w = pane.getPrefWidth(), theta = (double) newValue, x = pane.getLayoutX(), y = pane.getLayoutY();
            c1.setCenterX(x + w / 2 - (w / 2 + 10) * cos(theta));
            c2.setCenterX(x + w / 2 + (w / 2 + 10) * cos(theta));
            c1.setCenterY(y + h / 2 - (w / 2 + 10) * sin(theta));
            c2.setCenterY(y + h / 2 + (w / 2 + 10) * sin(theta));
            c1.toFront();
            c2.toFront();
        }));

        pane.setC1(c1);
        pane.setC2(c2);


        final double parentScaleX = pane.getParent().localToSceneTransformProperty().getValue().getMxx();
        final double parentScaleY = pane.getParent().localToSceneTransformProperty().getValue().getMyy();
        pane.setLayoutX(master.getPrefWidth() / 2 * parentScaleX);
        pane.setLayoutY(master.getPrefHeight() / 2 * parentScaleY);

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
        return "resources/images/widgets/bulb.png";
    }

    public Component getComponent() {
        return bulb;
    }
}
