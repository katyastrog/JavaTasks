package ru.spbstu.appmath.strogalshchikova;

public abstract class Lexical {
    public static final int NUMBER = 1;
    public static final int VARIABLE = 2;
    public static final int OPERATOR = 3;
    public static final int LEFTPAR = 4;
    public static final int RIGHTPAR = 5;
    public abstract int getLexClass();
}

