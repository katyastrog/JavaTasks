package ru.spbstu.appmath.strogalshchikova;

/**
 * Created by ��������� on 28.11.2015.
 */

public class ExpressionTree {
    public static final int NODE_NUMBER = 1;
    public static final int NODE_VARIABLE = 2;
    public static final int NODE_OPERATOR = 3;
    // ���� root ������������ ���� ������ ������ �� ���� ��������� �����
    protected Object root;

    // ��� ������������ ��� �������� ����� ���� ��������� �����
    public ExpressionTree(double number) {
        root = new Double(number);
    }

    public ExpressionTree(String variable) {
        root = variable;
    }

    public ExpressionTree(Operator operator) {
        root = operator;
    }

    // ������� ���������� ���� ����
    public int getTreeClass() {
        return (root instanceof Double ? NODE_NUMBER :
                root instanceof String ? NODE_VARIABLE : NODE_OPERATOR);
    }
}
