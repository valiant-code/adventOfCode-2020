package adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day1 {

    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        List<Integer> input = InputUtil.readFileAsIntList("day1/input.txt");
        boolean done = false;

        List<Integer> unprocessedInput = new ArrayList<>(input);
        for (int i = 0; i < input.size(); i++) {
            int cur = input.remove(0);
            Optional<Integer> partner = input.stream().filter(num -> num + cur == 2020).findAny();
            if (partner.isPresent()) {
                System.out.println("Part 1: " + cur * partner.get());
                done = true;
            }
            ;
        }
        if (!done) {
            System.out.println("Part 1 failed");

        }
    }

    private static void partTwo() throws IOException {
        List<Integer> input = InputUtil.readFileAsIntList("day1/input.txt");
        boolean done = false;

        for (int i = 0; i < input.size(); i++) {
            int cur = input.remove(0);
            List<Integer> unprocessedInput = new ArrayList<>(input);
            for (int j = 0; j < unprocessedInput.size(); j++) {
                int nxt = unprocessedInput.remove(0);
                int curVal = cur + nxt;
                Optional<Integer> partner = unprocessedInput.stream().filter(num -> num + curVal == 2020).findAny();
                if (partner.isPresent()) {
                    System.out.println("Part 2: " + cur * nxt * partner.get());
                    done = true;
                }
            }
        }
        if (!done) {
            System.out.println("Part 2 failed");
        }
    }

    private static int formula(int n) {
        return n / 3 - 2;
    }

}
