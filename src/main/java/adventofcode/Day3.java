package adventofcode;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Day3 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day3/input.txt");
        System.out.println("Part 1: " + checkSlope(3, 1, input));

    }


    //Right 1, down 1.
    //Right 3, down 1. (This is the slope you already checked.)
    //Right 5, down 1.
    //Right 7, down 1.
    //Right 1, down 2.
    private static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day3/input.txt");

        long val = Stream.of(
                checkSlope(1, 1, input),
                checkSlope(3, 1, input),
                checkSlope(5, 1, input),
                checkSlope(7, 1, input),
                checkSlope(1, 2, input)
        ).mapToLong(Integer::longValue).reduce(1l, Math::multiplyExact);

        System.out.println("Part 2: " + val);

    }

    static int checkSlope(int x, int y, List<String> input) {
        int currX = 0;
        int treeCount = 0;

        for (int currY = y; currY < input.size(); currY += y) {
            currX += x;
            int rollover = currX - input.get(currY).length();
            if (rollover >= 0) {
                currX = Math.abs(rollover);
            }

            if (input.get(currY).charAt(currX) == '#') {
                treeCount++;
            }
        }
        return treeCount;

    }

}
