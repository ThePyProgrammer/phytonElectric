package application.model.quantity;

public class Intensity extends UnitValue {
    public static final String unit = "kg/s^3";

    public Intensity(double value) {
        super(value, unit);
    }

    public Intensity(Power P, Area A) {
        this(P.div(A).getValue());
    }

    @Override
    public String toString() {
        return removeRandomZeroes(String.format("%.3f", getValue())) + " W/m^2";
    }
}
