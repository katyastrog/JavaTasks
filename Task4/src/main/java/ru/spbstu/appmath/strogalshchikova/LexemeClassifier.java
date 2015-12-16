package ru.spbstu.appmath.strogalshchikova;

public class LexemeClassifier {
    public static LexemeType classify(String lexeme) throws UnhandledLexemeException {
        if (lexeme.length() == 0)
            throw new IllegalArgumentException();

        final String parentheses = "()";
        final String operands = "+-*/";

        if (lexeme.matches(Parser.RE_NUM)) { // numbers
            return LexemeType.NUMBER;
        } else if (operands.contains(lexeme) && lexeme.length() == 1) { // operands
            return LexemeType.OPERAND;
        } else if (parentheses.contains(lexeme) && lexeme.length() == 1) { // parentheses
            return LexemeType.PARENTHESES;
        } else if (lexeme.matches(Parser.RE_LETTERS)) { // variables and constants
            if (isConstant(lexeme))
                return LexemeType.CONSTANT;
            else
                return LexemeType.VARIABLE;
        } else  if (lexeme.matches(Parser.RE_RANGE)) { // variable range
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
