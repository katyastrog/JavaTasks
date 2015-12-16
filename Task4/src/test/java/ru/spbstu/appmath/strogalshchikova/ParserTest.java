package ru.spbstu.appmath.strogalshchikova;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

public class ParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static String[] correctInput = {
            " ",
            "xregergerg ergerg egr",
            "x",
            "x 12:3:-2",
            "(5 + x) / x",
            "x - 4.32 + 5 * (-1.9 + x)",
            "x + (x + 10.324)*x/((x) - 2.5)",
            "-5.0",
            "7.32 * 5 - (x * 10)/(x -10) ",
            "(((((((x * 5)- 7) / x) / 10) - 7) * x) + x",
            "x + (x + 10.324)*x/((x) - 7.0)"
    };
    private static String[] incorrectInput = {
            "x1",
            "x 12:3:",
            "(!5 + x) / x",
            "x - 4,32 + 5 * (-1.9 + x)",
            ":4:x + (x + 10.324)*x/((x) - 2.5)",
            "-5..0",
            "7.32 * 5 - (:)(x * 10)/(x -10) ",
            "((((yt(((x * 5)- 7) / x) / 10) - 7) * x) + x",
            "@#$%$%x + (x + 10.324)*x/((x) - 7.0)"
    };


    @Test
    public void testParse() throws Exception {
        for (String data : correctInput) {
            List<String> parsedInput = Parser.parse(data);

            Assert.assertNotNull(parsedInput);


            for (String s : parsedInput) {
                System.out.print("'" + s + "' ");
            }
            System.out.println();
        }

        for (String data : incorrectInput) {
            Parser.parse(data);
            exception.expect(UnhandledLexemeException.class);
        }
    }
}