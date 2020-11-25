package application.model.util;

public class str {
    String string;

    public str(String string) {
        this.string = string;
    }

    public void replaceAll(String regex, String replacement) {
        string = string.replaceAll(regex, replacement);
    }

    public void replaceFirst(String regex, String replacement) {
        string = string.replaceFirst(regex, replacement);
    }

    @Override
    public String toString() {
        return string;
    }
}
