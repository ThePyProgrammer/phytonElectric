package application.model.circuitry;
import application.model.quantity.*;

public class Cell extends Component {
    private static Resistance resistance = new Resistance(0);
    private int index;
    private static int cnt = 1;


    public static Voltage genericEMF() {
        return new Voltage(1.5);
    }

    public static Current genericI() {
        return new Current(0);
    }

    public Voltage getEMF() {
        return super.getVoltage();
    }

    public int getIndex() {
        return index;
    }

    public static int getCnt() {
        return cnt;
    }

    public Cell(Power power, Voltage emf) {
        super(power, emf);
        index = cnt++;
    }

    public Cell(Voltage emf, Power power) { this(power, emf); }

    public Cell(Power power, Current current) {
        super(power, current);
        index = cnt++;
    }

    public Cell(Current current, Power power) { this(power, current); }

    public Cell(Voltage emf, Current current) {
        super(emf, current);
        index = cnt++;
    }

    public Cell(Current current, Voltage emf) { this(emf, current); }

    @Override
    public String toString() {
        return "<Cell index="+index+" power="+super.getPower()+" emf="+getEMF()+">";
    }
}
