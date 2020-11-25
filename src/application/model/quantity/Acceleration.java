package application.model.quantity;

public class Acceleration extends UnitValue implements Cloneable {
    public static final String unit = "m/s^2";

    public Acceleration(double value) {
        super(value, unit);
    }

    public Acceleration(Acceleration a) {
        this(a.getValue());
    }

    public Acceleration(Velocity v, Time t) {
        this(v.div(t).getValue());
    }

    public Acceleration(Force F, Mass m) {
        this(F.div(m).getValue());
    }

    public Acceleration clone() {
        return new Acceleration(this);
    }

    @Override
    public String toString() {
        return getValue() + " m/s^2";
    }
}
