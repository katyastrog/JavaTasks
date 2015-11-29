package ru.spbstu.appmath.strogalshchikova;

import java.text.ParseException;

/**
 * Created by Екатерина on 29.11.2015.
 */
public class Parser {
    public static void doOperator (Stack operands, Stack operators) throws StackException {
        ExpressionTree operandFirst = (ExpressionTree)operands.pop();
        ExpressionTree operandSecond = (ExpressionTree)operands.pop();
        char operationSign = ((LexOperator)operators.pop()).getOperatorSign();
        operands.push(new ExpressionTree(new Operator(operationSign, operandFirst, operandSecond)));
    }

    public static void doOperators (Stack operands, Stack operators, int minPriority) throws StackException {
        while (!operators.isEmpty() && ((LexOperator)operators.top()).getPriority() >= minPriority){
            doOperator(operands, operators);
        }
    }

    public static ExpressionTree parse(String source) throws ParseException {
        final int WAIT_OPERAND = 1;
        final int WAIT_OPERATOR = 2;
        Stack operands = new ArrayStack();
        Stack operators = new ArrayStack();

        LexAnalyzer analyzer = new LexAnalyzer(source);

        int waitFlag = WAIT_OPERAND;
        try {
            while (analyzer.hasNext()) {
                Lexical lex = (Lexical) analyzer.next();
                if (lex == null) {
                    throw new ParseException("Неизвестная лексема", analyzer.getCurrentPosition());
                }
                if (waitFlag == WAIT_OPERAND) {
                    switch (lex.getLexClass()) {
                        case Lexical.NUMBER:
                            operands.push(new ExpressionTree(((LexNumber) lex).getNumber()));
                            waitFlag = WAIT_OPERATOR;
                            break;
                        case Lexical.VARIABLE:
                            operands.push(new ExpressionTree(((LexVariable) lex).getVariable()));
                            waitFlag = WAIT_OPERATOR;
                            break;
                        case Lexical.LEFTPAR:
                            operators.push(lex);
                            break;
                        default:
                            throw new ParseException("Ожидался операнд", analyzer.getCurrentPosition());
                    }
                } else {
                    switch (lex.getLexClass()) {
                        case Lexical.RIGHTPAR:
                            doOperators(operands, operators, 1);
                            operators.pop();
                            break;
                        case Lexical.OPERATOR:
                            doOperators(operands, operators, ((LexOperator) lex).getPriority());
                            operators.push(lex);
                            waitFlag = WAIT_OPERAND;
                            break;
                        default:
                            throw new ParseException("Ожидался знак операции", analyzer.getCurrentPosition());
                    }
                }
            }
            doOperators(operands, operators, 0);
            return (ExpressionTree) operands.pop();
        } catch (StackException ex) {
            throw new ParseException("Ошибка в скобочной структуре выражения", analyzer.getCurrentPosition());
        }
    }
}