package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.*;

public class Calculator {
    public static String getAnswer(final String[] input) {

        try {
            final Expression expression = new Expression(input[0]);
            final boolean isVarExpected = expression.isVarExpected();
            final ExpressionTree tree;


            switch (input.length) {
                case 1:
                    if (isVarExpected) {
                        throw new VariableValueExpectationException();
                    } else {
                        tree = new ExpressionTree(expression);
                        return String.valueOf(tree.calculate());
                    }
                case 2:
                    //System.out.println("calc switch case 2");
                    final Lexeme varValue = new Lexeme(input[1]);
                if (!isVarExpected || !varValue.isNumber()) {
                    throw new WrongSyntaxException();
                } else {
                    tree = new ExpressionTree(expression);
                    return String.valueOf(tree.calculate(varValue.getRealValue()));
                }
            }
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

        return null;
    }

    public static String response(final String[] request) {
        final String answer;
        switch (request.length) {
            case 0:
                answer = "Not enough arguments.";
                break;
            case 1:
            case 2:
                System.out.println("Calculating");
                answer = getAnswer(request);
                break;
            case 3:

            default:
                answer = "Too many arguments.";
                break;
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(response(args));
    }


}

