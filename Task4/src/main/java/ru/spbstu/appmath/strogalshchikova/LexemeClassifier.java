package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.UnhandledLexemeException;

public class LexemeClassifier {
    public static LexemeType classify(final String lexeme) throws UnhandledLexemeException {
        if (lexeme.length() == 0)
            throw new IllegalArgumentException();

        //final String brackets = "()";
        final String operands = "+-*/";

        if (lexeme.matches(Parser.RE_NUM)) { // numbers
            return LexemeType.NUMBER;
        } else if (operands.contains(lexeme) && lexeme.length() == 1) { // operands
            return LexemeType.OPERAND;
        } else if (lexeme.equals("(")) { // left bracket
            return LexemeType.LEFT_BRACKET;
        } else if (lexeme.equals(")")) { // right bracket
            return LexemeType.RIGHT_BRACKET;
        } else if (lexeme.matches(Parser.RE_LETTERS)) { // variables and constants
            return LexemeType.VARIABLE;
        } else if (lexeme.matches(Parser.RE_RANGE)) { // variable range
            return LexemeType.RANGE;
        } else {
            throw new UnhandledLexemeException();
        }
    }

}
