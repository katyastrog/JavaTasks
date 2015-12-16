package ru.spbstu.appmath.strogalshchikova;


public class TooManyVariablesException extends Exception{
    public TooManyVariablesException() { super();
    }

    public TooManyVariablesException(String message) {
        super(message);
    }
}
