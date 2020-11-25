package application.model.fxcircuitry;

import application.model.circuitry.Node;
import application.model.util.Point;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class NodeCircle extends Circle {
    public ObjectProperty<Wire> connectedTo = new SimpleObjectProperty<>();
    protected Node node, pair;
    Point p;
    ComponentPane superPane;

    public NodeCircle(Point p, String type, Node node, Node pair) {
        super(p.getX(), p.getY(), 10, Color.TRANSPARENT);
        this.centerXProperty().bindBidirectional(p.xProperty());
        this.centerYProperty().bindBidirectional(p.yProperty());
        this.p = p;
        if (type.equals("stroke")) {
            setStroke(Color.BLACK);
        } else setFill(Color.BLACK);

        this.node = node;
        this.pair = pair;
    }

    public NodeCircle(Point p, String type) {
        super(p.getX(), p.getY(), 10, Color.TRANSPARENT);
        this.centerXProperty().bindBidirectional(p.xProperty());
        this.centerYProperty().bindBidirectional(p.yProperty());
        this.p = p;
        if (type.equals("stroke")) {
            setStroke(Color.BLACK);
        } else setFill(Color.BLACK);
    }

    public boolean isAvailable() {
        return connectedTo.get() == null;
    }


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Wire getConnectedTo() {
        return connectedTo.get();
    }

    public void setConnectedTo(Wire connectedTo) {
        this.connectedTo.set(connectedTo);
    }

    public ObjectProperty<Wire> connectedToProperty() {
        return connectedTo;
    }

    public abstract void newWire();

    public abstract void connectTo(NodeCircle prev);

    public abstract NodeCircle bestC(ComponentPane pane);

    public ComponentPane getSuperPane() {
        return superPane;
    }

    public void setSuperPane(ComponentPane superPane) {
        this.superPane = superPane;
    }
}
