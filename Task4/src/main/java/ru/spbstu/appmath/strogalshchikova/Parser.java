package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.UnhandledLexemeException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static final String RE_UNUM = "(\\d+(\\.\\d+)?)"; // regex for unsigned numeric
    public static final String RE_NUM = "(\\-?" + RE_UNUM + ")"; // regex for signed numeric
    public static final String RE_RANGE = RE_NUM + ":+" + RE_NUM + "(:+" + RE_NUM + ")?"; // regex for range ('min:max[:step]')
    public static final String RE_LETTERS = "[a-zA-Z]+";
    public static final String RE_OPERANDS = "[()*/+\\-]";
    private static final String REGEX = RE_RANGE + "|" + RE_LETTERS + "|" + RE_OPERANDS + "|" + RE_UNUM;

    public static List<Lexeme> parse(final String[] arg) throws UnhandledLexemeException {
        String input = "";

        for (String s : arg) {
            input += s;
        }

        return parse(input);
    }

    public static List<Lexeme> parse(final String input) throws UnhandledLexemeException {
        List<Lexeme> parsedInput = new ArrayList<>();
        final String trimmedInput = input.trim();
        final Matcher matcher = Pattern.compile(REGEX).matcher(trimmedInput);

        while (matcher.find()) {
            final String s = trimmedInput.substring(matcher.start(), matcher.end());
            parsedInput.add(new Lexeme(LexemeClassifier.classify(s), s));
        }

        int totalLength = 0;
        for (Lexeme lexeme : parsedInput) {
            totalLength += lexeme.get();
        }

        if (totalLength != trimmedInput.replaceAll("\\s", "").length())
            throw new UnhandledLexemeException();

        return parsedInput;
    }
}