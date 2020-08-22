package application.model.quantity;

public class Charge extends UnitValue implements Cloneable {
    public static final String unit = "As";

    public Charge(double value) {
        super(value, unit);
    }

    public Charge(Charge Q) {
        this(Q.getValue());
    }

    public Charge(Current I, Time t) {
        this(I.mul(t).getValue());
    }

    public Charge(Time t, Current I) {
        this(I.mul(t).getValue());
    }

    public Charge(Work W, Voltage V) {
        this(W.div(V).getValue());
    }

    public Charge(Energy E, Voltage V) {
        this(E.div(V).getValue());
    }

    public Charge clone() {
        return new Charge(this);
    }

    @Override
    public String toString() {
        return getValue() + " C";
    }
}
