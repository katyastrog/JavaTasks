package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {

    /*
    * analyzes expression
    * returns true if variable expected
    */
    public static boolean analyze(final List<Lexeme> input) throws ParenthesesBalanceException, UnhandledLexemeException, WrongSyntaxException, TooManyVariablesException {
        Set<String> variablesSet = new HashSet<>();
        int parentheses = 0;
        final int last = input.size() - 1;

        // when only lexeme exists
        if (input.size() == 1 && input.get(0).isReal())
            return false;
        else if (input.size() == 1 && input.get(0).isVar())
            return true;
        else if (input.size() == 1)
            throw new WrongSyntaxException();

        for (int curr = 0; curr < last; curr++) {
            final int next = curr + 1;

            switch (input.get(curr).getType()) {
                case LEFT_BRACKET:
                    if (!input.get(next).isReal() && !input.get(next).isVar() && !input.get(next).isLeftBracket())
                        throw new WrongSyntaxException();
                    parentheses += 1;
                    break;
                case RIGHT_BRACKET:
                    if (!input.get(next).isOperand() && !input.get(next).isRightBracket())
                        throw new WrongSyntaxException();
                    parentheses -= 1;
                    break;
                case NUMBER:
                case MATH_CONSTANT:
                    if (!input.get(next).isOperand() && !input.get(next).isRightBracket())
                        throw new WrongSyntaxException();
                    break;
                case OPERAND:
                    if (!input.get(next).isReal() && !input.get(next).isVar() && !input.get(next).isLeftBracket())
                        throw new WrongSyntaxException();
                    break;
                case VARIABLE:
                    if (!input.get(next).isOperand() && !input.get(next).isRightBracket() && last != next)
                        throw new WrongSyntaxException();
                    if (!variablesSet.isEmpty() && !variablesSet.contains(input.get(curr).getValue()))
                        throw new TooManyVariablesException();
                    if (variablesSet.isEmpty())
                        variablesSet.add(input.get(curr).getValue());
                    break;
                case RANGE:
                    throw new WrongSyntaxException(); // range shouldn't be within input
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

        return !variablesSet.isEmpty();
    }

    private static void analyzeRange(final Lexeme range) throws WrongRangeException {
        final Matcher matcher = Pattern.compile(Parser.RE_NUM).matcher(range.getValue());
        Double d[] = {null, null, null};
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