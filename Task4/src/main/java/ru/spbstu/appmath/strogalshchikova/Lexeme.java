package ru.spbstu.appmath.strogalshchikova;


import ru.spbstu.appmath.strogalshchikova.exceptions.UnhandledLexemeException;

public class Lexeme {
    private final LexemeType type;
    private final String value;

    public Lexeme(final LexemeType type, final String value) {
        this.type = type;
        this.value = value;
    }

    public Lexeme(final String value) throws UnhandledLexemeException {
        this.type = LexemeClassifier.classify(value);
        this.value = value;
    }

    public double getRealValue() throws Exception {
        switch (type) {
            case NUMBER:
                return Double.parseDouble(value);
            default:
                throw new Exception("unexpected error");
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
