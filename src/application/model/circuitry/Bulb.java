package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;

public class Bulb extends Component {
    /**
     * Default constructor of Bulb class, where a Bulb of 1 ohms and 0 amperes has been created
     */
    public Bulb() {
        this(genericI(), genericR());
    }

    public Bulb(Current I, Voltage V) {
        super(I, V);
    }

    public Bulb(Current I, Resistance R) {
        super(I, R);
    }

    public Bulb(Current I, Power P) {
        super(I, P);
    }

    public Bulb(Voltage V, Current I) {
        super(V, I);
    }

    public Bulb(Voltage V, Resistance R) {
        super(V, R);
    }

    public Bulb(Voltage V, Power P) {
        super(V, P);
    }

    public Bulb(Resistance R, Current I) {
        super(R, I);
    }

    public Bulb(Resistance R, Voltage V) {
        super(R, V);
    }

    public Bulb(Resistance R, Power P) {
        super(R, P);
    }

    public Bulb(Power P, Current I) {
        super(P, I);
    }

    public Bulb(Power P, Voltage V) {
        super(P, V);
    }

    public Bulb(Power P, Resistance R) {
        super(P, R);
    }

    /**
     * @return a generic resistance of 1 ohms
     */
    public static Resistance genericR() {
        return new Resistance(1);
    }

    /**
     * @return a generic current of 0 amperes
     */
    public static Current genericI() {
        return new Current(0);
    }

    public static String getRepr() {
        return "⊗";
    }

    /**
     * @param current which is the current you want to set
     *                this changes the voltage and the power, while maintaining the resistance
     */
    public void setCurrent(Current current) {
        this.current = current;
        this.voltage = new Voltage(current, resistance);
        this.power = new Power(current, resistance);
    }

    public String toString() {
        return "<Bulb I=\"" + current.toString().replaceAll("\\s", "") + "\" R=\"" + resistance.toString().replaceAll("\\s", "") + "\" x=\"" + getX() + "\" y=\"" + getY() + "\" angle=\"" + getAngle() + "\" prev=\"" + nodeToPrev() + "\" />";
    }
}
