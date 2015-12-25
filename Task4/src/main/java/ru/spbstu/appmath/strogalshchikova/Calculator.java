package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.*;

public class Calculator {
    public static double calculate(final String[] input) throws WrongSyntaxException {
        final Expression expression = new Expression(input[0]);
        final boolean isVarExpected = expression.isVarExpected();

        switch (input.length) {
            case 1:
                if (isVarExpected) {
                    throw new VariableValueExpectationException();
                } else {
                    return expression.calculate();
                }
            case 2:
                final Expression.Lexeme varValue = new Expression.Lexeme(input[1]);

                if (!isVarExpected || !varValue.isNumber()) {
                    throw new WrongSyntaxException();
                } else {
                    return expression.calculate(varValue.getRealValue());
                }
            default:
                throw new WrongSyntaxException();
        }
    }

    public static String getAnswer(final String[] input) {
        if (input == null || input[0] == null)
            return "Input is empty.";
        try {
            return String.valueOf(calculate(input));
        } catch (UnhandledLexemeException e) {
            return "Unhandled lexeme.";
        } catch (ParenthesesBalanceException e) {
            return "Parentheses are not balanced.";
        } catch (TooManyVariablesException e) {
            return "Too many variables.";
        } catch (DivisionByZeroException e) {
            return "Division by zero.";
        } catch (VariableValueExpectationException e) {
            return "Variable value was expected.";
        } catch (WrongSyntaxException e) {
            return "Wrong syntax.";
        }
    }

    public static String response(final String[] request) {
        final String answer;
        switch (request.length) {
            case 0:
                answer = "Not enough arguments.";
                break;
            case 1:
            case 2:
                answer = getAnswer(request);
                break;
            case 3:

            default:
                answer = "Too many arguments.";
                break;
        }

        return answer;
    }

    /*public static void main(String[] args) {
        System.out.println(response(args));
    }
*/

}

