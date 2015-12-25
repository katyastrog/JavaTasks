package ru.spbstu.appmath.strogalshchikova.exceptions;

public class UnhandledLexemeException extends WrongSyntaxException {
    public UnhandledLexemeException() {
        super();
    }

    public UnhandledLexemeException(String message) {
        super(message);
    }
}
