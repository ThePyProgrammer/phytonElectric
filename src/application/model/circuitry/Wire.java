package application.model.circuitry;

import application.model.util.Point;

public class Wire {
    private final Point p = new Point();

    /**
     * @param node Node to be connected to
     *             connects to this node by binding the point to a private point
     */
    public void connectTo(Node node) {
        p.bindBidirectional(node);
    }

    /**
     * disconnects from previously generated point
     */
    public void disconnect() {
        p.xProperty().unbind();
        p.yProperty().unbind();
    }
}
