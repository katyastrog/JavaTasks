package ru.spbstu.appmath.strogalshchikova;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

public class ExpressionAnalyzerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static String[] correctInput = {
            "x 12:20:2",
            "(5 + x) / x 12",
            "x - 4.32 + 5 * (1.9 + x) 12:45",
            "x + (x + 10.324)*x/((x) - 2.5) 12",
            "5.0",
            "7.32 * 5 - (2 * 10)/(0 -10) ",
            "(((((((1 * 5)- 7) / 5) / 10) - 7) * (2/4)) + 32)",
            "x + (x + 10.324)*x/((x) - 7.0) 2:3"
    };
    private static String[] incorrectInput = {
            "x",
            "x 12:3:2",
            "(5 + x)) / x 34",
            "x + y - 4.32 + 5 * (-1.9 + x)",
            "x + (x + 10.324)*x/((x) - 2.5)()",
            "5.0 12:24",
            "7.32 * 5 - (x * 10)/(x (-)10) ",
            "(((((((x * 5)- 7) / x) / 10) - 7) * x) + x",
            "x + (x + 10.324)*x/((x) - 7.0)) 34:334"
    };

    @Test
    public void testAnalyze() throws Exception {
        for (String data : correctInput) {
            System.out.print(data + " -- ");
            List<String> parsedInput = Parser.parse(data);
            List<String> analyzedInput = ExpressionAnalyzer.analyze(parsedInput);

            for (String s : parsedInput) {
                System.out.print("'" + s + "' ");
            }
            System.out.println();

            for (String s : analyzedInput) {
                System.out.print("'" + s + "' ");
            }
            System.out.println();

            Assert.assertEquals(parsedInput.size(), analyzedInput.size());
        }

        for (String data : incorrectInput) {
            //System.out.print(data + " -- ");
            try {
                ExpressionAnalyzer.analyze(Parser.parse(data));
            } catch (TooManyVariablesException ex) {
                System.out.println("Too Many Variables");
            } catch (WrongSyntaxException ex) {
                System.out.println("Wrong Syntax");
            } catch (ParenthesesBalanceException ex) {
                System.out.println("Parentheses Balance Exception");
            } catch (WrongRangeException ex) {
                System.out.println("Wrong Range");
            } catch (Exception ex) {
                System.out.println("Undefined exception");
            }
        }
    }
}