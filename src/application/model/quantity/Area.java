package application.model.quantity;

public class Area extends UnitValue implements Cloneable {
    public static final String unit = "m^2";

    public Area(double value) {
        super(value, unit);
    }

    public Area(Area A) {
        this(A.getValue());
    }

    public Area clone() {
        return new Area(this);
    }

    @Override
    public String toString() {
        return getValue() + " m^2";
    }
}
