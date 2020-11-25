package application.model.fxcircuitry;

import application.controller.MainframeController;
import application.model.circuitry.ParallelNode;
import application.model.util.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Collection;
import java.util.LinkedHashMap;

public class ParallelCircle extends NodeCircle {
    ObservableMap<Wire, NodeCircle> connectedTo;
    ParallelNode node;
    ComponentPane master;
    Pane pane;
    Wire focused = null;

    public ParallelCircle(Point p, Pane pane, ComponentPane master) {
        this(p, pane, new ParallelNode(), master);
    }

    public ParallelCircle(Point p, Pane pane, ParallelNode node, ComponentPane master) {
        super(p, "fill");

        p.xProperty().bindBidirectional(centerXProperty());
        p.yProperty().bindBidirectional(centerYProperty());

        this.node = node;
        this.master = master;
        this.pane = pane;

        setNode(node);
        setSuperPane(master);
        node.setPane(master);

        pane.getChildren().add(this);

        connectedTo = FXCollections.observableMap(new LinkedHashMap<>());

        setOnDragDetected(event -> {
            newWire();
            Point2D point = pane.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
            focused.setEnd(point.getX(), point.getY());
            startFullDrag();
            event.consume();

        });


        setOnMouseReleased(event -> {
            if (focused != null) {
                if (focused.getTarget() == null) disconnect();
                else connectTo(focused.getTarget());
            }
            CircuitPane.beingDragged = null;
            toFront();
            event.consume();
        });

        setOnMouseDragged(event -> {
            if (CircuitPane.beingDragged != null) CircuitPane.beingDragged.setEnd(event.getX(), event.getY());
            toFront();
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

    @Override
    public void newWire() {
        Wire wire = new Wire(this);
        wire.bindStart(p);
        pane.getChildren().add(wire);
        CircuitPane.beingDragged = wire;
        connectedTo.put(wire, null);
        focused = wire;
        MainframeController.currentOccurrence.getTabController().setMainNode(master);
    }

    public void connectTo(ParallelCircle other) { // overloading
        focused.bindEnd(other.p);
        other.connectedTo.put(focused, this);
        connectedTo.put(focused, other);
        node.connectTo(other.node);
        other.node.connectTo(node);
        other.master.process();
        master.process();
    }

    public void connectTo(ComponentCircle other) { // overloading
        focused.bindEnd(other.p);
        other.setConnectedTo(focused);
        connectedTo.put(focused, other);
        node.connectTo(other.node);
        other.node.connectTo(node);
        other.master.process();
        master.process();
    }

    @Override
    public void connectTo(NodeCircle other) {
        if (connectedTo.containsValue(other)) return;
        if (other instanceof ParallelCircle) connectTo((ParallelCircle) other);
        else if (other instanceof ComponentCircle) connectTo((ComponentCircle) other);
    }

    @Override
    public NodeCircle bestC(ComponentPane pane) {
        if (pane.getC1().isAvailable() && pane.getC2().isAvailable()) {
            NodeCircle c1 = pane.getC1(), c2 = pane.getC2();
            if (c1.p.hypotenuseFrom(p) > c2.p.hypotenuseFrom(p)) return c1;
            else return c2;
        } else return pane.getAvailableC();
    }

    public void disconnect() {
        if (focused != null) {
            pane.getChildren().remove(focused);
            if (node.getConnected() != null) {
                node.getConnected().disconnect();
                node.disconnect();
                MainframeController.currentOccurrence.getTabController().setMainNode(master);
            }
            connectedTo.remove(focused);
            focused = null;
            for (Wire wire : connectedTo.keySet()) focused = wire;

        }
        master.process();
    }

    public void disconnectCompletely() {
        while (focused != null) {
            disconnect();
        }
    }

    public boolean isAvailable() {
        return true;
    }

    @Override
    public ParallelNode getNode() {
        return node;
    }

    public Collection<NodeCircle> connectedNodes() {
        return connectedTo.values();
    }
}
