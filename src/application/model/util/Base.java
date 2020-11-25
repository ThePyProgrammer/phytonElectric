package application.model.util;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

public interface Base {
    // Math
    double PI = Math.PI, pi = Math.PI, E = Math.E, e = Math.E;
    // Random
    Random random = new Random();

    static double sin(double angle) {
        return Math.sin(angle);
    }

    static double sind(double angle) {
        return Math.sin(toRadians(angle));
    }

    static double[] sin(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = sin(angles[i]);
        return arr;
    }

    static Collection<Double> sin(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(sin(angle));
        return arr;
    }

    static double cos(double angle) {
        return Math.cos(angle);
    }

    static double[] cos(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = cos(angles[i]);
        return arr;
    }

    static Collection<Double> cos(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(cos(angle));
        return arr;
    }

    static double tan(double angle) {
        return Math.tan(angle);
    }

    static double[] tan(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = tan(angles[i]);
        return arr;
    }

    static Collection<Double> tan(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(tan(angle));
        return arr;
    }

    static double asin(double angle) {
        return Math.asin(angle);
    }

    static double[] asin(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = asin(angles[i]);
        return arr;
    }

    static Collection<Double> asin(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(asin(angle));
        return arr;
    }

    static double acos(double angle) {
        return Math.acos(angle);
    }

    static double[] acos(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = acos(angles[i]);
        return arr;
    }

    static Collection<Double> acos(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(acos(angle));
        return arr;
    }

    static double atan(double angle) {
        return Math.atan(angle);
    }

    static double[] atan(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = atan(angles[i]);
        return arr;
    }

    static Collection<Double> atan(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(atan(angle));
        return arr;
    }

    static double sinh(double angle) {
        return Math.sinh(angle);
    }

    static double[] sinh(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = sinh(angles[i]);
        return arr;
    }

    static Collection<Double> sinh(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(sinh(angle));
        return arr;
    }

    static double cosh(double angle) {
        return Math.cosh(angle);
    }

    static double[] cosh(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = cosh(angles[i]);
        return arr;
    }

    static Collection<Double> cosh(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(cosh(angle));
        return arr;
    }

    static double tanh(double angle) {
        return Math.tanh(angle);
    }

    static double[] tanh(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = tanh(angles[i]);
        return arr;
    }

    static Collection<Double> tanh(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(tanh(angle));
        return arr;
    }

    static double sqrt(double angle) {
        return Math.sqrt(angle);
    }

    static double[] sqrt(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = sqrt(angles[i]);
        return arr;
    }

    static Collection<Double> sqrt(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(sqrt(angle));
        return arr;
    }

    static double cbrt(double angle) {
        return Math.cbrt(angle);
    }

    static double[] cbrt(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = cbrt(angles[i]);
        return arr;
    }

    static Collection<Double> cbrt(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(cbrt(angle));
        return arr;
    }

    static double log(double angle) {
        return Math.log(angle);
    }

    static double[] log(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = log(angles[i]);
        return arr;
    }

    static Collection<Double> log(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(log(angle));
        return arr;
    }

    static double log10(double angle) {
        return Math.log10(angle);
    }

    static double[] log10(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = log10(angles[i]);
        return arr;
    }

    static Collection<Double> log10(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(log10(angle));
        return arr;
    }

    static double log1p(double angle) {
        return Math.log1p(angle);
    }

    static double[] log1p(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = log1p(angles[i]);
        return arr;
    }

    static Collection<Double> log1p(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(log1p(angle));
        return arr;
    }

    static double exp(double angle) {
        return Math.exp(angle);
    }

    static double[] exp(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = exp(angles[i]);
        return arr;
    }

    static Collection<Double> exp(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(exp(angle));
        return arr;
    }

    static double expm1(double angle) {
        return Math.expm1(angle);
    }

    static double[] expm1(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = expm1(angles[i]);
        return arr;
    }

    static Collection<Double> expm1(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(expm1(angle));
        return arr;
    }

    static double toRadians(double angle) {
        return Math.toRadians(angle);
    }

    static double[] toRadians(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = toRadians(angles[i]);
        return arr;
    }

    static Collection<Double> toRadians(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(toRadians(angle));
        return arr;
    }

    static double toDegrees(double angle) {
        return Math.toDegrees(angle);
    }

    static double[] toDegrees(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = toDegrees(angles[i]);
        return arr;
    }

    static Collection<Double> toDegrees(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(toDegrees(angle));
        return arr;
    }

    static double signum(double angle) {
        return Math.signum(angle);
    }

    static double[] signum(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = signum(angles[i]);
        return arr;
    }

    static Collection<Double> signum(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(signum(angle));
        return arr;
    }

    static double ceil(double angle) {
        return Math.ceil(angle);
    }

    static double[] ceil(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = ceil(angles[i]);
        return arr;
    }

    static Collection<Double> ceil(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(ceil(angle));
        return arr;
    }

    static double floor(double angle) {
        return Math.floor(angle);
    }

    static double[] floor(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = floor(angles[i]);
        return arr;
    }

    static Collection<Double> floor(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(floor(angle));
        return arr;
    }

    static double round(double angle) {
        return Math.round(angle);
    }

    static double[] round(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = round(angles[i]);
        return arr;
    }

    static Collection<Double> round(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(round(angle));
        return arr;
    }

    static double rint(double angle) {
        return Math.rint(angle);
    }

    static double[] rint(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = rint(angles[i]);
        return arr;
    }

    static Collection<Double> rint(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(rint(angle));
        return arr;
    }

    static double ulp(double angle) {
        return Math.ulp(angle);
    }

    static double[] ulp(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = ulp(angles[i]);
        return arr;
    }

    static Collection<Double> ulp(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(ulp(angle));
        return arr;
    }

    static double getExponent(double angle) {
        return Math.getExponent(angle);
    }

    static double[] getExponent(double... angles) {
        double[] arr = new double[angles.length];
        for (int i = 0; i < angles.length; i++) arr[i] = getExponent(angles[i]);
        return arr;
    }

    static Collection<Double> getExponent(Collection<Double> angles) {
        ArrayList<Double> arr = new ArrayList<>();
        for (double angle : angles) arr.add(getExponent(angle));
        return arr;
    }

    static double max(int... values) {
        double maxn = Double.NEGATIVE_INFINITY;
        for (int value : values) {
            if ((double) value > maxn) maxn = value;
        }
        return maxn;
    }

    static double max(double... values) {
        double maxn = Double.NEGATIVE_INFINITY;
        for (double value : values) {
            if (value > maxn) maxn = value;
        }
        return maxn;
    }

    static double max(float... values) {
        double maxn = Double.NEGATIVE_INFINITY;
        for (float value : values) {
            if (value > maxn) maxn = value;
        }
        return maxn;
    }

    static double max(long... values) {
        double maxn = Double.NEGATIVE_INFINITY;
        for (long value : values) {
            if ((double) value > maxn) maxn = value;
        }
        return maxn;
    }

    static double max(short... values) {
        double maxn = Double.NEGATIVE_INFINITY;
        for (short value : values) {
            if ((double) value > maxn) maxn = value;
        }
        return maxn;
    }

    static double min(int... values) {
        double maxn = Double.POSITIVE_INFINITY;
        for (int value : values) {
            if ((double) value < maxn) maxn = value;
        }
        return maxn;
    }

    static double min(double... values) {
        double maxn = Double.POSITIVE_INFINITY;
        for (double value : values) {
            if (value < maxn) maxn = value;
        }
        return maxn;
    }

    static double min(float... values) {
        double maxn = Double.POSITIVE_INFINITY;
        for (float value : values) {
            if (value < maxn) maxn = value;
        }
        return maxn;
    }

    static double min(long... values) {
        double maxn = Double.POSITIVE_INFINITY;
        for (long value : values) {
            if ((double) value < maxn) maxn = value;
        }
        return maxn;
    }

    static double min(short... values) {
        double maxn = Double.POSITIVE_INFINITY;
        for (short value : values) {
            if ((double) value < maxn) maxn = value;
        }
        return maxn;
    }

    static int[] fib(double upperBound) {
        int a = 0, b = 1, temp;
        ArrayList<Integer> arr = new ArrayList<>();
        while (a < upperBound) {
            arr.add(a);
            temp = b;
            b = a + b;
            a = temp;
        }

        int[] array = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) array[i] = arr.get(i);
        return array;
    }

    static void print(Object... objs) {
        for (Object o : objs) System.out.print(o);
    }

    static String input(String inp) {
        System.out.print(inp);
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        sc.close();
        return line;
    }

    static String input() {
        return input("");
    }

    static int randInt() {
        return random.nextInt();
    }

    static int randInt(int upperBound) {
        return random.nextInt(upperBound);
    }

    static int randInt(int lowerBound, int upperBound) {
        return lowerBound + randInt(upperBound - lowerBound);
    }

    static int randInt(int lowerBound, int upperBound, int step) {
        return lowerBound + step * randInt((upperBound - lowerBound - 1) / step - 1);
    }

    static double randDouble() {
        return random.nextDouble();
    }

    static double randDouble(double upperBound) {
        return Math.random() * upperBound;
    }

    static double randDouble(double lowerBound, double upperBound) {
        return lowerBound + Math.random() * (upperBound - lowerBound);
    }

    static double randDouble(double lowerBound, double upperBound, double step) {
        return lowerBound + Math.random() * ((upperBound - lowerBound - 1) / step - 1);
    }

    static float randFloat() {
        return random.nextFloat();
    }

    static float randFloat(float upperBound) {
        return (float) Math.random() * upperBound;
    }

    static float randFloat(float lowerBound, float upperBound) {
        return lowerBound + (float) Math.random() * (upperBound - lowerBound);
    }

    static float randFloat(float lowerBound, float upperBound, float step) {
        return lowerBound + (float) Math.random() * ((upperBound - lowerBound - 1) / step - 1);
    }

    static boolean randBoolean() {
        return random.nextBoolean();
    }

    static long randLong() {
        return random.nextLong();
    }

    static long randLong(long upperBound) {
        return (long) floor(Math.random() * upperBound);
    }

    static long randLong(long lowerBound, long upperBound) {
        return lowerBound + (long) floor(Math.random() * (upperBound - lowerBound));
    }

    static long randLong(long lowerBound, long upperBound, long step) {
        return lowerBound + (long) floor(Math.random() * ((upperBound - lowerBound - 1) / step - 1));
    }

    // Output
    default void print() throws IOException {
        print(System.out);
    }

    default void print(Writer out) throws IOException {
        if (out instanceof PrintWriter) ((PrintWriter) out).println(toString());
        else if (out instanceof BufferedWriter) out.write(toString());
        else if (out instanceof FileWriter) out.write(toString());
    }

    default void print(OutputStream outStream) throws IOException {
        if (outStream instanceof FileOutputStream) outStream.write(toString().getBytes());
        else if (outStream instanceof DataOutputStream) ((DataOutputStream) outStream).writeUTF(toString());
        else if (outStream instanceof BufferedOutputStream) new DataOutputStream(outStream).writeUTF(toString());
        else if (outStream instanceof PrintStream) outStream.write(toString().getBytes());
    }

    default void print(FileChannel channel) throws IOException {
        byte[] strBytes = toString().getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
        buffer.put(strBytes);
        buffer.flip();
        channel.write(buffer);
    }

    default void print(Path path) throws IOException {
        Files.writeString(path, toString());
    }

    default void print(java.io.File file) throws IOException {
        PrintWriter pw = new PrintWriter(file);
        print(pw);
        pw.close();
    }

    default void print(File file) throws IOException {
        if (file.out != null) file.out.println(file);
        else print(new java.io.File(file.getAbsolutePath()));
    }

    default void print(File parent, String child) throws IOException {
        print(new java.io.File(parent, child));
    }

    default void print(String parent, String child) throws IOException {
        print(new java.io.File(parent, child));
    }

    default void print(URI uri) throws IOException {
        print(new File(uri, 'w'));
    }

    default void print(URL url) throws IOException {
        print(new File(url, 'w'));
    }
}
