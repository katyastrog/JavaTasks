package ru.spbstu.appmath.strogalshchikova;


import ru.spbstu.appmath.strogalshchikova.exceptions.UnhandledLexemeException;

public class Lexeme {
    private final LexemeType type;
    private final String value;

    public Lexeme(final String value) throws UnhandledLexemeException {
        this.type = classify(value);
        this.value = value;
    }

    private static LexemeType classify(final String lexeme) throws UnhandledLexemeException {
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
