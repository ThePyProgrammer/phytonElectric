package application.model.quantity;

public class Time extends UnitValue implements Cloneable {
    public static final String unit = "s";

    public Time(double value) {
        super(value, unit);
    }

    public Time(Time t) {
        this(t.getValue());
    }

    public Time(Distance d, Velocity v) {
        this(d.div(v).getValue());
    }

    public Time(Velocity v, Acceleration a) {
        this(v.div(a).getValue());
    }

    public Time(Charge Q, Current I) {
        this(Q.div(I).getValue());
    }

    public Time(Energy E, Power P) {
        this(E.div(P).getValue());
    }

    public Time(Work W, Power P) {
        this(W.div(P).getValue());
    }

    public Time clone() {
        return new Time(this);
    }

    @Override
    public String toString() {
        return getValue()+" s";
    }
}
