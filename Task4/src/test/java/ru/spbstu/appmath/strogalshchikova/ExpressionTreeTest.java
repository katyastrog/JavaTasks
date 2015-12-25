package ru.spbstu.appmath.strogalshchikova;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ExpressionTreeTest {

    private final static Object[][] SIMPLIFIER_TEST_CORRECT_DATA = {
            {"(((x)))", "x"},
            {"3 + ((23 / 32) - 2)", "3 + 23 /32 -2"},
            {"32  + (12 + (5)/34)", "32+12+5/34"},
            {"1+((3-4)*(34/3))", "1 +(3-4)*(34/3)"},
            {"124+((1/23) -8)", "124 + 1/23 - 8"},
            {"((1 + 4) + 343) - 23", "1 + 4 + 343 -23"},
    };


    private final String input;
    private final String expected;

    public ExpressionTreeTest(final String input, final String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(SIMPLIFIER_TEST_CORRECT_DATA);
    }

    @Test
    public void testSimplify() throws Exception {
        final Expression simplifiedExp = new Expression(ExpressionUtilities.simplify(Expression.Parser.parse(input)));
        final Expression expectedExp = new Expression(expected);

        System.out.println(input + " == " + expectedExp);
        Assert.assertTrue(simplifiedExp.toString().equals(expectedExp.toString()));
    }

    @Test
    public void testCalculate() throws Exception {

    }
}