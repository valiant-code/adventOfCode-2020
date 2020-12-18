package adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 {

    private static int currentPart = 0;

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //12956356593940
        boolean runPart2 = true; //94240043727614
        if (runPart1) {
            currentPart = 1;
            TimeUtil.startClock(1);
            partOne();
            TimeUtil.time();
        }
        if (runPart2) {
            currentPart = 2;
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
        input.forEach(expressionChars -> acc.addAndGet(evaluateExpression(expressionChars)));
        //sample expected 12266
        System.out.println("Part 1 Answer: " + acc);
    }

    private static Pattern innerMostParensRegex = Pattern.compile(
            //negative lookahead to ensure we find the last (
            // and then lazy dot quantifier .*?) to get the first right paren after that
            "\\((?!.*\\().*?\\)");

    private static long evaluateExpression(String expression) {
        //while the expression has any more parenthesis inside, find the innermost parenthetical expressions
        // and recursively call this function on that substring to evaluate them
        while (expression.contains("(")) {
            Matcher matcher = innerMostParensRegex.matcher(expression);
            matcher.find();
            String innerExpression = matcher.group().replaceAll("[()]", "");
            expression = matcher.replaceFirst(String.valueOf(evaluateExpression(innerExpression)));

        }

        List<String> symbols = new ArrayList<>(Arrays.asList(expression.split(" ")));

        //extra logic needed for part 2 only - handle addition operations first
        if (currentPart == 2) {
            //with parens already handled through the above recursion
            //go ahead and evaluate all plus operations from left to right
            while (symbols.contains("+")) {
                //note this wouldn't handle a string beginning or ending with + but that's ok
                int iOp = symbols.indexOf("+");
                int iNum1 = iOp - 1;
                int iNum2 = iOp + 1;
                //replace num1 with the summed value, and remove the 2 other symbols (+, and num2)
                symbols.set(iNum1, String.valueOf(Long.parseLong(symbols.get(iNum1)) + Long.parseLong(symbols.get(iNum2))));
                symbols.remove(iNum2);
                symbols.remove(iOp);
            }
        }

        //start with the first number and then evaluate from left to right
        long currNum = Long.parseLong(symbols.remove(0));
        String currOperation = null;
        for (String symbol : symbols) {
            //if the currOperation is null, the current symbol should be + or *
            if (currOperation == null) {
                currOperation = symbol;
            } else {
                //we already know what operation to do, so do it
                switch (currOperation) {
                    case "*":
                        currNum *= Long.parseLong(symbol);
                        break;
                    case "+":
                        currNum += Long.parseLong(symbol);
                        break;
                    default:
                        throw new RuntimeException("error parsing symbols");
                }
                //clear out the operation, the next symbol should be another one
                currOperation = null;
            }
        }

        return currNum;
    }

    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day18/input.txt");
        AtomicLong acc = new AtomicLong();
        input.forEach(expressionChars -> acc.addAndGet(evaluateExpression(expressionChars)));
        //sample expected 669,106
        System.out.println("Part 2 Answer: " + acc);
    }

}
