package ru.spbstu.appmath.strogalshchikova;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParserTest {

    private static String[] correctInput = {
            " ",
            "xregergerg ergerg egr",
            "x",
            "x / 124.4 - 23",
            "(5 + x) / x",
            "x - 4.32 + 5 * (-1.9 + x)",
            "x + (x + 10.324)*x/((x) - 2.5)",
            "-5.0",
            "7.32 * 5 - (x * 10)/(x -10) ",
            "(((((((x * 5)- 7) / x) / 10) - 7) * x) + x",
            "x + (x + 10.324)*x/((x) - 7.0)"
    };
    private static String[] incorrectInput = {
            "x 12:3:",
            "(!5 + x) / x",
            "x - 4,32 + 5 * (-1.9 + x)",
            ":4:x + (x + 10.324)*x/((x) - 2.5)",
            "-5..0",
            "7.32 * 5 - (:)(x * 10)/(x -10) ",
            //"((((yt(((x * 5)- 7) / x) / 10) - 7) * x) + x",
            "@#$%$%x + (x + 10.324)*x/((x) - 7.0)"
    };

    @Test
    public void testParse() throws Exception {
        for (String data : correctInput) {
            List<Expression.Lexeme> parsedInput = Expression.Parser.parse(data);
            Assert.assertNotNull(parsedInput);

            int parsedInputLen = 0;

            for (Expression.Lexeme lexeme : parsedInput) {
                parsedInputLen += lexeme.getLen();
            }

            Assert.assertEquals(data.replaceAll("\\s", "").length(), parsedInputLen);

        }

        for (String data : incorrectInput) {
            List<Expression.Lexeme> lexemes = Expression.Parser.parse(data);
            //System.out.println(data);
            int parsedInputLen = 0;

            for (Expression.Lexeme l : lexemes)
                parsedInputLen += l.getLen();

            Assert.assertNotEquals(parsedInputLen, data.replaceAll("\\s", "").length());
        }
    }
}