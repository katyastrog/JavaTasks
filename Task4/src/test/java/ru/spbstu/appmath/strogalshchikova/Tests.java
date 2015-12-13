/**
 * Created by Екатерина on 13.12.2015.
 */
package ru.spbstu.appmath.strogalshchikova;
public class Tests {
    private static String[] data = {
            "x",
            "(5 + x) / x",
            "x - 4.32 + 5 * (-1.9 + x)",
            "x + (x + 10.324)*x/((x) - 2.5)",
            "-5.0",
            "7.32 * 5 - (x * 10)/(x -10) ",
            "(((((((x * 5)- 7) / x) / 10) - 7) * x) + x",
            "x + (x + 10.324)*x/((x) - 7.0)"
    };

    private static Object[] answers = {
            0.0,
            6.0,
            17.18,
            82.944,
            -5.0,
            46.6
    };

}
