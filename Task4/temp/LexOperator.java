package ru.spbstu.appmath.strogalshchikova;

public class LexOperator extends Lexical {
    protected char operator; // �������������� ���� �������� ��� ������

    public LexOperator(char oper) {
        this.operator = oper;
    }

    // ������� ����������� ������ �������
    public int getLexClass() {
        return (operator == '(' ? Lexical.LEFTPAR : operator == ')' ? Lexical.RIGHTPAR : Lexical.OPERATOR);
    }

    // ������� ���������� ���������� ��������
    public int getPriority() {
        return (operator == '-' || operator == '+' ? 1 : operator == '*' ? 2 : 0);
    }
// ������� �������

    public char getOperatorSign() {
        return operator;
    }
}
