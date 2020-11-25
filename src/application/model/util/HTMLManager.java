package application.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLManager {
    private Set<String> variables;
    private HashMap<String, String> map;
    private String html, filePath;
    private File file, actualFile;
    private Pattern p = Pattern.compile("\\$([a-zA-Z_]\\w*)"),
            css = Pattern.compile("<link rel=\"stylesheet\" href=\"(.*)\">"),
            url = Pattern.compile("url\\(\"(.*)\"\\)"),
            js = Pattern.compile("<script type=\"text/javascript\" src=\"(.*)\"></script>");

    public HTMLManager(String html) {
        Matcher m = p.matcher(html);
        variables = new HashSet<>();
        map = new HashMap<>();
        while (m.find()) {
            variables.add(m.group(1));
        }
        for (String variable : variables) map.put(variable, "");
        this.html = html;
    }

    public HTMLManager(File file) {
        this(file.read());
        file.close();
        this.file = file.getParentFile();
        actualFile = file;

        String[] arr = file.getAbsolutePath().split("\\\\");
        arr[arr.length - 1] = "";
        filePath = String.join("\\", Arrays.copyOfRange(arr, 0, arr.length - 2));

        processFile();

    }

    public HTMLManager(java.io.File file) {
        this(File.read(file));
    }

    public void processFile() {
        // replaceItAll(url, "url(\"%s\")", s -> File.relative(this.file, s));
        Matcher m = css.matcher(html);
        html = m.replaceAll(matchResult -> {
            String location = File.relative(this.file, matchResult.group(1));
            String stylesheet = File.readFrom(location);
            File loc = new File(location).getParentFile();
            Matcher urlMatcher = url.matcher(stylesheet);
            stylesheet = urlMatcher.replaceAll(match -> {
                String s = "url(\"" + File.relative(loc, match.group(1)) + "\")";
                return s.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
            });
            loc.close();
            return String.format("<style type=\"text/css\">%n%s%n</style>", stylesheet);
        });
        replaceItAll(js, "<script type=\"text/javascript\">%n%s%n</script>", s -> File.readFrom(file.getAbsolutePath() + "\\" + s));
    }

    public void replaceItAll(Pattern p, String formatter, Function<String, String> function) {
        Matcher m = p.matcher(html);
        html = m.replaceAll(matchResult -> String.format(formatter, function.apply(matchResult.group(1))));
    }

    public HTMLManager substitute(String original, String newer) {
        if (variables.contains(original)) {
            map.put(original, newer);
        }
        return this;
    }

    @Override
    public String toString() {
        str text = new str(html);
        map.forEach((key, value) -> {
            text.replaceAll("\\$" + key, value);
        });
        return text.toString();
    }
}
