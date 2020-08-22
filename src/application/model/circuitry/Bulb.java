package application.model.circuitry;
import application.model.quantity.*;

public class Bulb extends Component {
    private final String repr = "⊗";
    private int index;
    private static int cnt = 1;

    public static Voltage genericV() {
        return new Voltage(120);
    }

    public static Current genericI() {
        return new Current(0);
    }

    public Bulb(Current I, Voltage V) {
        super(I, V);
        index = cnt++;
    }

    public Bulb(Current I, Resistance R) {
        super(I, R);
        index = cnt++;
    }

    public Bulb(Current I, Power P) {
        super(I, P);
        index = cnt++;
    }

    public Bulb(Voltage V, Current I) {
        super(V, I);
        index = cnt++;
    }

    public Bulb(Voltage V, Resistance R) {
        super(V, R);
        index = cnt++;
    }

    public Bulb(Voltage V, Power P) {
        super(V, P);
        index = cnt++;
    }

    public Bulb(Resistance R, Current I) {
        super(R, I);
        index = cnt++;
    }

    public Bulb(Resistance R, Voltage V) {
        super(R, V);
        index = cnt++;
    }

    public Bulb(Resistance R, Power P) {
        super(R, P);
        index = cnt++;
    }

    public Bulb(Power P, Current I) {
        super(P, I);
        index = cnt++;
    }

    public Bulb(Power P, Voltage V) {
        super(P, V);
        index = cnt++;
    }

    public Bulb(Power P, Resistance R) {
        super(P, R);
        index = cnt++;
    }

    public int getIndex() {
        return index;
    }

    public static int getCnt() {
        return cnt;
    }
}
