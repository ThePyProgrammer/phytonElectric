package application.model.circuitry;

import java.util.*;
import application.model.quantity.*;

public class ParallelArray extends ArrayList<Series> {
    private Voltage pd;

    public ParallelArray(Series[] series) {
        HashMap<Voltage, Integer> acceptable = new HashMap<>();
        for(Series s: series) {
            boolean done = false;
            for (Map.Entry<Voltage, Integer> entry : acceptable.entrySet()) {
                if(entry.getKey().equals(s.getVoltage())) {
                    acceptable.put(entry.getKey(), acceptable.get(entry.getKey())+1);
                    done = true;
                    break;
                }
            }
            if(!done) {
                acceptable.put(s.getVoltage(), 1);
            }
        }
        pd =  Collections.max(acceptable.entrySet(), Map.Entry.comparingByValue()).getKey();
        for(Series s: series) {
            if(s.getVoltage().equals(pd)) add(s);
        }
    }


    public void add(int index, Series s) {
        if(s.getVoltage().equals(pd)) super.add(index, s);
    }

    public boolean add(Series s) {
        if(s.getVoltage().equals(pd)) return super.add(s);
        return false;
    }

    public Current effectiveI() {
        Current I = new Current(0);
        for(Series s: this) {
            I = new Current(I.add(s.getCurrent()).getValue());
        }
        return I;
    }

    public Resistance effectiveR() {
        Resistance Reff = new Resistance(0);
        for(Series s: this) {
            Reff = new Resistance(Reff.add(s.getResistance()).getValue());
        }
        return Reff;
    }

    public Voltage getPD() {
        return pd;
    }

    public Voltage getEMF() {
        return new Voltage(-pd.getValue());
    }

    @Override
    public String toString() {
        String s = "<Parallel p.d.="+pd+"effectiveR="+effectiveR()+">\n";
        for(Series series: this) {
            for (String sub: series.toString().split("\n")) {
                s += "\t" + sub + "\n";
            }
        }
        return s+"</Parallel>";
    }
}
