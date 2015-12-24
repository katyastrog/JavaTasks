package ru.spbstu.appmath.strogalshchikova;


import ru.spbstu.appmath.strogalshchikova.exceptions.DivisionByZeroException;
import ru.spbstu.appmath.strogalshchikova.exceptions.VariableValueExpectationException;

import java.util.List;

public class ExpressionTree {

    private final Function root;
    protected Double variableValue = null;

    public ExpressionTree(final List<Lexeme> expression) throws Exception {
        root = buildNode(expression);
    }

    public double calculate() throws DivisionByZeroException, VariableValueExpectationException {
        variableValue = null;
        return root.value();
    }

    public double calculate(final double realValue) throws DivisionByZeroException, VariableValueExpectationException {
        variableValue = realValue;
        return root.value();
    }

    private Function buildNode(final List<Lexeme> expression) throws Exception {
        final List<Lexeme> exp = ExpressionUtilities.simplify(expression);
        Function node = null;
        int fracture;

        if (exp.size() == 1 && exp.get(0).isReal()) {
            switch (exp.get(0).getType()) {
                case VARIABLE:
                    node = new Variable();
                    break;
                case NUMBER:
                case MATH_CONSTANT:
                    node = new Constant(exp.get(0).getRealValue());
                    break;
            }
        } else {
            fracture = ExpressionUtilities.findFracture(exp);

            switch (exp.get(fracture).getValue()) {
                case "+":
                    node = new SumOperation(exp.subList(0, fracture), exp.subList(fracture + 1, exp.size() - 1));
                    break;
                case "-":
                    node = new SubOperation(exp.subList(0, fracture), exp.subList(fracture + 1, exp.size() - 1));
                    break;
                case "*":
                    node = new MultOperation(exp.subList(0, fracture), exp.subList(fracture + 1, exp.size() - 1));
                    break;
                case "/":
                    node = new DivOperation(exp.subList(0, fracture), exp.subList(fracture + 1, exp.size() - 1));
                    break;
                default:
                    throw new Exception("Something goes wrong!");
            }
        }

        return node;
    }

    private interface Function {
        double value() throws VariableValueExpectationException;
    }

    // division
    protected class DivOperation implements Function {
        private final Function lChild;
        private final Function rChild;

        public DivOperation(final List<Lexeme> left, final List<Lexeme> right) throws Exception {
            lChild = buildNode(left);
            rChild = buildNode(right);
        }

        @Override
        public double value() throws VariableValueExpectationException {
            final double divisor = rChild.value();

            if (Double.compare(divisor, 0.0) == 0) {
                throw new DivisionByZeroException();
            } else {
                return lChild.value() / divisor;
            }
        }
    }

    // multiplication
    protected class MultOperation implements Function {
        private final Function lChild;
        private final Function rChild;

        public MultOperation(final List<Lexeme> left, final List<Lexeme> right) throws Exception {
            lChild = buildNode(left);
            rChild = buildNode(right);
        }

        @Override
        public double value() throws VariableValueExpectationException {
            return lChild.value() * rChild.value();
        }
    }

    // subtraction
    protected class SubOperation implements Function {

        private final Function lChild;
        private final Function rChild;

        public SubOperation(final List<Lexeme> left, final List<Lexeme> right) throws Exception {
            lChild = buildNode(left);
            rChild = buildNode(right);
        }

        @Override
        public double value() throws VariableValueExpectationException {
            return lChild.value() - rChild.value();
        }
    }

    protected class SumOperation implements Function {
        private final Function lChild;
        private final Function rChild;

        public SumOperation(final List<Lexeme> left, final List<Lexeme> right) throws Exception {
            lChild = buildNode(left);
            rChild = buildNode(right);
        }

        @Override
        public double value() throws VariableValueExpectationException {
            return lChild.value() + rChild.value();
        }
    }

    protected class Variable implements Function {
        @Override
        public double value() throws VariableValueExpectationException {
            if (variableValue == null)
                throw new VariableValueExpectationException();
            else
                return variableValue;
        }
    }

    protected class Constant implements Function {
        private final double value;

        public Constant(final double realValue) {
            this.value = realValue;
        }

        @Override
        public double value() {
            return this.value;
        }
    }
}
