package ru.spbstu.appmath.strogalshchikova;

public class LexVariable extends Lexical {
    protected String variable;
    public LexVariable(String v) {
        variable = v;
    }
    public int getLexClass() {
        return Lexical.VARIABLE;
    }
    public String getVariable() {
        return variable;
    }
}
