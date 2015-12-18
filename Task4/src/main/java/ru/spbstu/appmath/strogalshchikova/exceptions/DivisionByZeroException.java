package ru.spbstu.appmath.strogalshchikova.exceptions;

public class DivisionByZeroException extends ArithmeticException {
    public DivisionByZeroException() {
        super();
    }

    public DivisionByZeroException(String s) {
        super(s);
    }
}
