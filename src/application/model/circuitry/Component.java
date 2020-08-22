package application.model.circuitry;
import application.model.quantity.*;

public class Component {
    Current current;
    Voltage voltage;
    Resistance resistance;
    Power power;

    public Component(Current I, Voltage V) {
        this.current = I;
        this.voltage = V;
        this.resistance = new Resistance(V, I);
        this.power = new Power(V, I);
    }

    public Component(Current I, Resistance R) {
        this.current = I;
        this.resistance = R;
        this.voltage = new Voltage(I, R);
        this.power = new Power(I, R);
    }

    public Component(Current I, Power P) {
        this.current = I;
        this.power = P;
        this.voltage = new Voltage(P, I);
        this.resistance = new Resistance(P, I);
    }

    public Component(Voltage V, Current I) {
        this(I, V);
    }

    public Component(Voltage V, Resistance R) {
        this.voltage = V;
        this.resistance = R;
        this.current = new Current(V, R);
        this.power = new Power(V, R);
    }

    public Component(Voltage V, Power P) {
        this.voltage = V;
        this.power = P;
        this.current = new Current(P, V);
        this.resistance = new Resistance(V, P);
    }

    public Component(Resistance R, Current I) {
        this(I, R);
    }

    public Component(Resistance R, Voltage V) {
        this(V, R);
    }

    public Component(Resistance R, Power P) {
        this.resistance = R;
        this.power = P;
        this.current = new Current(P, R);
        this.voltage = new Voltage(P, R);
    }

    public Component(Power P, Current I) { this(I, P); }

    public Component(Power P, Voltage V) { this(V, P); }

    public Component(Power P, Resistance R) { this(R, P); }

    public Current getCurrent() {
        return current;
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public Resistance getResistance() {
        return resistance;
    }

    public Power getPower() {
        return power;
    }
}
