package ru.spbstu.appmath.strogalshchikova;

import ru.spbstu.appmath.strogalshchikova.exceptions.DivisionByZeroException;
import ru.spbstu.appmath.strogalshchikova.exceptions.WrongRangeException;
import ru.spbstu.appmath.strogalshchikova.exceptions.WrongSyntaxException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {

    private final String inputFile;
    private final String outputFile;
    private final Range range;

    public FileHandler(final String[] args) throws IOException, WrongSyntaxException, WrongRangeException {
        if (args.length != 3)
            throw new IllegalArgumentException();

        range = new Range(args[2]);

        inputFile = args[0];
        outputFile = args[1];
    }

    public static void analyzeRange(final Expression.Lexeme range) throws WrongRangeException {
        final Matcher matcher = Pattern.compile(Expression.Parser.RE_NUM).matcher(range.getValue());
        Double d[] = {null, null, null};
        int i = 0;
        while (matcher.find()) {
            d[i] = (Double.valueOf(range.getValue().substring(matcher.start(), matcher.end())));
            ++i;
        }

        if ((i == 2 && d[0] > d[1]) ||
                (i == 3 && (Math.signum(d[1] - d[0]) != Math.signum(d[2]) || d[2].equals(0.0))))
            throw new WrongRangeException();
    }

    public static void main(String[] args) throws WrongRangeException, WrongSyntaxException, IOException {
        final String INPUT_FILE = "./src/resources/input.txt";
        final String OUTPUT_FILE = "./src/resources/output.txt";
        final String RANGE = "-4:8";

        final String[] input = new String[]{INPUT_FILE, OUTPUT_FILE, RANGE};

//        System.out.println(input[0]);
//        System.out.println(input[1]);
//        System.out.println(input[2]);

        final FileHandler handler = new FileHandler(input);
        handler.run();

    }

    public void run() throws IOException {
        File file = new File(inputFile);

        if (!file.exists() || !file.isFile())
            throw new IOException("Input file doesn't exist.");

        final List<Expression> expressions = new ArrayList<>();

        try (final Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.trim().length() == 0)
                    continue;
                try {
                    expressions.add(new Expression(line));
                } catch (WrongSyntaxException e) {
                    System.out.println(line + " -- wrong syntax");
                }
            }
        }

        file = new File(outputFile);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Output file doesn't exist.");
        }

        final int n;
        if (range.getStep() == 0) {
            n = (int) (range.getMax() - range.getMin());
        } else {
            n = (int) ((range.getMax() - range.getMin()) / range.getStep());
        }

        final String[][] results = new String[n][expressions.size()];

        for (int j = 0; j < expressions.size(); j++) {
            final Expression exp = expressions.get(j);
            results[0][j] = exp.toString();
            for (int i = 1; i < n; i++) {
                try {
                    results[i][j] = String.valueOf(exp.calculate(range.getMin() + range.getStep() * (double) (i - 1)));
                } catch (DivisionByZeroException e) {
                    results[i][j] = "Division by zero";
                }
            }
        }

        final int[] maxLen = new int[n];
        for (int i = 0; i < expressions.size(); i++) {
            maxLen[i] = 0;
            for (int j = 0; j < n; j++) {
                if (maxLen[i] < results[j][i].length())
                    maxLen[i] = results[j][i].length();
            }
        }

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < n; i++) {
                final StringBuilder line = new StringBuilder();

                for (int j = 0; j < expressions.size(); j++) {
                    line.append(String.format("%" + (maxLen[j] + 3) + "s", results[i][j]));
                }
                line.append("\r\n");
                writer.write(line.toString());
                //System.out.println(line.toString());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private class Range {
        final double min;
        final double max;
        final double step;

        public Range(final String range) throws WrongRangeException, WrongSyntaxException {
            if (!range.matches(Expression.Parser.RE_RANGE))
                throw new WrongSyntaxException();
            analyzeRange(new Expression.Lexeme(range));

            final String[] parsedRange = range.split(":");
            min = Double.valueOf(parsedRange[0]);
            max = Double.valueOf(parsedRange[1]);
            if (parsedRange.length == 3)
                step = Double.valueOf(parsedRange[2]);
            else
                step = 1.0;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }

        public double getStep() {
            return step;
        }
    }
}
