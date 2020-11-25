package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;

public class Resistor extends Component {
    /**
     * default constructor, sets current as 0, resistance as 1.5
     */
    public Resistor() {
        this(new Resistance(1.5));
    }

    public Resistor(Resistance R) {
        super(new Current(0), R);
    }

    // mutators

    /**
     * @param R which is the resistance you want to set
     *          this changes the voltage and the power, while maintaining the current
     */
    public void setResistance(Resistance R) {
        resistance = R;
        voltage = new Voltage(current, resistance);
        power = new Power(current, resistance);
    }

    /**
     * @param current which is the current you want to set
     *                this changes the voltage and the power, while maintaining the resistance
     */
    public void setCurrent(Current current) {
        super.setCurrent(current);
        voltage = new Voltage(current, resistance);
        power = new Power(current, resistance);
    }

    public String toString() {
        return "<Resistor R=\"" + resistance.toString().replaceAll("\\s", "") + "\" I=\"" + current.toString().replaceAll("\\s", "") + "\" x=\"" + getX() + "\" y=\"" + getY() + "\" angle=\"" + getAngle() + "\" prev=\"" + nodeToPrev() + "\" />";
    }
}
