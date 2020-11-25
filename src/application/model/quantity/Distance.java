package application.model.quantity;

public class Distance extends UnitValue implements Cloneable {
    public static final String unit = "m";

    public Distance(double value) {
        super(value, unit);
    }

    public Distance(Distance d) {
        this(d.getValue());
    }

    public Distance(Velocity v, Time t) {
        this(v.mul(t).getValue());
    }

    public Distance(Time t, Velocity v) {
        this(v.mul(t).getValue());
    }

    public Distance clone() {
        return new Distance(this);
    }

    @Override
    public String toString() {
        return getValue() + " m";
    }
}
