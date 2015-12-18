package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.*;

public class Calculator {
    public static String getAnswer(final String[] input) {

        final String answer;

        try {
            final Expression expression = new Expression(input[0]);
            final boolean isVarExpected = expression.isVarExpected();
            final ExpressionTree tree;


            switch (input.length) {
                case 1:
                    if (isVarExpected) {
                        throw new VariableValueExpectationException();
                    } else {
                        tree = new ExpressionTree(expression.getExpression());
                        answer = tree.calculate();
                    }
                    break;

                case 2:
                    final Lexeme varValue = new Lexeme(input[1]);
                if (!isVarExpected || !varValue.isReal()) {
                    throw new WrongSyntaxException();
                } else {
                    tree = new ExpressionTree(expression.getExpression());
                    answer = tree.calculate(varValue.getRealValue());
                }
            }
            return "default";

        } catch (UnhandledLexemeException e) {
            return "Unhandled lexeme.";
        } catch (WrongSyntaxException e) {
            return "Wrong syntax.";
        } catch (ParenthesesBalanceException e) {
            return "Parentheses are not balance.";
        } catch (TooManyVariablesException e) {
            return "Too many variables.";
        } catch (DivisionByZeroException e) {
            return "Division by zero.";
        } catch (VariableValueExpectationException e) {
            return "Variable value was expected.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static void main(final String[] args) {

        for (String s : args) {
            System.out.println(s);
        }

        final String answer;
        switch (args.length) {
            case 0:
                answer = "Not enough arguments.";
                break;
            case 1:
            case 2:
                answer = getAnswer(args);
                break;
            case 3:

            default:
                answer = "Too many arguments.";
                break;
        }

        System.out.println(answer);
    }
}
