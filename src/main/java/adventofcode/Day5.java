package adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Day5 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    static int big = 127;
    static int small = 0;
    static int max = 0;

    private static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day5/input.txt");
        input.forEach(line -> {
            resetVals(127, 0);
            parseLetter(line.charAt(0));
            parseLetter(line.charAt(1));
            parseLetter(line.charAt(2));
            parseLetter(line.charAt(3));
            parseLetter(line.charAt(4));
            parseLetter(line.charAt(5));
            parseLetter(line.charAt(6));
            if (big != small) {
                int rrr = 0;
            }
            int row = big;

            resetVals(7, 0);
            parseLetter(line.charAt(7));
            parseLetter(line.charAt(8));
            parseLetter(line.charAt(9));
            if (big != small) {
                int brhg = 0;
            }
            int column = big;
            //multiply the row by 8, then add the column
            int endVal = row * 8 + column;
            if (endVal > max) {
                max = endVal;
            }

        });
        System.out.println("Part 1: " + max);

    }


    private static void resetVals(int big, int small) {
        Day5.big = big;
        Day5.small = small;
    }

    private static void parseLetter(char letter) {
        String ltr = String.valueOf(letter);
        if (ltr.equalsIgnoreCase("F") || ltr.equalsIgnoreCase("L")) {
            big = (int) (big - Math.ceil((big - small) / 2f));
        } else {
            small = (int) (small + Math.ceil((big - small) / 2f));
        }
    }


    private static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day5/input.txt");
        List<Integer> allIds = new ArrayList<>();
        input.forEach(line -> {
            resetVals(127, 0);
            parseLetter(line.charAt(0));
            parseLetter(line.charAt(1));
            parseLetter(line.charAt(2));
            parseLetter(line.charAt(3));
            parseLetter(line.charAt(4));
            parseLetter(line.charAt(5));
            parseLetter(line.charAt(6));
            if (big != small) {
                int rrr = 0;
            }
            int row = big;

            resetVals(7, 0);
            parseLetter(line.charAt(7));
            parseLetter(line.charAt(8));
            parseLetter(line.charAt(9));
            if (big != small) {
                int brhg = 0;
            }
            int column = big;
            //multiply the row by 8, then add the column
            int endVal = row * 8 + column;
            allIds.add(endVal);

        });
        Collections.sort(allIds);
        int num = 0;
        for (int i = 0; i < allIds.size(); i++) {
            int currNum = allIds.get(i);
            int nextNum = allIds.get(i + 1);
            if (currNum != nextNum - 1) {
                num = currNum + 1;
                break;
            }
        }
        System.out.println("Part 2: " + num);

    }

    private static boolean checkBirthYear(Map<String, String> fields) {
        // byr (Birth Year) - four digits; at least 1920 and at most 2002.
        int birthYear = Integer.parseInt(fields.get("byr"));
        return birthYear >= 1920 && birthYear <= 2002;

    }
}
