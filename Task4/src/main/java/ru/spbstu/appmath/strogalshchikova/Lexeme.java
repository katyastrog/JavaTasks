package ru.spbstu.appmath.strogalshchikova;


public class Lexeme {
    private final LexemeType type;
    private final String value;

    public Lexeme(final LexemeType type, final String value) {
        this.type = type;
        this.value = value;
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
}
