package application.model.quantity;

public class Current extends UnitValue implements Cloneable {
    public static final String unit = "A";

    public Current(double value) {
        super(value, unit);
    }

    public Current(Current I) {
        this(I.getValue());
    }

    public Current(Charge Q, Time t) {
        this(Q.div(t).getValue());
    }

    public Current(Power P, Voltage V) { this(P.div(V).getValue()); }

    public Current(Voltage V, Resistance R) { this(V.div(R).getValue()); }

    public Current(Power P, Resistance R) { this(P.div(R).pow(0.5).getValue()); }

    public Current clone() {
        return new Current(this);
    }

    @Override
    public String toString() {
        return getValue()+" A";
    }
}
