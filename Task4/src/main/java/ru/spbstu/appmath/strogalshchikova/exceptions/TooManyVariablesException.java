package ru.spbstu.appmath.strogalshchikova.exceptions;


public class TooManyVariablesException extends WrongSyntaxException {
    public TooManyVariablesException() {
        super();
    }

    public TooManyVariablesException(String message) {
        super(message);
    }
}
