package adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day18 {

    public static void main(String[] args) throws IOException {
        boolean runPart1 = false; //
        boolean runPart2 = true; //
        if (runPart1) {
            TimeUtil.startClock(1);
            partOne();
            TimeUtil.time();
        }
        if (runPart2) {
            TimeUtil.startClock(2);
            partTwo();
            TimeUtil.time();
        }
    }

    private static List<Integer> key(Integer... coordinates) {
        return Arrays.asList(coordinates);
    }

    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day18/input.txt");
        AtomicLong acc = new AtomicLong();
        input.stream()
//                .map(e -> new ArrayList<>(Arrays.asList(e.replaceAll(" ", "").split(""))))
                .forEach(expressionChars -> {
                    acc.addAndGet(evaluateExpresion(expressionChars));
                });
        //sample expected 12266
        System.out.println("Part 1 Answer: " + acc);
    }

    private static long evaluateExpresion(String expressionChars) {
        //recursive to make sure parens are evaluated
        while (expressionChars.contains("(")) {
            expressionChars = evaluateParen(expressionChars);
        }

        List<String> symbols = new ArrayList<>(Arrays.asList(expressionChars.split(" ")));
        String currOperation = null;
        long currNum = Long.parseLong(symbols.remove(0));
        for (String symbol : symbols) {
            if (currOperation == null) {
                currOperation = symbol;
            } else {
                switch (currOperation) {
                    case "+":
                        currNum += Long.parseLong(symbol);
                        break;
                    case "*":
                        currNum *= Long.parseLong(symbol);
                        break;
                    default:
                        throw new RuntimeException("error parsing symbols");
                }
                currOperation = null;
            }
        }

        return currNum;
    }

    private static String evaluateParen(String expressionChars) {
        int startIndex = expressionChars.lastIndexOf("(");
        int endIndex = expressionChars.indexOf(")", startIndex);
        expressionChars = expressionChars.substring(0, startIndex)
                .concat(String.valueOf(evaluateExpresion(
                        expressionChars.substring(startIndex, endIndex + 1).replaceAll("[()]", "")))
                )
                .concat(expressionChars.substring(endIndex + 1));

        return expressionChars;
    }

    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day18/sample.txt");
        AtomicLong acc = new AtomicLong();
        input.stream()
//                .map(e -> new ArrayList<>(Arrays.asList(e.replaceAll(" ", "").split(""))))
                .forEach(expressionChars -> {
                    acc.addAndGet(evaluateExpresion(expressionChars));
                });
        //sample expected 669,106
        System.out.println("Part 2 Answer: " + acc);
    }

}
