package ru.spbstu.appmath.strogalshchikova;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LexemeClassifierTest {

    private final static Object[][] CORRECT_TEST_DATA = {
            {"E", LexemeType.MATH_CONSTANT},
            {"PI", LexemeType.MATH_CONSTANT},
            {"1.2324", LexemeType.NUMBER},
            {"0", LexemeType.NUMBER},
            {"12324", LexemeType.NUMBER},
            {"x", LexemeType.VARIABLE},
            {"X", LexemeType.VARIABLE},
            {"EREGD", LexemeType.VARIABLE},
            {"dfdsEFWEF", LexemeType.VARIABLE},
            {"alpha", LexemeType.VARIABLE},
            {"-", LexemeType.OPERAND},
            {"*", LexemeType.OPERAND},
            {"+", LexemeType.OPERAND},
            {"/", LexemeType.OPERAND},
            {"1:3:32", LexemeType.RANGE},
            {"1.0:3:-21", LexemeType.RANGE},
            {"1:3.10131", LexemeType.RANGE},
            {")", LexemeType.RIGHT_BRACKET},
            {"(", LexemeType.LEFT_BRACKET}
    };
    private String input;
    private LexemeType type;
    public LexemeClassifierTest(String input, LexemeType type) {
        this.input = input;
        this.type = type;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(CORRECT_TEST_DATA);
    }

    @Test
    public void testClassify() throws Exception {
        Assert.assertEquals(LexemeClassifier.classify(input), type);
    }
}