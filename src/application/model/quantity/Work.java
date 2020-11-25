package application.model.quantity;

public class Work extends UnitValue implements Cloneable {
    public static final String unit = "kgm^2/s^2";

    public Work(double value) {
        super(value, unit);
    }

    public Work(Energy E) {
        this(E.getValue());
    }

    public Work(Work W) {
        this(W.getValue());
    }

    public Work(Power P, Time t) {
        this(P.mul(t).getValue());
    }

    public Work clone() {
        return new Work(this);
    }

    @Override
    public String toString() {
        return getValue() + " J";
    }
}
