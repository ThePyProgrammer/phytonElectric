package application.model.circuitry;

public class ComponentNode extends Node {
    private final Component self;
    private Wire wire;
    private Node connected, pair;

    /**
     * @param self Component that this ComponentNode belongs to
     *             instantiates new Wire
     */
    public ComponentNode(Component self) {
        this.self = self;
        wire = new Wire();
    }

    /**
     * @param node connects to the node
     *             overrides abstract method connectTo(Node node) in Node
     */
    public void connectTo(Node node) {
        wire.connectTo(node);
        connected = node;
    }

    /**
     * disconnects from the connected node
     * overrides abstract method disconnect() in Node
     */
    public void disconnect() {
        connected = null;
        wire.disconnect();
    }

    // Accessors and Mutators

    public Component getSelf() {
        return self;
    }

    public Wire getWire() {
        return wire;
    }

    public void setWire(Wire wire) {
        this.wire = wire;
    }

    public Node getPair() {
        return pair;
    }

    public void setPair(ComponentNode pair) {
        this.pair = pair;
    }

    public Node getConnected() {
        return connected;
    }

    public void setConnected(ComponentNode connected) {
        this.connected = connected;
    }
}
