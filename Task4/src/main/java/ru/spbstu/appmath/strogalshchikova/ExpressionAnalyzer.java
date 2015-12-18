package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionAnalyzer {
    public static List<Lexeme> analyze(final List<Lexeme> input) throws ParenthesesBalanceException, UnhandledLexemeException, WrongSyntaxException, TooManyVariablesException, WrongRangeException {
        Set<Lexeme> variablesSet = new HashSet<>();
        int parentheses = 0;
        boolean isVarExpected = false;
        final int last = input.size() - 1;

        // when only lexeme exists
        if (input.size() == 1 && input.get(0).isReal()) {
            return input.subList(0, last);
        } else if (input.size() == 1) {
            throw new WrongSyntaxException();
        }

        // check if variable is expected
        if (input.get(last).isRange() || (input.get(last).isReal() && !input.get(last - 1).isOperand())) {
            isVarExpected = true;
            // checks is range syntax correct
            analyzeRangeSyntax(input.get(last));
        }

        for (int curr = 0; curr < last; curr++) {
            final int next = curr + 1;

            switch (input.get(curr).getType()) {
                case LEFT_BRACKET:
                    if (!input.get(next).isReal() && !(isVarExpected && input.get(next).isVar()) && !input.get(next).isLeftBracket())
                        throw new WrongSyntaxException();
                    parentheses += 1;
                    break;
                case RIGHT_BRACKET:
                    if (!input.get(next).isOperand() && !input.get(next).isRightBracket() && !(isVarExpected && last == next))
                        throw new WrongSyntaxException();
                    parentheses -= 1;
                    break;
                case NUMBER:
                case MATH_CONSTANT:
                    if (!input.get(next).isOperand() && !input.get(next).isRightBracket() && !(isVarExpected && last == next))
                        throw new WrongSyntaxException();
                    break;
                case OPERAND:
                    if (!input.get(next).isReal() && !(isVarExpected && input.get(next).isVar()) && !input.get(next).isLeftBracket())
                        throw new WrongSyntaxException();
                    break;
                case VARIABLE:
                    if (!isVarExpected || (!input.get(next).isOperand() && !input.get(next).isRightBracket() && last != next))
                        throw new WrongSyntaxException();
                    if (!variablesSet.isEmpty() && !variablesSet.contains(input.get(curr)))
                        throw new TooManyVariablesException();
                    if (variablesSet.isEmpty())
                        variablesSet.add(input.get(curr));
                    break;
                case RANGE:
                    throw new WrongSyntaxException(); // range should be the last element within input
            }

            if (parentheses < 0)
                throw new ParenthesesBalanceException();

        }

        if (input.get(last).isLeftBracket())
            parentheses++;
        else if (input.get(last).isRightBracket())
            parentheses--;

        if (parentheses != 0)
            throw new ParenthesesBalanceException();

        return input.subList(0, last);
    }

    private static void analyzeRangeSyntax(final Lexeme range) throws WrongRangeException {
        Matcher matcher = Pattern.compile(Parser.RE_NUM).matcher(range.getValue());
        Double d[] = {null};
        int i = 0;
        while (matcher.find()) {
            d[i] = (Double.valueOf(range.getValue().substring(matcher.start(), matcher.end())));
            ++i;
        }

        if ((i == 2 && d[0] > d[1]) ||
                (i == 3 && Math.signum(d[1] - d[0]) != Math.signum(d[2])))
            throw new WrongRangeException();
    }
}