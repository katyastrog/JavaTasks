package ru.spbstu.appmath.strogalshchikova;


public class MoreThenOneVariableException extends Exception{
    public MoreThenOneVariableException() { super();
    }

    public MoreThenOneVariableException(String message) {
        super(message);
    }
}
