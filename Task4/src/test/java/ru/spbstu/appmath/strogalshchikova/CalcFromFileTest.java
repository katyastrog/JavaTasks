package ru.spbstu.appmath.strogalshchikova;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CalcFromFileTest {
    private final static String INPUT_FILE = "./src/resources/test_examples_input.txt";
    private final static String OUTPUT_FILE = "./src/resources/test_examples_output.txt";

    @Test
    public void testCalcWithFile() throws Exception {
        final List<String[]> inputList = new ArrayList<>();

        File file = new File(INPUT_FILE);

        if (!file.exists() || !file.isFile())
            throw new IOException("Input file doesn't exist.");

        try (final Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.trim().length() == 0)
                    continue;

                final String[] input = line.split("#");
                inputList.add(input);
            }
        }

//        for (String[] input : inputList) {
//            for (String s : input) {
//                System.out.print("{" + s + "} ");
//            }
//            System.out.println();
//        }
        file = new File(OUTPUT_FILE);

        if (!file.exists() || !file.isFile())
            throw new FileNotFoundException("Output file doesn't exist.");

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String[] input : inputList) {
                String result = Calculator.getAnswer(input) + "\n";
                writer.write(result);
                //System.out.print(result);
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
