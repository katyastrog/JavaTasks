package ru.spbstu.appmath.strogalshchikova;

public class WrongSyntaxException extends Exception {
    public WrongSyntaxException() { super();
    }

    public WrongSyntaxException(String message) {
        super(message);
    }
}
