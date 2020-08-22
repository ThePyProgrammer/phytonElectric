package application.model.quantity;

public class Resistance extends UnitValue implements Cloneable {
    public static final String unit = "kgm^2/s^3A^2";

    public Resistance(double value) {
        super(value, unit);
    }

    public Resistance(Resistance R) {
        this(R.getValue());
    }

    public Resistance(Voltage V, Current I) {
        this(V.div(I).getValue());
    }

    public Resistance(Power P, Current I) { this(P.div(I.pow(2)).getValue()); }

    public Resistance(Voltage V, Power P) { this(V.pow(2).div(P).getValue()); }

    public Resistance clone() {
        return new Resistance(this);
    }

    @Override
    public String toString() {
        return getValue()+" Ω";
    }
}
