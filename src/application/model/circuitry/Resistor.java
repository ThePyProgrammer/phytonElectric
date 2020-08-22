package application.model.circuitry;
import application.model.quantity.*;

public class Resistor extends Component {
    public Resistor(Resistance R) {
        super(new Current(1), R);
    }

    public Resistor setCurrent(Current I) {
        current = I;
        voltage = new Voltage(I, resistance);
        power = new Power(I, resistance);
        return this;
    }
}
