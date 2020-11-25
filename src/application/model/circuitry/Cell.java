package application.model.circuitry;

import application.model.quantity.Current;
import application.model.quantity.Power;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;

public class Cell extends Component {
    private static final Resistance r = new Resistance(0);
    private ComponentNode positive, negative;
    private boolean isNegative = false;


    public Cell(Power power, Voltage emf) {
        super(power, emf);
    }

    public Cell(Voltage emf) {
        super(r, emf);
        setCurrent(genericI());
    }

    public Cell(Voltage emf, Power power) {
        this(power, emf);
    }

    public Cell(Power power, Current current) {
        super(power, current);
    }

    public Cell(Current current, Power power) {
        this(power, current);
    }

    public Cell(Voltage emf, Current current) {
        super(emf, current);
        setCurrent(new Current(0));
        positive = getLeft();
        negative = getRight();
    }

    public Cell(Current current, Voltage emf) {
        this(emf, current);
    }

    public static Voltage genericEMF() {
        return new Voltage(1.5);
    }

    public static Current genericI() {
        return new Current(0);
    }

    public static Resistance genericR() {
        return r;
    }

    public Voltage getEMF() {
        return super.getVoltage();
    }

    public void setEMF(Voltage emf) {
        super.setVoltage(emf);
    }

    public void setCurrent(Current current) {
        super.setCurrent(current);
        power = new Power(current, getVoltage());
    }

    public ComponentNode getPositive() {
        return positive;
    }

    public ComponentNode getNegative() {
        return negative;
    }

    public boolean isNegative() {
        return isNegative;
    }

    public void setNegative(boolean negative) {
        isNegative = negative;
    }

    @Override
    public String toString() {
        return "<Cell P=\"" + super.getPower().toString().replaceAll("\\s", "") + "\" emf=\"" + getEMF().toString().replaceAll("\\s", "") + "\" x=\"" + getX() + "\" y=\"" + getY() + "\" angle=\"" + getAngle() + "\" prev=\"" + nodeToPrev() + "\" />";
    }

    public NegativeCell negate() {
        return new NegativeCell(this);
    }

    public static class NegativeCell extends Cell {
        Cell cell;

        public NegativeCell(Cell cell) {
            super(cell.getEMF(), cell.getPower());
            this.cell = cell;
            setCurrent(cell.getCurrent());
        }

        @Override
        public void setCurrent(Current current) {
            super.current = current;
            power = new Power(current, voltage);
            resistance = new Resistance(voltage, current);
        }

        @Override
        public String toString() {
            return "<Cell P=\"" + super.getPower().toString().replaceAll("\\s", "") + "\" emf=\"" + getEMF().toString().replaceAll("\\s", "") + "\" isNegative=\"true\" x=\"" + cell.getX() + "\" y=\"" + cell.getY() + "\" />";
        }
    }
}
