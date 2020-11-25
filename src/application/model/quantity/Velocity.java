package application.model.quantity;

public class Velocity extends UnitValue implements Cloneable {
    public static final String unit = "m/s";

    public Velocity(double value) {
        super(value, unit);
    }

    public Velocity(Velocity v) {
        this(v.getValue());
    }

    public Velocity(Distance d, Time t) {
        this(d.div(t).getValue());
    }

    public Velocity(Acceleration a, Time t) {
        this(a.mul(t).getValue());
    }

    public Velocity(Time t, Acceleration a) {
        this(a.mul(t).getValue());
    }

    public Velocity(Power P, Force F) {
        this(P.div(F).getValue());
    }

    public Velocity clone() {
        return new Velocity(this);
    }

    @Override
    public String toString() {
        return getValue() + " m/s";
    }
}
