package application.model.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Rotation implements Cloneable {
    private DoubleProperty rotate = new SimpleDoubleProperty();

    public Rotation() {
        this(0);
    }

    public Rotation(double angle) {
        set(angle);
    }

    public Rotation copy() {
        return new Rotation(360 - get());
    }

    public Rotation clone() {
        return copy();
    }

    public double getRotate() {
        return rotate.get();
    }

    public void setRotate(double rotate) {
        this.rotate.set(360 - rotate);
    }

    public double get() {
        return getRotate();
    }

    public DoubleProperty rotateProperty() {
        return rotate;
    }

    public DoubleProperty property() {
        return rotate;
    }

    public void set(double rotate) {
        setRotate(rotate);
    }

    public void bind(Rotation rotate) {
        bind(rotate.rotate);
    }

    public void bind(DoubleProperty property) {
        this.rotate.bind(property);
    }

    public void bindBidirectional(Rotation rotate) {
        bindBidirectional(rotate.rotate);
    }

    public void bindBidirectional(DoubleProperty property) {
        this.rotate.bindBidirectional(property);
    }
}
