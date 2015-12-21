package ru.spbstu.appmath.strogalshchikova;


import ru.spbstu.appmath.strogalshchikova.exceptions.*;

import java.util.List;

public class Expression {

    private final List<Lexeme> expression;
    private final boolean isVarExpected;

    public Expression(final String input) throws UnhandledLexemeException, WrongSyntaxException, ParenthesesBalanceException, WrongRangeException, TooManyVariablesException {
        this.expression = Parser.parse(input);
        this.isVarExpected = Analyzer.analyze(expression);
    }

    public Expression(final List<Lexeme> expression) throws WrongSyntaxException, ParenthesesBalanceException, UnhandledLexemeException, TooManyVariablesException {
        this.expression = expression;
        this.isVarExpected = Analyzer.analyze(this.expression);
    }

    public List<Lexeme> getExpression() {
        return expression;
    }

    public boolean isVarExpected() {
        return isVarExpected;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Lexeme l : expression) {
            result.append(l.getValue());
        }

        return result.toString();
    }
}
