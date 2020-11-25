package application.model.fxcircuitry;

import application.model.util.Point;
import javafx.scene.shape.Line;

public class Wire extends Line {
    NodeCircle source, target = null;

    public Wire(NodeCircle node) {
        source = node;
        setMouseTransparent(true);
        setStrokeWidth(getStrokeWidth() + 3);
    }

    public NodeCircle getTarget() {
        return target;
    }

    public void setTarget(NodeCircle target) {
        this.target = target;
    }

    public void bindStart(Point p) {
        startXProperty().bindBidirectional(p.xProperty());
        startYProperty().bindBidirectional(p.yProperty());
    }

    public void setEnd(double x, double y) {
        setEndX(x);
        setEndY(y);
    }

    public void bindEnd(Point p) {
        endXProperty().bindBidirectional(p.xProperty());
        endYProperty().bindBidirectional(p.yProperty());
    }
}
