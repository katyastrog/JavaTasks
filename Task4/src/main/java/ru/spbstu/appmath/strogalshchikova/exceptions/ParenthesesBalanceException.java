package ru.spbstu.appmath.strogalshchikova.exceptions;

public class ParenthesesBalanceException extends WrongSyntaxException {
    public ParenthesesBalanceException() {
        super();
    }

    public ParenthesesBalanceException(String message) {
        super(message);
    }
}
