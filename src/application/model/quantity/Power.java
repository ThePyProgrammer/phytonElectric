package application.model.quantity;

public class Power extends UnitValue implements Cloneable {
    public static final String unit = "kgm^2/s^3";

    public Power(double value) {
        super(value, unit);
    }

    public Power(Power P) {
        this(P.getValue());
    }

    public Power(Energy E, Time t) {
        this(E.div(t).getValue());
    }

    public Power(Work W, Time t) {
        this(W.div(t).getValue());
    }

    public Power(Voltage V, Current I) {
        this(V.mul(I).getValue());
    }

    public Power(Current I, Voltage V) {
        this(V.mul(I).getValue());
    }

    public Power(Current I, Resistance R) {
        this(I.pow(2).mul(R).getValue());
    }

    public Power(Resistance R, Current I) {
        this(I.pow(2).mul(R).getValue());
    }

    public Power(Voltage V, Resistance R) {
        this(V.pow(2).div(R).getValue());
    }

    public Power(Resistance R, Voltage V) {
        this(V.pow(2).div(R).getValue());
    }

    public Power clone() {
        return new Power(this);
    }

    @Override
    public String toString() {
        return getValue()+" W";
    }
}
