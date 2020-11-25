package application.model.fxcircuitry;

import application.controller.MainframeController;
import application.model.circuitry.ComponentNode;
import application.model.util.Point;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class ComponentCircle extends NodeCircle {
    final Point point = new Point();
    ObjectProperty<Wire> connectedTo = new SimpleObjectProperty<>();
    BooleanProperty isStart = new SimpleBooleanProperty(false);
    ComponentNode node, pair;
    ComponentPane master;
    Pane pane;

    public ComponentCircle(Point p, Pane pane, ComponentNode node, ComponentNode pair, ComponentPane master) {
        super(p, "fill", node, pair);

        this.node = node;
        this.pair = pair;
        this.master = master;
        this.pane = pane;
        pane.getChildren().add(this);
        super.connectedTo.bindBidirectional(this.connectedTo);

        setSuperPane(master);

        setOnMouseClicked(event -> {
            disconnect();
            event.consume();
        });

        setOnMousePressed(event -> {
            disconnect();
            event.consume();
        });

        setOnDragDetected(event -> {
            newWire();
            point.set(pane.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY())));
            connectedTo.get().setEnd(point.getX(), point.getY());
            startFullDrag();
            event.consume();

        });


        setOnMouseReleased(event -> {
            if (connectedTo.get() != null) {
                if (connectedTo.get().getTarget() == null) disconnect();
                else connectTo(connectedTo.get().getTarget());
            }
            CircuitPane.beingDragged = null;
            toFront();
            MainframeController.currentOccurrence.getTabController().setMainNode(master);
            event.consume();
        });

        setOnMouseDragged(event -> {
            if (CircuitPane.beingDragged != null) CircuitPane.beingDragged.setEnd(event.getX(), event.getY());
            toFront();
            MainframeController.currentOccurrence.getTabController().setMainNode(master);
            event.consume();
        });

        setOnMouseDragEntered(event -> {
            if (CircuitPane.beingDragged != null && CircuitPane.beingDragged.source.pair != this.node)
                CircuitPane.beingDragged.setTarget(this);
            toFront();
            event.consume();
        });
        setOnMouseDragExited(event -> {
            if (CircuitPane.beingDragged != null && CircuitPane.beingDragged.source.pair != this.node)
                CircuitPane.beingDragged.setTarget(null);
            toFront();
            event.consume();
        });
    }

    public ComponentCircle() {
        super(new Point(), "stroke", null, null);
    }

    public void newWire() {
        disconnect();
        connectedTo.set(new Wire(this));
        connectedTo.get().bindStart(p);
        isStart.set(true);
        pane.getChildren().add(connectedTo.get());
        CircuitPane.beingDragged = connectedTo.get();
        MainframeController.currentOccurrence.getTabController().setMainNode(master);

    }

    public void connectTo(ComponentCircle other) {
        connectedTo.get().bindEnd(other.p);
        pane.getChildren().remove(other.connectedTo.get());
        if (other.node.getConnected() != null) other.node.getConnected().disconnect();
        other.node.disconnect();
        other.connectedTo.set(connectedTo.get());
        other.isStart.set(false);
        node.connectTo(other.node);
        other.node.connectTo(node);
        other.master.process();
        master.process();
    }


    public void connectTo(ParallelCircle other) {
        connectedTo.get().bindEnd(other.p);
        other.connectedTo.put(connectedTo.get(), this);
        node.connectTo(other.node);
        other.node.connectTo(node);
        other.master.process();
        master.process();
    }

    public void connectTo(NodeCircle other) {
        if (other instanceof ParallelCircle) {
            connectTo((ParallelCircle) other);
        } else if (other instanceof ComponentCircle) {
            connectTo((ComponentCircle) other);
        }
    }

    public void disconnect() {
        if (connectedTo.get() != null) {
            pane.getChildren().remove(connectedTo.get());
            if (node.getConnected() != null) {
                node.getConnected().disconnect();
                node.disconnect();
                MainframeController.currentOccurrence.getTabController().setMainNode(master);
            }
        }
        master.process();
    }

    public boolean isAvailable() {
        return connectedTo.get() == null;
    }

    public NodeCircle bestC(ComponentPane pane) {
        if (pane.getC1().isAvailable() && pane.getC2().isAvailable()) {
            NodeCircle c1 = pane.getC1(), c2 = pane.getC2();
            if (c1.p.hypotenuseFrom(p) > c2.p.hypotenuseFrom(p)) return c1;
            else return c2;
        } else return pane.getAvailableC();
    }

    public Wire getConnectedTo() {
        return connectedTo.get();
    }

    public ObjectProperty<Wire> connectedToProperty() {
        return connectedTo;
    }

    public boolean isIsStart() {
        return isStart.get();
    }

    public BooleanProperty isStartProperty() {
        return isStart;
    }

    public Point getPoint() {
        return point;
    }

    public Point getP() {
        return p;
    }

    public ComponentNode getNode() {
        return node;
    }

    public ComponentNode getPair() {
        return pair;
    }
}
