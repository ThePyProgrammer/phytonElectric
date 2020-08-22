package application.model.circuitry;
import application.model.quantity.*;

public class Ammeter extends Component {
    static double inf = Double.POSITIVE_INFINITY;
    public Ammeter() {
        super(new Resistance(0), new Voltage(0));
    }
    public Ammeter setCurrent(Current I) {
        current = I;
        return this;
    }

}