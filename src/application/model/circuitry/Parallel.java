package application.model.circuitry;

import application.model.quantity.*;

public class Parallel extends Component {
    ParallelArray arr;

    public Parallel(Series ...series) {
        super(new ParallelArray(series).getPD(), new ParallelArray(series).effectiveI());
        arr = new ParallelArray(series);
    }

    public void add(int index, Series s) {
        arr.add(index, s);
    }

    public boolean add(Series s) {
        return arr.add(s);
    }

    public Current getCurrent() {
        return arr.effectiveI();
    }

    public Power getPower() {
        return new Power(getVoltage(), getCurrent());
    }

    public Resistance getResistance() {
        return new Resistance(getVoltage(), getCurrent());
    }

    @Override
    public String toString() {
        return arr.toString();
    }
}
