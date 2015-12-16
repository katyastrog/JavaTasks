package ru.spbstu.appmath.strogalshchikova;

import java.util.ArrayList;
import java.util.List;

public class ExpressionAnalyzer {
    public static List<String> analyze(final List<String> input) throws ParenthesesBalanceException, UnhandledLexemeException {
        List<String> result = new ArrayList<>();
        LexemeType lexemeType;
        int parentheses = 0;

        for (int curr = 0; curr < input.size(); curr++) {
            if (parentheses < 0)
                throw new ParenthesesBalanceException();

            switch (LexemeClassifier.classify(input.get(curr))) {
                case NUMBER:

            }

            parentheses += handleIfParentheses(input.get(curr));

        }

        if (parentheses == 0)
            throw new ParenthesesBalanceException();

        return result;
    }

    private static byte handleIfParentheses(String s) {
        if (s.equals("("))
            return 1;
        else if (s.equals(")"))
            return -1;
        else
            return 0;
    }


}
