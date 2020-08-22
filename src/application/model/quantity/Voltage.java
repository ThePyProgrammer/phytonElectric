package application.model.quantity;

public class Voltage extends UnitValue implements Cloneable {
    public static final String unit = "kgm^2/As^3";

    public Voltage(double value) {
        super(value, unit);
    }

    public Voltage(Voltage V) {
        this(V.getValue());
    }

    public Voltage(Work W, Charge Q) {
        this(W.div(Q).getValue());
    }

    public Voltage(Energy E, Charge Q) { this(E.div(Q).getValue()); }

    public Voltage(Power P, Current I) { this(P.div(I).getValue()); }

    public Voltage(Current I, Resistance R) { this(I.mul(R).getValue()); }

    public Voltage(Resistance R, Current I) { this(I.mul(R).getValue()); }

    public Voltage(Resistance R, Power P) { this(R.mul(P).pow(0.5).getValue()); }

    public Voltage(Power P, Resistance R) { this(R.mul(P).pow(0.5).getValue()); }

    public Voltage clone() {
        return new Voltage(this);
    }

    @Override
    public String toString() {
        return getValue() + " V";
    }
}
