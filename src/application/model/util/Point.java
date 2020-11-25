package application.model.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;

public class Point implements Cloneable, Comparable {

    public final static int MIN = -75000;
    public final static int MAX = 75000;
    private DoubleProperty x = new SimpleDoubleProperty(), y = new SimpleDoubleProperty();

    public Point() {
        this(0, 0);
    }

    public Point(double x, double y) {
        set(x, y);
    }

    public Point(DoubleProperty x, DoubleProperty y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point2D point) {
        this(point.getX(), point.getY());
    }

    private static int randomize(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public double getX() {
        return x.get();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void set(double x, double y) {
        setX(x);
        setY(y);
    }

    public void set(Point2D point) {
        setX(point.getX());
        setY(point.getY());
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Point copy() {
        return new Point(getX(), getY());
    }

    public Point clone() {
        return copy();
    }

    public void bind(Point p) {
        x.bind(p.xProperty());
        y.bind(p.yProperty());
    }

    public void bindBidirectional(Point p) {
        x.bindBidirectional(p.xProperty());
        y.bindBidirectional(p.yProperty());
    }

    public Point2D to2d() {
        return new Point2D(getX(), getY());
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Point2D) return compareTo((Point2D) o);
        else if (o instanceof Point) return compareTo((Point) o);
        else return 0;
    }

    public int compareTo(Point p) {
        if (getX() != p.getX()) return (int) (getX() - p.getX());
        else return (int) (getY() - p.getY());
    }

    public int compareTo(Point2D p) {
        if (getX() != p.getX()) return (int) (getX() - p.getX());
        else return (int) (getY() - p.getY());
    }

    public Point fromOrigin(Point p) {
        return new Point(getX() - p.getX(), getY() - p.getY());
    }

    public double getHypotenuse() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public double hypotenuseFrom(Point p) {
        return fromOrigin(p).getHypotenuse();
    }
}
