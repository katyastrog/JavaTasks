package ru.spbstu.appmath.strogalshchikova.exceptions;

public class VariableValueExpectationException extends WrongSyntaxException {
    public VariableValueExpectationException() {
        super();
    }

    public VariableValueExpectationException(String message) {
        super(message);
    }
}
