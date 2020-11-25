package application.model.circuitry;

import application.model.util.Point;

public abstract class Node extends Point {
    /**
     * @param node node to be connected
     *             connects to the node
     */
    abstract public void connectTo(Node node);

    /**
     * disconnects Node
     */
    abstract public void disconnect();

    /**
     * gets current Component
     */
    abstract public Component getSelf();

    /**
     * gets pair Component
     */
    abstract public Node getPair();

    /**
     * gets connected Component
     */
    abstract public Node getConnected();
}
