package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;

public class CircuitObject {
    Current current;
    Voltage voltage;
    Resistance resistance;
    Power power;

    public CircuitObject(Current I, Voltage V) {
        this.current = I;
        this.voltage = V;
        this.resistance = new Resistance(V, I);
        this.power = new Power(V, resistance);
    }

    public CircuitObject(Current I, Resistance R) {
        this.current = I;
        this.resistance = R;
        this.voltage = new Voltage(I, R);
        this.power = new Power(I, resistance);
    }

    public CircuitObject(Current I, Power P) {
        this.current = I;
        this.power = P;
        this.voltage = new Voltage(P, I);
        this.resistance = new Resistance(P, I);
    }

    public CircuitObject(Voltage V, Resistance R) {
        this.voltage = V;
        this.resistance = R;
        this.current = new Current(V, R);
        this.power = new Power(V, R);
    }

    public CircuitObject(Voltage V, Power P) {
        this.voltage = V;
        this.power = P;
        this.current = new Current(P, V);
        this.resistance = new Resistance(V, P);
    }

    public CircuitObject(Resistance R, Power P) {
        this.resistance = R;
        this.power = P;
        this.current = new Current(P, R);
        this.voltage = new Voltage(P, R);
    }

    // Accessors and Mutators

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public void setVoltage(Voltage voltage) {
        this.voltage = voltage;
    }

    public Resistance getResistance() {
        return resistance;
    }

    public void setResistance(Resistance resistance) {
        this.resistance = resistance;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }
}
