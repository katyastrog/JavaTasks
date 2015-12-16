package ru.spbstu.appmath.strogalshchikova;

public class LexemeClassifier {
    public static LexemeType classify(String lexeme) throws UnhandledLexemeException {
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
            if (isConstant(lexeme))
                return LexemeType.CONSTANT;
            else
                return LexemeType.VARIABLE;
        } else if (lexeme.matches(Parser.RE_RANGE)) { // variable range
            return LexemeType.RANGE;
        } else {
            throw new UnhandledLexemeException();
        }
    }

    private static boolean isConstant(final String lexeme) {
        for (Constant constant : Constant.values()) {
            if (constant.name().equals(lexeme)) {
                return true;
            }
        }

        return false;
    }
}
