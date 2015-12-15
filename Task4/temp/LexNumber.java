package ru.spbstu.appmath.strogalshchikova;

public class LexNumber extends Lexical {
    protected double number; // �������������� �����

    // ����������� �������
    public LexNumber(double num) {
        number = num;
    }

    // ������� ����������� ������ �������
    public int getLexClass() {
        return Lexical.NUMBER;
    }

    // ���1��� �������
    public double getNumber() {
        return number;
    }
}
