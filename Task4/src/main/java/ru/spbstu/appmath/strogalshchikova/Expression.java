package ru.spbstu.appmath.strogalshchikova;


import ru.spbstu.appmath.strogalshchikova.exceptions.*;

import java.util.List;

public class Expression {

    private final List<Lexeme> expression;
    private final boolean isVarExpected;

    public Expression(final String input) throws UnhandledLexemeException, WrongSyntaxException, ParenthesesBalanceException, WrongRangeException, TooManyVariablesException {
        expression = Parser.parse(input);
        isVarExpected = Analyzer.analyze(expression);
    }

    public List<Lexeme> getExpression() {
        return expression;
    }

    public boolean isVarExpected() {
        return isVarExpected;
    }
}
