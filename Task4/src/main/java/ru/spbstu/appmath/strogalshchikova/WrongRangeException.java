package ru.spbstu.appmath.strogalshchikova;


public class WrongRangeException extends Exception{
    public WrongRangeException() { super();
    }

    public WrongRangeException(String message) {
        super(message);
    }
}
