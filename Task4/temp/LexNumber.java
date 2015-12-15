package ru.spbstu.appmath.strogalshchikova;

public class LexNumber extends Lexical {
    protected double number; // представляемое число

    // Конструктор лексемы
    public LexNumber(double num) {
        number = num;
    }

    // Функция определения класса лексемы
    public int getLexClass() {
        return Lexical.NUMBER;
    }

    // Фун1щия доступа
    public double getNumber() {
        return number;
    }
}
