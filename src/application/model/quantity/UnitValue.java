package application.model.quantity;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitValue implements Cloneable, Comparable {
    private static final String[] SIs = {"kg", "mol", "m", "A", "s", "K", "cd"};
    private String unit;
    private double value;
    private HashMap<String, Double> powers;

    public UnitValue(double value, String unit) {
        setUnit(unit);
        setValue(value);
    }

    public UnitValue(UnitValue uv) {
        this(uv.getValue(), uv.getUnit());
    }

    private static String deriveNumer(String unit) {
        if (!unit.contains("/")) return unit;
        if (unit.startsWith("1/")) return "";
        return unit.split("/")[0];
    }

    private static String deriveDenom(String unit) {
        if (!unit.contains("/")) return "";
        return unit.split("/")[0];
    }

    private static String formUnit(String numer, String denom) {
        if (numer.length() > 0 && denom.length() > 0) return numer + '/' + denom;
        else if (numer.length() > 0) return numer;
        else if (denom.length() > 0) return "1/" + denom;
        return "";
    }

    public UnitValue clone() {
        return new UnitValue(this);
    }

    public UnitValue add(UnitValue... uvs) {
        double val = value;
        for (UnitValue uv : uvs) {
            if (uv.getUnit().equals(unit)) val += uv.getValue();
        }
        return new UnitValue(val, unit);
    }

    public void iadd(UnitValue... uvs) {
        double val = value;
        for (UnitValue uv : uvs) {
            if (uv.getUnit().equals(unit)) val += uv.getValue();
        }
        value = val;
    }

    public UnitValue sub(UnitValue... uvs) {
        double val = value;
        for (UnitValue uv : uvs) {
            if (uv.getUnit().equals(unit)) val -= uv.getValue();
        }
        return new UnitValue(val, unit);
    }

    public void isub(UnitValue... uvs) {
        double val = value;
        for (UnitValue uv : uvs) {
            if (uv.getUnit().equals(unit)) val -= uv.getValue();
        }
        value = val;
    }

    public UnitValue subtract(UnitValue uv) {
        return sub(uv);
    }

    public UnitValue mul(UnitValue... uvs) {
        String numer = deriveNumer(), denom = deriveDenom();
        double val = value;
        for (UnitValue uv : uvs) {
            val *= uv.getValue();
            numer += uv.deriveNumer();
            denom += uv.deriveDenom();
        }
        return new UnitValue(val, formUnit(numer, denom));
    }

    public UnitValue multiply(UnitValue uv) {
        return mul(uv);
    }

    public UnitValue div(UnitValue uv) {
        String numer = deriveNumer(), denom = deriveDenom();
        double val = value / uv.getValue();
        denom += uv.deriveNumer();
        numer += uv.deriveDenom();
        return new UnitValue(val, formUnit(numer, denom));
    }

    public UnitValue divide(UnitValue uv) {
        return div(uv);
    }

    public UnitValue pow(double pow) {
        String numer = "", denom = "";
        for (Map.Entry<String, Double> entry : powers.entrySet()) {
            double p = entry.getValue() * pow;
            if (p == 0) continue;
            else if (p == 1) numer += entry.getKey();
            else if (p == -1) denom += entry.getKey();
            else if (p > 0) numer += entry.getKey() + "^" + p;
            else if (p < 0) denom += entry.getKey() + "^" + Math.abs(p);
        }
        return new UnitValue(Math.pow(value, pow), formUnit(numer, denom));
    }

    public UnitValue negate() {
        return new UnitValue(-value, unit);
    }

    private String deriveNumer() {
        if (!unit.contains("/")) return unit;
        if (unit.startsWith("1/")) return "";
        return unit.split("/")[0];
    }

    private String deriveDenom() {
        if (!unit.contains("/")) return "";
        return unit.split("/")[1];
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = fix(unit);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private UnitValue classify(UnitValue uv) {
        if (uv.getUnit().equals("kgm^2/s^3")) return new Power(uv.getValue());
        if (uv.getUnit().equals("m/s^2")) return new Acceleration(uv.getValue());
        return uv;
    }

    private String fix(String unit) {
        String numer = "", denom = "", ognum = unit, ogden = "";
        if (unit.contains("/")) {
            String[] temp = unit.split("/");
            ognum = temp[0];
            ogden = temp[1];
        }

        powers = new HashMap<>();
        Matcher m;

        for (String u : SIs) {
            double p = 0;
            // Pattern re = Pattern.compile(String.format("(%s\\^)(-?\\d+(\\.\\d+)?)", u));
            int start;
            while (ognum.contains(u + "^")) {
                start = ognum.indexOf(u + "");
                String str = "";
                for (char i : ognum.substring(start + u.length() + 1).toCharArray()) {
                    if ((m = Pattern.compile("\\d|\\.").matcher("" + i)).matches()) str += m.group();
                    else break;

                }
                p += str.contains(".") ? Double.parseDouble(str) : Integer.parseInt(str);
                ognum = ognum.replaceFirst(u + "\\^" + str, "");
            }
            while (ognum.contains(u)) {
                p++;
                ognum = ognum.replaceFirst(u, "");
            }


            while (ogden.contains(u + "^")) {
                start = ogden.indexOf(u + "");
                String str = "";
                for (char i : ogden.substring(start + u.length() + 1).toCharArray()) {
                    if ((m = Pattern.compile("\\d|\\.").matcher("" + i)).matches()) str += m.group();
                    else break;
                }
                p -= str.contains(".") ? Double.parseDouble(str) : Integer.parseInt(str);
                ogden = ogden.replaceFirst(u + "\\^" + str, "");
            }
            while (ogden.contains(u)) {
                p--;
                ogden = ogden.replaceFirst(u, "");
            }
            powers.put(u, p);
        }

        for (Map.Entry<String, Double> entry : powers.entrySet()) {
            if (entry.getValue() == 0) continue;
            else if (entry.getValue() == 1) numer += entry.getKey();
            else if (entry.getValue() == -1) denom += entry.getKey();
            else if (entry.getValue() > 0) numer += entry.getKey() + "^" + entry.getValue();
            else if (entry.getValue() < 0) denom += entry.getKey() + "^" + Math.abs(entry.getValue());
        }
        return formUnit(numer, denom);
    }

    public String removeRandomZeroes(String str) {
        if (str.contains(".")) {
            while (str.charAt(str.length() - 1) == '0') {
                str = str.substring(0, str.length() - 1);
            }
            if (str.charAt(str.length() - 1) == '.') {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str.trim();
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    public String toString(Locale locale) {
        return NumberFormat.getNumberInstance().format(value) + " " + unit;
    }


    public boolean equals(UnitValue other) {
        return value == other.value && unit.equals(other.unit);
    }


    public int compareTo(UnitValue o) {
        if (getValue() > o.getValue()) return 1;
        if (getValue() == o.getValue()) return 0;
        return -1;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof UnitValue) {
            return compareTo((UnitValue) o);
        } else return -1;
    }
}