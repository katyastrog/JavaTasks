package ru.spbstu.appmath.strogalshchikova;

import java.util.List;

public class ExpressionUtilities {

    public static List<Lexeme> simplify(final List<Lexeme> inputExpression) {
        List<Lexeme> exp = inputExpression.subList(0, inputExpression.size());

        for (int i = 0; i < exp.size(); i++) { // iterates by the element before the last element
            int last = exp.size() - 1; // size can be changed
            if (exp.get(i).isLeftBracket()) {
                int balance = 0;

                for (int j = i + 1; j < exp.size(); j++) {
                    balance += exp.get(j).handleIfBracket(); // consider inner parentheses constructions
                    if (balance == -1) { // balance == -1 when j arrives appropriate right bracket
                        if (((i == 0 || exp.get(i - 1).isLeftBracket() || exp.get(i - 1).getValue().equals("+")) && // left elements allows to remove brackets
                                (j == last || exp.get(j + 1).isRightBracket() || exp.get(j + 1).getPriorityIfOperand() == 2)) || // right elements allows to remove brackets
                                (j - i == 2)) { // only lexeme is between brackets
                            exp.remove(j); // remove right bracket
                            exp.remove(i); // remove left bracket
                            i--; // i will be incremented at the next iteration
                        }
                        break;
                    }
                }
            }
        }

        return exp;
    }

    public static int findFracture(final List<Lexeme> exp) {
        int brackets = 0;
        int fracture = -1;

        for (int i = 0; i < exp.size(); i++) {
            brackets += exp.get(i).handleIfBracket();
            if (brackets == 0) {
                if (exp.get(i).isOperand()) {
                    if (exp.get(i).getPriorityIfOperand() == 2 && fracture == -1) { // if
                        fracture = i;
                    }
                    if (exp.get(i).getPriorityIfOperand() == 1) {
                        fracture = i;
                        break;
                    }
                }
            }
        }
        return fracture;
    }
}
