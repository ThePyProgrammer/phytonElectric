package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;

public class Parallel extends Series {
    ParallelArray arr;

    public Parallel(ParallelNode node, Series... series) {
        arr = new ParallelArray(node, series);
        setResistance(arr.effectiveR());
        setCurrent(arr.effectiveI());
        setVoltage(arr.getPD());
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
