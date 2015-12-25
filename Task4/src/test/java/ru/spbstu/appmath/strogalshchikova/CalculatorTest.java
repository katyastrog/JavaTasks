package ru.spbstu.appmath.strogalshchikova;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.spbstu.appmath.strogalshchikova.exceptions.WrongSyntaxException;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CalculatorTest {
    private final static Object[][] CORRECT_TEST_DATA = {
            {"3 + ((23 / 32) - 2)", null, String.valueOf(new Double(3.0 + ((23.0 / 32.0) - 2.0)))},
            {"32  + (12 + (5)/34)", null, String.valueOf(new Double(32.0 + (12.0 + (5.0) / 34.0)))},
            {"00..65 + 23", null, "Wrong syntax."},
            {"1+((3-4)*(34/1))", null, String.valueOf(new Double(1+((3-4)*(34.0/1.0))))},
            {"124+((0.5) + x -8)", "0", String.valueOf(new Double(124+((0.5) + 0 -8)))},
            {"(1 + 4)*const + 2*0.0", "1", String.valueOf(new Double((1 + 4)*1.0 + 2*0.0))},
            {"((1 + 4) + 343) - 23 / (3 - 3)", null, "Division by zero."},
    };


    private final String input;
    private final String expected;
    private final String varValue;

    public CalculatorTest(String input, String varValue, String expected) {
        this.input = input;
        this.varValue = varValue;
        this.expected = expected;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(CORRECT_TEST_DATA);
    }

    @Test
    public void testCorrectAnswer() throws Exception {

        if (varValue == null)
            Assert.assertEquals(Calculator.response(new String[]{input}), expected);
        else
            Assert.assertEquals(Calculator.response(new String[]{input, varValue}), expected);
    }
}