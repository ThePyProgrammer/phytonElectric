package application.model.circuitry;

import application.model.fxcircuitry.ComponentPane;
import application.model.quantity.Current;
import application.model.quantity.Resistance;

import java.util.LinkedHashMap;
import java.util.Set;

public class ParallelNode extends Node {
    Component self;
    LinkedHashMap<Node, Wire> connectedTo;
    ComponentPane pane;

    public ParallelNode() {
        self = new Component(new Current(0), new Resistance(0));
        connectedTo = new LinkedHashMap<>();
    }

    public void connectTo(Node node) {
        Wire wire = new Wire();
        wire.connectTo(node);
        connectedTo.put(node, wire);
    }

    public void disconnect(Node node) {
        if (connectedTo.containsKey(node)) {
            Wire wire = connectedTo.get(node);
            wire.disconnect();
        }
    }

    public void disconnect() {
        Node lastNode = null;
        for (Node node : connectedTo.keySet()) {
            lastNode = node;
        }
        if (lastNode != null) disconnect(lastNode);
    }

    public Component getSelf() {
        return self;
    }

    @Override
    public Node getPair() {
        return this;
    }

    @Override
    public Node getConnected() {
        return null;
    }

    public Set<Node> connectedNodes() {
        return connectedTo.keySet();
    }

    public Wire getWire(Node node) {
        if (connectedTo.containsKey(node)) return connectedTo.get(node);
        return null;
    }

    public ComponentPane getPane() {
        return pane;
    }

    public void setPane(ComponentPane pane) {
        this.pane = pane;
    }
}
