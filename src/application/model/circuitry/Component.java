package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;
import application.model.util.Point;
import application.model.util.Rotation;
import javafx.beans.property.DoubleProperty;

public class Component extends CircuitObject {
    Point p = new Point();
    Rotation r = new Rotation();
    Series s;

    ComponentNode left = new ComponentNode(this),
            right = new ComponentNode(this);

    {
        left.setPair(right);
        right.setPair(left);
    }

    public Component(Current I, Voltage V) {
        super(I, V);
    }

    public Component(Current I, Resistance R) {
        super(I, R);
    }

    public Component(Current I, Power P) {
        super(I, P);
    }

    public Component(Voltage V, Current I) {
        this(I, V);
    }

    public Component(Voltage V, Resistance R) {
        super(V, R);
    }

    public Component(Voltage V, Power P) {
        super(V, P);
    }

    public Component(Resistance R, Current I) {
        this(I, R);
    }

    public Component(Resistance R, Voltage V) {
        this(V, R);
    }

    public Component(Resistance R, Power P) {
        super(R, P);
    }

    public Component(Power P, Current I) {
        this(I, P);
    }

    public Component(Power P, Voltage V) {
        this(V, P);
    }

    public Component(Power P, Resistance R) {
        this(R, P);
    }


    // Point, Rotation accessors, mutators

    public double getX() {
        return p.getX();
    }

    public void setX(double x) {
        p.setX(x);
    }

    public DoubleProperty xProperty() {
        return p.xProperty();
    }

    public double getY() {
        return p.getY();
    }

    public void setY(double y) {
        p.setY(y);
    }

    public DoubleProperty yProperty() {
        return p.yProperty();
    }

    public Point getPoint() {
        return p;
    }

    public Rotation getRotation() {
        return r;
    }

    public double getAngle() {
        return r.getRotate();
    }

    // Series Methods

    public Series getSeries() {
        return s;
    }

    public void setSeries(Series s) {
        this.s = s;
    }

    public String nodeToPrev() {
        if (s != null) {
            int index = (s.getArray().indexOf(this) - 1);
            if (!getSeries().isClosed() && index == -1) return "";
            if (index == -1) index += s.getArray().size();
            Component prev = s.getArray().get(index);
            if (left.getConnected() == null) return "";
            if (prev.equals(left.getConnected().getSelf())) return "c1";
            else return "c2";
        } else return "";
    }

    public ComponentNode getLeft() {
        return left;
    }

    public ComponentNode getRight() {
        return right;
    }
}
