package ru.spbstu.appmath.strogalshchikova;

public class LexOperator extends Lexical {
    protected char operator; // представляемый знак операции или скобка

    public LexOperator(char oper) {
        this.operator = oper;
    }

    // Функция определения класса лексемы
    public int getLexClass() {
        return (operator == '(' ? Lexical.LEFTPAR : operator == ')' ? Lexical.RIGHTPAR : Lexical.OPERATOR);
    }

    // Функция вычисления приоритета операции
    public int getPriority() {
        return (operator == '-' || operator == '+' ? 1 : operator == '*' ? 2 : 0);
    }
// Функция доступа

    public char getOperatorSign() {
        return operator;
    }
}
