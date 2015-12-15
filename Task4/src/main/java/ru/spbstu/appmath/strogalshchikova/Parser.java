package ru.spbstu.appmath.strogalshchikova;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final String RE_UNUM = "(\\d+(\\.\\d+)?)"; // regex for unsigned numeric
    private static final String RE_NUM = "(\\-?" + RE_UNUM + ")"; // regex for signed numeric
    private static final String RE_RANGE = RE_NUM + ":+" + RE_NUM + "(:+" + RE_NUM + ")?"; // regex for range ('min:max[:step]')
    private static final String RE_LITERALS = "[a-zA-Z]";
    private static final String RE_OPERANDS = "[()*/+\\-]";
    private static final String REGEX = RE_RANGE    + "|" +
                                        RE_LITERALS + "|" +
                                        RE_OPERANDS + "|" +
                                        RE_UNUM;

    public static List<String> parse(final String[] arg) {
        String input = "";

        for (String s : arg) {
            input += s;
        }

        return parse(input);
    }

    public static List<String> parse(final String input) {
        List<String> parsedInput = new ArrayList<>();
        String trimmedInput = input.trim();
        Matcher matcher = Pattern.compile(REGEX).matcher(trimmedInput);

        while (matcher.find()) {
            parsedInput.add(trimmedInput.substring(matcher.start(), matcher.end()));
        }
        //
        int totalLength = 0;
        for (String s : parsedInput) {
            totalLength += s.length();
        }

        if (totalLength != trimmedInput.replaceAll("\\s", "").length()) {
            System.out.println("Unhandled symbol");
        } else {
            for (String s : parsedInput) {
                System.out.print(s + ", ");
            }
            System.out.println();
        }
        return parsedInput;
    }
}