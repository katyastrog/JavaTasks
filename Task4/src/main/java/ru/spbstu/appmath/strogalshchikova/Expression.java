package ru.spbstu.appmath.strogalshchikova;


import ru.spbstu.appmath.strogalshchikova.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {

    private final List<Lexeme> lexemeList;
    private final Function root;
    private final boolean isVarExpected;
    private Double variableValue = null;

    public Expression(final String input) throws WrongSyntaxException {
        this.lexemeList = Parser.parse(input);
        analyze(lexemeList);
        root = buildNode(this.lexemeList);
        isVarExpected = isVarExpected();
    }

    public Expression(final List<Lexeme> lexemeList) throws WrongSyntaxException {
        this.lexemeList = lexemeList;
        analyze(this.lexemeList);
        root = buildNode(this.lexemeList);
        isVarExpected = isVarExpected();
    }

    public static void analyze(final List<Lexeme> input) throws WrongSyntaxException {
        Set<String> variablesSet = new HashSet<>(2);
        int parentheses = 0;
        final int last = input.size() - 1;

        // when only lexeme exists
        if (input.size() == 1) {
            if (input.get(0).isNumber())
                return;
            else if (input.get(0).isVar())
                return;
            else
                throw new WrongSyntaxException();
        }

        for (int curr = 0; curr < last; curr++) {
            final int next = curr + 1;

            switch (input.get(curr).getType()) {
                case LEFT_BRACKET:
                    if (!input.get(next).isNumber() && !input.get(next).isVar() && !input.get(next).isLeftBracket())
                        throw new WrongSyntaxException();
                    parentheses += 1;
                    break;
                case RIGHT_BRACKET:
                    if (!input.get(next).isOperand() && !input.get(next).isRightBracket())
                        throw new WrongSyntaxException();
                    parentheses -= 1;
                    break;
                case NUMBER:
                    if (!input.get(next).isOperand() && !input.get(next).isRightBracket())
                        throw new WrongSyntaxException();
                    break;
                case OPERAND:
                    if (!input.get(next).isNumber() && !input.get(next).isVar() && !input.get(next).isLeftBracket())
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
                default:
                    throw new UnhandledLexemeException();
            }

            if (parentheses < 0)
                throw new ParenthesesBalanceException();

        }

        if (parentheses + input.get(last).handleIfBracket() != 0)
            throw new ParenthesesBalanceException();

        if (!input.get(last).isNumber() && !input.get(last).isVar() && !input.get(last).isRightBracket())
            throw new WrongSyntaxException();

        if (input.get(last).isVar() && !variablesSet.isEmpty() && !variablesSet.contains(input.get(last).toString())) {
            throw new TooManyVariablesException();
        }
    }

    public boolean isVarExpected() {
        return root.isVarExpected();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Lexeme l : lexemeList) {
            result.append(l.getValue());
        }

        return result.toString();
    }

    public double calculate() throws DivisionByZeroException, VariableValueExpectationException {
        variableValue = null;
        if (isVarExpected)
            throw new VariableValueExpectationException();
        else
            return root.value();
    }

    public double calculate(final double realValue) throws DivisionByZeroException {
        variableValue = realValue;
        return root.value();
    }

    private Function buildNode(final List<Lexeme> expression) {
        final List<Lexeme> exp = ExpressionUtilities.simplify(expression);
        Function node = null;
        int fracture;

        if (exp.size() == 1 && (exp.get(0).isNumber() || exp.get(0).isVar())) {
            //System.out.print(exp.get(0).getValue() + " ");

            switch (exp.get(0).getType()) {
                case VARIABLE:
                    node = new Variable();
                    break;
                case NUMBER:
                    node = new Constant(exp.get(0).getRealValue());
                    break;
            }
        } else {
            fracture = ExpressionUtilities.findFracture(exp);

            //System.out.print(exp.get(fracture).getValue() + " ");

            switch (exp.get(fracture).getValue()) {
                case "+":
                    node = new SumOperation(exp.subList(0, fracture), exp.subList(fracture + 1, exp.size()));
                    break;
                case "-":
                    node = new SubOperation(exp.subList(0, fracture), exp.subList(fracture + 1, exp.size()));
                    break;
                case "*":
                    node = new MultOperation(exp.subList(0, fracture), exp.subList(fracture + 1, exp.size()));
                    break;
                case "/":
                    node = new DivOperation(exp.subList(0, fracture), exp.subList(fracture + 1, exp.size()));
                    break;
            }
        }

        return node;
    }

    public enum LexemeType {
        NUMBER, LEFT_BRACKET, RIGHT_BRACKET, OPERAND, VARIABLE, RANGE
    }

    private interface Function {
        double value();

        boolean isVarExpected();
    }

    public static class Lexeme {
        private final LexemeType type;
        private final String value;

        public Lexeme(final String value) throws UnhandledLexemeException {
            final String trimmedValue = value.trim();
            this.type = classify(trimmedValue);
            this.value = trimmedValue;
        }

        public static LexemeType classify(final String lexeme) throws UnhandledLexemeException {
            if (lexeme.length() == 0)
                throw new IllegalArgumentException();

            //final String brackets = "()";
            final String operands = "+-*/";

            if (lexeme.matches(Parser.RE_NUM)) { // numbers
                return LexemeType.NUMBER;
            } else if (operands.contains(lexeme) && lexeme.length() == 1) { // operands
                return LexemeType.OPERAND;
            } else if (lexeme.equals("(")) { // left bracket
                return LexemeType.LEFT_BRACKET;
            } else if (lexeme.equals(")")) { // right bracket
                return LexemeType.RIGHT_BRACKET;
            } else if (lexeme.matches(Parser.RE_LETTERS)) { // variables and constants
                return LexemeType.VARIABLE;
            } else if (lexeme.matches(Parser.RE_RANGE)) { // variable range
                return LexemeType.RANGE;
            } else {
                throw new UnhandledLexemeException();
            }
        }

        public Double getRealValue() {
            switch (type) {
                case NUMBER:
                    return Double.parseDouble(value);
                default:
                    return null;
            }

        }

        public LexemeType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        public int getLen() {
            return value.length();
        }

        @Override
        public String toString() {
            return getValue();
        }

        public boolean isNumber() {
            return (this.type == LexemeType.NUMBER);
        }

        public boolean isLeftBracket() {
            return (this.type == LexemeType.LEFT_BRACKET);
        }

        public boolean isRightBracket() {
            return (this.type == LexemeType.RIGHT_BRACKET);
        }

        public boolean isOperand() {
            return (this.type == LexemeType.OPERAND);
        }

        public boolean isRange() {
            return (this.type == LexemeType.RANGE);
        }

        public boolean isVar() {
            return (this.type == LexemeType.VARIABLE);
        }

        public int handleIfBracket() {
            if (isLeftBracket())
                return 1;
            else if (isRightBracket())
                return -1;
            else
                return 0;
        }

        public int getPriorityIfOperand() {
            switch (value) {
                case "-":
                case "+":
                    return 2;
                case "*":
                case "/":
                    return 1;
                default:
                    return 0;
            }
        }
    }

    public static class Parser {

        public static final String RE_UNUM = "(\\d+(\\.\\d+)?)"; // regex for unsigned numeric
        public static final String RE_NUM = "(\\-?" + RE_UNUM + ")"; // regex for signed numeric
        public static final String RE_RANGE = RE_NUM + ":?" + RE_NUM + "(:?" + RE_NUM + ")?"; // regex for range ('min:max[:step]')
        public static final String RE_LETTERS = "[a-zA-Z]+";
        public static final String RE_OPERANDS = "[()*/+\\-]";
        private static final String REGEX = RE_LETTERS + "|" + RE_OPERANDS + "|" + RE_UNUM;

        public static List<Lexeme> parse(final String input) throws UnhandledLexemeException {
            List<Lexeme> parsedInput = new ArrayList<>();
            final String trimmedInput = input.trim();

            if (trimmedInput.length() == 0)
                return null;

            final Matcher matcher = Pattern.compile(REGEX).matcher(trimmedInput);

            while (matcher.find()) {
                final String s = trimmedInput.substring(matcher.start(), matcher.end());
                parsedInput.add(new Lexeme(s));
            }

            int len = 0;

            for (Lexeme l : parsedInput) {
                len += l.getLen();
            }

            if (len != input.replaceAll("[\\s]*", "").length())
                throw new UnhandledLexemeException();

            return parsedInput;
        }
    }

    // division
    protected class DivOperation implements Function {
        private final Function lChild;
        private final Function rChild;

        public DivOperation(final List<Lexeme> left, final List<Lexeme> right) {
            lChild = buildNode(left);
            rChild = buildNode(right);
        }

        @Override
        public double value() {
            final double dividend = lChild.value();
            final double divisor = rChild.value();
            //System.out.println(dividend + " / " + divisor + " = " + (dividend / divisor));


            if (Double.compare(divisor, 0.0) == 0) {
                throw new DivisionByZeroException();
            } else {
                return dividend / divisor;
            }
        }

        @Override
        public boolean isVarExpected() {
            return (lChild.isVarExpected() || rChild.isVarExpected());
        }
    }

    // multiplication
    protected class MultOperation implements Function {
        private final Function lChild;
        private final Function rChild;

        public MultOperation(final List<Lexeme> left, final List<Lexeme> right) {
            lChild = buildNode(left);
            rChild = buildNode(right);
        }

        @Override
        public double value() {
            final double m1 = lChild.value();
            final double m2 = rChild.value();
            //System.out.println(m1 + " * " + m2 + " = " + (m1 * m2));

            return m1 * m2;
        }

        @Override
        public boolean isVarExpected() {
            return (lChild.isVarExpected() || rChild.isVarExpected());
        }
    }

    // subtraction
    protected class SubOperation implements Function {

        private final Function lChild;
        private final Function rChild;

        public SubOperation(final List<Lexeme> left, final List<Lexeme> right) {
            lChild = buildNode(left);
            rChild = buildNode(right);
        }

        @Override
        public double value() {
            final double minuend = lChild.value();
            final double subtrahend = rChild.value();

            //System.out.println(minuend + " - " + subtrahend + " = " + (minuend - subtrahend));

            return minuend - subtrahend;
        }

        @Override
        public boolean isVarExpected() {
            return (lChild.isVarExpected() || rChild.isVarExpected());
        }
    }

    protected class SumOperation implements Function {
        private final Function lChild;
        private final Function rChild;

        public SumOperation(final List<Lexeme> left, final List<Lexeme> right) {
            lChild = buildNode(left);
            rChild = buildNode(right);
        }

        @Override
        public double value() {
            final double s1 = lChild.value();
            final double s2 = rChild.value();
            //System.out.println(s1 + " + " + s2 + " = " + (s1 + s2));

            return s1 + s2;
        }

        @Override
        public boolean isVarExpected() {
            return (lChild.isVarExpected() || rChild.isVarExpected());
        }
    }

    protected class Variable implements Function {
        @Override
        public double value() {
            return variableValue;
        }

        @Override
        public boolean isVarExpected() {
            return true;
        }
    }

    protected class Constant implements Function {
        private final double value;

        public Constant(final double realValue) {
            this.value = realValue;
        }

        @Override
        public double value() {
            return this.value;
        }

        @Override
        public boolean isVarExpected() {
            return false;
        }
    }
}
