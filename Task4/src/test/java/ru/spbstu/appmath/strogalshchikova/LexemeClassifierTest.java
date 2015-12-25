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
            {"1.2324", Expression.LexemeType.NUMBER},
            {"0", Expression.LexemeType.NUMBER},
            {"12324", Expression.LexemeType.NUMBER},
            {"x", Expression.LexemeType.VARIABLE},
            {"X", Expression.LexemeType.VARIABLE},
            {"EREGD", Expression.LexemeType.VARIABLE},
            {"dfdsEFWEF", Expression.LexemeType.VARIABLE},
            {"alpha", Expression.LexemeType.VARIABLE},
            {"-", Expression.LexemeType.OPERAND},
            {"*", Expression.LexemeType.OPERAND},
            {"+", Expression.LexemeType.OPERAND},
            {"/", Expression.LexemeType.OPERAND},
            {"1:3:32", Expression.LexemeType.RANGE},
            {"1.0:3:-21", Expression.LexemeType.RANGE},
            {"1:3.10131", Expression.LexemeType.RANGE},
            {")", Expression.LexemeType.RIGHT_BRACKET},
            {"(", Expression.LexemeType.LEFT_BRACKET},
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
    private final Expression.LexemeType type;

    public LexemeClassifierTest(String input, Expression.LexemeType type) {
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
            Assert.assertEquals(new Expression.Lexeme(input), type);
    }

    @Test(expected = UnhandledLexemeException.class)
    public void testClassifyIncorrectly() throws Exception {
        if (type == null)
            Expression.Lexeme.classify(input);
        else
            throw new UnhandledLexemeException("fake");
    }

}