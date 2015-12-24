package ru.spbstu.appmath.strogalshchikova;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.spbstu.appmath.strogalshchikova.exceptions.UnhandledLexemeException;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LexemeClassifierTest {

    private final static Object[][] CORRECT_TEST_DATA = {
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
            {"(", LexemeType.LEFT_BRACKET},
            {"x:qe", null},
            {")(", null},
            {"1ed", null},
            {"P i", null},
            {"1)", null},
            {"#@$%", null},
            {"--", null},
            {"+1", null},
            {"/13", null}
    };

    private final String input;
    private final LexemeType type;

    public LexemeClassifierTest(String input, LexemeType type) {
        this.input = input;
        this.type = type;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(CORRECT_TEST_DATA);
    }

    @Test
    public void testClassifyCorrectly() throws Exception {
        if (type != null)
            Assert.assertEquals(LexemeClassifier.classify(input), type);
    }

    @Test(expected = UnhandledLexemeException.class)
    public void testClassifyIncorrectly() throws Exception {
        if (type == null)
            LexemeClassifier.classify(input);
        else
            throw new UnhandledLexemeException("fake");
    }

}