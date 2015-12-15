package ru.spbstu.appmath.strogalshchikova;

import java.util.Iterator;

import static java.lang.Character.isDigit;

public class LexAnalyzer implements Iterator {
    private String expression;
    private int currentPosition;

    public LexAnalyzer(String expr) {
        expression = expr.trim();
        currentPosition = 0;
    }

    public boolean hasNext () {
        if (currentPosition >= expression.length())
            return false;
        return true;
    }

    public Object next () {
        if (expression.charAt(currentPosition) == '+' ||
                expression.charAt(currentPosition) == '-' ||
                expression.charAt(currentPosition) == '*' ||
                expression.charAt(currentPosition) == '/' ||
                expression.charAt(currentPosition) == '(' ||
                expression.charAt(currentPosition) == ')'
                ){
            currentPosition++;
            return expression.charAt(currentPosition);
        }
        if (expression.charAt(currentPosition) == 'x'){
            currentPosition++;
            return "x";
        }
        if (expression.charAt(currentPosition) == ' '){
            currentPosition++;
        }
        if (isDigit(expression.charAt(currentPosition))){
            String number = "";
            int n = 0;
            while(isDigit(expression.charAt(currentPosition)) || expression.charAt(currentPosition) == '.'){
                n++;
            }
            number.copyValueOf(expression.toCharArray(), currentPosition, currentPosition + n);
            currentPosition += n;
            return Double.parseDouble(number);
        }
        return null;
    }

    public void remove(){}

    public int getCurrentPosition() { return currentPosition; }
}
