package application.model.circuitry;

import application.model.quantity.*;

public class Voltmeter extends Component {
    static double inf = Double.POSITIVE_INFINITY;
    public Voltmeter() {
        super(new Resistance(inf), new Current(0));
    }

    public Voltmeter setVoltage(Voltage V) {
        voltage = V;
        return this;
    }

}
