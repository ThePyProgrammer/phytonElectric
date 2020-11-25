package application.model.quantity;

public class Mass extends UnitValue implements Cloneable {
    public static final String unit = "kg";

    public Mass(double value) {
        super(value, unit);
    }

    public Mass(Mass m) {
        this(m.getValue());
    }

    public Mass(Force F, Acceleration a) {
        this(F.div(a).getValue());
    }

    public Mass clone() {
        return new Mass(this);
    }

    @Override
    public String toString() {
        return getValue() + " N";
    }
}
