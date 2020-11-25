package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SeriesArray extends ArrayList<Component> {
    private final BooleanProperty isClosed = new SimpleBooleanProperty(false);
    private Resistance Reff;
    private Voltage voltage, emf;
    private Power power;
    private Current current;
    private Series series;

    public SeriesArray(Component... components) {
        super(Arrays.asList(components));
        processComponents();
    }


    /**
     * processes all the components in this array
     */
    private void processComponents() {
        Reff = new Resistance(0);
        emf = new Voltage(0);
        for (Component component : this) {
            if (component instanceof Cell.NegativeCell) emf.isub(component.getVoltage());
            else if (component instanceof Cell) emf.iadd(((Cell) component).getEMF());
            else Reff.iadd(component.getResistance());

        }

        if (Reff.getValue() == 0.0) {
            if (emf.getValue() == 0.0) {
                current = new Current(0);
            } else current = new Current(Double.POSITIVE_INFINITY);
            voltage = new Voltage(0.0);
            power = new Power(0.0);
        } else if (Reff.getValue() == Double.POSITIVE_INFINITY) {
            current = new Current(0.0);
            voltage = new Voltage(Double.POSITIVE_INFINITY);
            power = new Power(0.0);
        } else {
            current = new Current(emf, Reff);
            voltage = new Voltage(emf.getValue());
            power = new Power(current, voltage);
        }

        for (Component component : this) {
            component.setSeries(series);
        }

        setCurrent(current);
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        for (Component component : this) {
            component.setCurrent(current);
        }
    }

    public Resistance effectiveR() {
        return Reff;
    }

    public Voltage effectivePD() {
        Voltage Veff = new Voltage(0);
        for (Component c : this) {
            if (c instanceof Cell) Veff = new Voltage(Veff.sub(((Cell) c).getEMF()).getValue());
            else Veff = new Voltage(Veff.add(c.getVoltage()).getValue());
        }
        return Veff;
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public Voltage getEMF() {
        return emf;
    }

    public Power getPower() {
        return power;
    }

    public Resistance getResistance() {
        return Reff;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public void add(int index, Component component) {
        super.add(index, component);
        processComponents();
    }

    @Override
    public boolean add(Component component) {
        boolean bool = super.add(component);
        processComponents();
        return bool;
    }

    public void addAll(Component... components) {
        for (Component component : components) add(component);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("<Series current=\"" + current.toString().replaceAll("\\s", "") + "\" effectiveR=\"" + effectiveR().toString().replaceAll("\\s", "") + "\" isClosed=\"" + isClosed() + "\">\n");

        for (Component o : this) {
            for (String sub : o.toString().split("\n")) {
                s.append("\t").append(sub).append("\n");
            }
        }
        return s.toString() + "</Series>";
    }

    public String sortedToString() {
        String s = "<Series current=\"" + current.toString().replaceAll("\\s", "") + "\" effectiveR=\"" + effectiveR().toString().replaceAll("\\s", "") + "\" isClosed=\"" + isClosed() + "\">\n";
        ArrayList<String> arr = new ArrayList<>();
        for (Component o : this) {
            for (String sub : o.toString().split("\n")) {
                arr.add("\t" + sub);
                //s += "\t" + sub + "\n";
            }
        }
        Collections.sort(arr);
        s += String.join("\n", arr);
        return s + "\n</Series>";
    }

    public boolean equals(SeriesArray arr) {
        if (arr.size() != size()) return false;
        ArrayList<Component> duplicate1 = new ArrayList<>(this), duplicate2 = new ArrayList<>(arr);
        duplicate1.sort((o1, o2) -> {
            if (o1.getX() > o2.getX()) return 1;
            if (o1.getX() < o2.getX()) return -1;
            return Double.compare(o1.getY(), o2.getY());
        });
        duplicate2.sort((o1, o2) -> {
            if (o1.getX() > o2.getX()) return 1;
            if (o1.getX() < o2.getX()) return -1;
            return Double.compare(o1.getY(), o2.getY());
        });
        for (int i = 0; i < size(); i++) {
            if (!duplicate1.get(i).equals(duplicate2.get(i))) return false;
        }
        return true;
    }

    public boolean isClosed() {
        return isClosed.get();
    }

    public void setClosed(boolean isClosed) {
        this.isClosed.set(isClosed);
        if (!isClosed) {
            setCurrent(new Current(0));
        }
    }
}
