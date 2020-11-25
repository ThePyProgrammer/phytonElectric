package application.model.quantity;

public class Force extends UnitValue implements Cloneable {
    public static final String unit = "kgm/s^2";

    public Force(double value) {
        super(value, unit);
    }

    public Force(Force F) {
        this(F.getValue());
    }

    public Force(Mass m, Acceleration a) {
        this(m.mul(a).getValue());
    }

    public Force(Acceleration a, Mass m) {
        this(m.mul(a).getValue());
    }

    public Force(Power P, Velocity v) {
        this(P.div(v).getValue());
    }

    public Force(Energy E, Distance d) {
        this(E.div(d).getValue());
    }

    public Force(Work W, Distance d) {
        this(W.div(d).getValue());
    }

    public Force clone() {
        return new Force(this);
    }

    @Override
    public String toString() {
        return getValue() + " N";
    }
}
