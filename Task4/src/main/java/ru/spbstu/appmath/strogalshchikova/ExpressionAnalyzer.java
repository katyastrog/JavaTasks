package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionAnalyzer {
    public static List<String> analyze(final List<String> input) throws ParenthesesBalanceException, UnhandledLexemeException, WrongSyntaxException, TooManyVariablesException, WrongRangeException {
        List<String> result = new ArrayList<>();
        Set<String> variables = new HashSet<>();
        LexemeType lexemeType;
        int parentheses = 0;
        boolean isVarExpected = false;


        // when only lexeme exists
        if (input.size() == 1) {
            if (isNumber(input.get(0)) || isConstant(input.get(0))) {
                result.add(input.get(0));
                return result;
            } else {
                throw new WrongSyntaxException();
            }
        }

        // check if variable is expected
        int last = input.size() - 1;
        if (input.size() > 1 && (isRange(input.get(last)) ||
                ((isNumber(input.get(last)) || isConstant(input.get(last))) && !isOperand(input.get(last - 1))))) {
            isVarExpected = true;
            if (isRange(input.get(last))) {
                analyzeRange(input.get(last));
            }
        }

        for (int curr = 0; curr < input.size() - 1; curr++) {
            int next = curr + 1;
            if (parentheses < 0)
                throw new ParenthesesBalanceException();
            if (variables.size() > 1)
                throw new TooManyVariablesException();

            switch (LexemeClassifier.classify(input.get(curr))) {
                case LEFT_BRACKET:
                    if (isNumber(input.get(next)) || isConstant(input.get(next)) || (isVar(input.get(next)) && isVarExpected) || input.get(next).equals("("))
                        result.add(input.get(curr));
                    else
                        throw new WrongSyntaxException();
                    parentheses += 1;
                    break;
                case RIGHT_BRACKET:
                    if (isOperand(input.get(next)) || input.get(next).equals(")") || (isVarExpected && last == next))
                        result.add(input.get(curr));
                    else
                        throw new WrongSyntaxException();
                    parentheses -= 1;
                    break;
                case NUMBER:
                case CONSTANT:
                    if (isOperand(input.get(next)) || input.get(next).equals(")") || (isVarExpected && last == next))
                        result.add(input.get(curr));
                    else
                        throw new WrongSyntaxException();
                    break;
                case OPERAND:
                    if (isNumber(input.get(next)) || isConstant(input.get(next)) || isVar(input.get(next)) || input.get(next).equals("("))
                        result.add(input.get(curr));
                    else
                        throw new WrongSyntaxException();
                    break;
                case VARIABLE:
                    if (!isVarExpected || !(isOperand(input.get(next)) || input.get(next).equals(")") || last == next)) {
                        throw new WrongSyntaxException();
                    } else if (!variables.isEmpty() && !variables.contains(input.get(curr))) {
                        throw new TooManyVariablesException();
                    } else {
                        result.add(input.get(curr));
                        variables.add(input.get(curr));
                    }
                    break;
                case RANGE:
                    throw new WrongSyntaxException(); // range should be the last element within input
            }
        }

        result.add(input.get(last));

        if (input.get(last).equals("("))
            parentheses++;
        else if (input.get(last).equals(")"))
            parentheses--;


        if (parentheses != 0)
            throw new ParenthesesBalanceException();
        if (variables.size() != 1 && isVarExpected)
            throw new WrongSyntaxException();

        return result;
    }

    private static void analyzeRange(final String range) throws WrongRangeException {
        Matcher matcher = Pattern.compile(Parser.RE_NUM).matcher(range);
        ArrayList<Double> d = new ArrayList<>();

        while (matcher.find()) {
            d.add(Double.valueOf(range.substring(matcher.start(), matcher.end())));
        }

        if (d.size() == 2 && d.get(0) > d.get(1))
            throw new WrongRangeException();
        if (d.size() == 3) {
            if (Math.signum(d.get(1) - d.get(0)) != Math.signum(d.get(2)))
                throw new WrongRangeException();
        }
    }

    private static boolean isNumber(final String lexeme) throws UnhandledLexemeException {
        return (LexemeClassifier.classify(lexeme) == LexemeType.NUMBER);
    }

    private static boolean isConstant(final String lexeme) throws UnhandledLexemeException {
        return (LexemeClassifier.classify(lexeme) == LexemeType.CONSTANT);
    }

    private static boolean isOperand(final String lexeme) throws UnhandledLexemeException {
        return (LexemeClassifier.classify(lexeme) == LexemeType.OPERAND);
    }

    private static boolean isRange(final String lexeme) throws UnhandledLexemeException {
        return (LexemeClassifier.classify(lexeme) == LexemeType.RANGE);
    }

    private static boolean isVar(final String lexeme) throws UnhandledLexemeException {
        return (LexemeClassifier.classify(lexeme) == LexemeType.VARIABLE);
    }

}