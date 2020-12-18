package adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    private static long evaluateExpression(String expression) {
        //while the expression has any more parenthesis inside, call the function to evaluate them
        while (expression.contains("(")) {
            expression = evaluateParens(expression);
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

        //start with the first number and then evaluate from left to ight
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

    private static String evaluateParens(String expression) {
        //work backwards, getting the last ( and then the first ) after that
        int startIndex = expression.lastIndexOf("(");
        int endIndex = expression.indexOf(")", startIndex);
        //a little bit of recursive ping pong here, call evaluateExpression for the string inside our parens
        String expressionWithinParens = expression.substring(startIndex, endIndex + 1).replaceAll("[()]", "");
        long innerExpressionValue = evaluateExpression(expressionWithinParens);
        //our result is the original expression string, with the resulting value in place of the parenthetical
        String result = expression.substring(0, startIndex)
                .concat(String.valueOf(innerExpressionValue))
                .concat(expression.substring(endIndex + 1));

        return result;
    }

    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day18/input.txt");
        AtomicLong acc = new AtomicLong();
        input.forEach(expressionChars -> acc.addAndGet(evaluateExpression(expressionChars)));
        //sample expected 669,106
        System.out.println("Part 2 Answer: " + acc);
    }

}
