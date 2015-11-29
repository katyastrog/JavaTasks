/**
 * Created by ��������� on 02.11.2015.
 */
package ru.spbstu.appmath.strogalshchikova;

public class Operator {
    char operationSign; // ���� ��������
    ExpressionTree operandFirst; // ������ �������
    ExpressionTree operandSecond; // ������ �������

    public Operator(char sign, ExpressionTree op1, ExpressionTree op2) {
        operationSign = sign;
        operandFirst = op1;
        operandSecond = op2;
    }

    public char getOperationSign() {
        return operationSign;
    }
    public ExpressionTree getOperandFirst() {
        return operandFirst;
    }
    public ExpressionTree getOperandSecond() {
        return operandSecond;
    }
}
