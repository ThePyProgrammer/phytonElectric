package application.model.circuitry;

import java.util.*;
import application.model.quantity.*;

public class SeriesArray extends ArrayList<Component> {
    private Current current;
    public SeriesArray(Component[] components) {
        HashMap<Current, Integer> acceptable = new HashMap<>();
        for(Component component:components) {
            if(component instanceof Resistor || component instanceof Ammeter) continue;
            boolean done = false;
            for (Map.Entry<Current, Integer> entry : acceptable.entrySet()) {
                if(entry.getKey().equals(component.getCurrent())) {
                    acceptable.put(entry.getKey(), acceptable.get(entry.getKey())+1);
                    done = true;
                    break;
                }
            }
            if(!done) {
                acceptable.put(component.getCurrent(), 1);
            }
        }
        current = Collections.max(acceptable.entrySet(), Map.Entry.comparingByValue()).getKey();
        for(Component component: components) {
            if(component instanceof Ammeter) add(((Ammeter) component).setCurrent(current));
            if(component instanceof Resistor) add(((Resistor) component).setCurrent(current));
            if(component.getCurrent().equals(current)) add(component);
        }
    }

    public Current getCurrent() { return current; }


    public Resistance effectiveR() {
        Resistance Reff = new Resistance(0);
        for(Object o: this) {
            if(o instanceof Cell) continue;
            if(o instanceof Component) Reff = new Resistance(Reff.add(((Component) o).getResistance()).getValue());
        }
        return Reff;
    }

    public Voltage effectivePD() {
        Voltage Veff = new Voltage(0);
        for(Component c: this) {
            if(c instanceof Cell) Veff = new Voltage(Veff.sub(((Cell) c).getEMF()).getValue());
            else Veff = new Voltage(Veff.add(c.getVoltage()).getValue());
        }
        return Veff;
    }

    public Voltage effectiveEMF() {
        return new Voltage(-effectivePD().getValue());
    }

    public Voltage getVoltage() { return effectivePD(); }
    public Power getPower() { return new Power(getVoltage(), current); }
    public Resistance getResistance() { return new Resistance(getVoltage(), current); }

    @Override
    public void add(int index, Component component) {
        if(component instanceof Ammeter) super.add(index, ((Ammeter) component).setCurrent(current));
        if(component instanceof Resistor) super.add(index, ((Resistor) component).setCurrent(current));
        if(component.getCurrent().equals(current)) super.add(index, component);
    }

    @Override
    public boolean add(Component component) {
        if(component instanceof Resistor) return super.add(((Resistor) component).setCurrent(current));
        if(component.getCurrent().equals(current)) return super.add(component);
        return false;
    }

    @Override
    public String toString() {
        String s = "<Series current="+current+"effectiveR="+effectiveR()+">\n";
        for(Object o: this) {
            for (String sub: o.toString().split("\n")) {
                s += "\t" + sub + "\n";
            }
        }
        return s+"</Series>";
    }
}
