package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;

import java.util.Collection;

public class Series extends Component {
    SeriesArray arr;
    boolean leftToRight;

    public Series(Component... components) {
        super(new Current(0), new SeriesArray(components).effectivePD());
        arr = new SeriesArray();
        arr.setSeries(this);
        arr.addAll(components);
    }

    public Series(Collection<Component> components) {
        this(components.toArray(new Component[0]));
    }

    public void add(int index, Series s) {
        arr.add(index, s);
    }

    public boolean add(Series s) {
        return arr.add(s);
    }

    public void add(int index, Component c) {
        arr.add(index, c);
    }

    public boolean add(Component c) {
        return arr.add(c);
    }

    public Voltage getVoltage() {
        return arr.getVoltage();
    }

    public Voltage getEMF() {
        return arr.getEMF();
    }

    public Power getPower() {
        return new Power(getVoltage(), getCurrent());
    }

    public Resistance getResistance() {
        return arr.effectiveR();
    }

    public Current getCurrent() {
        return arr.getCurrent();
    }

    public void setCurrent(Current current) {
        arr.setCurrent(current);
    }

    public boolean isClosed() {
        return arr.isClosed();
    }

    public void setClosed(boolean isClosed) {
        arr.setClosed(isClosed);
    }

    @Override
    public String toString() {
        return arr.toString();
    }

    public String sortedToString() {
        return arr.sortedToString();
    }

    public SeriesArray getArray() {
        return arr;
    }

    public boolean equals(Series series) {
        if (series == this) return true;
        if (series == null) return false;
        if (sortedToString().equals(series.sortedToString())) return true;
        return arr.equals(series.arr) && getCurrent() == series.getCurrent() && getPower() == series.getPower();
    }
}
