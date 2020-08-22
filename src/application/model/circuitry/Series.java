package application.model.circuitry;

import application.model.quantity.*;

public class Series extends Component {
    SeriesArray arr;

    public Series(Component ...components) {
            super(new SeriesArray(components).getCurrent(), new SeriesArray(components).effectivePD());
            arr = new SeriesArray(components);

    }

    public void add(int index, Series s) {
        arr.add(index, s);
    }

    public boolean add(Series s) {
        return arr.add(s);
    }

    public Voltage getVoltage() {
        return arr.getVoltage();
    }

    public Power getPower() {
        return new Power(getVoltage(), getCurrent());
    }

    public Resistance getResistance() {
        return arr.effectiveR();
    }

    @Override
    public String toString() {
        return arr.toString();
    }
}
