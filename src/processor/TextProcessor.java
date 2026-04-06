package processor;

public class TextProcessor {
    public static String process(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}