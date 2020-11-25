package application.model.quantity;

public class Energy extends UnitValue implements Cloneable {
    public static final String unit = "kgm^2/s^2";

    public Energy(double value) {
        super(value, unit);
    }

    public Energy(Energy E) {
        this(E.getValue());
    }

    public Energy(Power P, Time t) {
        this(P.mul(t).getValue());
    }

    public Energy(Voltage V, Charge Q) {
        this(V.mul(Q).getValue());
    }

    public Energy(Charge Q, Voltage V) {
        this(V.mul(Q).getValue());
    }

    public Energy(Force F, Distance d) {
        this(F.mul(d).getValue());
    }

    public Energy clone() {
        return new Energy(this);
    }

    @Override
    public String toString() {
        return getValue() + " J";
    }
}
