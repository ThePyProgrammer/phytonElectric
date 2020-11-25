package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;

import java.util.ArrayList;
import java.util.Arrays;

public class ParallelArray extends ArrayList<Series> {
    private final ParallelNode node;
    private final ArrayList<Series> outwards;
    private final ArrayList<Series> inwards;
    private Resistance Reff;
    private Voltage voltage;
    private Current current;

    public ParallelArray(ParallelNode node, Series... series) {
        super(Arrays.asList(series));
        inwards = new ArrayList<>();
        outwards = new ArrayList<>();
        this.node = node;
        processComponents();
    }

    private void processComponents() {
        inwards.clear();
        outwards.clear();
        for (Node connectedNode : node.connectedNodes()) {
            Component component = connectedNode.getSelf();
            Series componentSeries = component.getSeries();
            for (Series series : this) {
                if (series.equals(componentSeries)) {
                    if (series.getArray().indexOf(component) == 0) {
                        if (series.leftToRight) outwards.add(series);
                        else inwards.add(series);
                    } else {
                        if (!series.leftToRight) outwards.add(series);
                        else inwards.add(series);
                    }
                }
            }
        }

        double resistance = 0, seriesR;
        double emf = 0;

        for (Series series : outwards) {
            seriesR = series.getResistance().getValue();
            for (Component component : series.arr) {
                if (component instanceof Cell.NegativeCell) {
                    seriesR -= component.getResistance().getValue();
                } else if (component instanceof Cell) {
                    seriesR += ((Cell) component).negate().getResistance().getValue();
                }
            }
            resistance += 1 / seriesR;
        }

        resistance = 1 / resistance;

        for (Series series : inwards) {
            seriesR = series.getResistance().getValue();
            emf += series.getEMF().getValue();
            resistance += seriesR;
        }

        Reff = new Resistance(resistance);
        voltage = new Voltage(emf);
        current = new Current(voltage, Reff);

        setCurrent(current);

    }

    public void setCurrent(Current current) {
        for (Series series : this) {
            for (Component component : series.arr) {
                if (inwards.contains(series)) {

                }
                if (component instanceof Resistor || component instanceof Bulb || component instanceof Cell.NegativeCell) {
                    double current_N = current.mul(Reff).div(component.getResistance()).getValue();
                    component.setCurrent(new Current(current_N));
                } else if (component instanceof Cell) component.setCurrent(current);
                else component.setCurrent(current);
            }
        }
    }


    public void add(int index, Series s) {
        super.add(index, s);
        processComponents();
    }

    public boolean add(Series s) {
        boolean bool = super.add(s);
        processComponents();
        return bool;
    }

    public void addAll(Series... multipleSeries) {
        for (Series series : multipleSeries) add(series);
    }

    public Current effectiveI() {
        return current;
    }

    public Resistance effectiveR() {
        return Reff;
    }

    public Voltage getPD() {
        return voltage;
    }

    @Override
    public String toString() {
        String s = "<Parallel voltage=" + voltage + "effectiveR=" + Reff + ">\n";
        for (Series series : this) {
            for (String sub : series.toString().split("\n")) {
                s += "\t" + sub + "\n";
            }
        }
        return s + "</Parallel>";
    }
}
