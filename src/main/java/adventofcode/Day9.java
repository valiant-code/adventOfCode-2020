package adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day9 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        AtomicInteger count = new AtomicInteger();
        List<String> input = InputUtil.readFileAsStringList("day9/input.txt");

        List<Long> values = input.stream().map(Long::parseLong).collect(Collectors.toList());
        int SIZE = 25;
        List<Long> window = values.subList(0, SIZE + 1);
        int unmatchedIndex = -1;
        for (int i = SIZE; i < values.size(); i++) {
            Long val = values.get(i);
            boolean match = false;
            for (int j = 0; j < window.size(); j++) {
                List<Long> windowCopy = new ArrayList<>(window);
                Long num1 = windowCopy.remove(j);
                if (windowCopy.stream().anyMatch(num2 ->
                        num1 + num2 == val)) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                unmatchedIndex = i;
                break;
            } else {
                window.remove(0);
                window.add(val);
            }

        }


        System.out.println("Part 1: " + values.get(unmatchedIndex));

    }


    private static void partTwo() throws IOException {
        AtomicInteger count = new AtomicInteger();
        //find a contiguous set of at least two numbers
        // in your list which sum to the invalid number from step 1.
        Long PART_ONE_NUM = 18272118L;

        List<String> input = InputUtil.readFileAsStringList("day9/input.txt");
        List<Long> values = input.stream().map(Long::parseLong).collect(Collectors.toList());

        //start looking 2 at a time and increase each
        List<Long> answers = new ArrayList<>();
        for (int i = 2; i < values.size(); i++) {
            for (int j = 0; j < values.size(); j++) {
                List<Long> window = values.subList(j, Math.min(j + i, values.size()));
                if (PART_ONE_NUM.equals(window.stream().mapToLong(Long::longValue).sum())) {
                    answers = window;
                }
            }
            if (answers.size() > 0) {
                break;
            }
        }

        Collections.sort(answers);
        System.out.println("Part 2 list " + answers.toString());
        System.out.println("Part 2 enc weakness " + (answers.get(0) + answers.get(answers.size() - 1)));
    }
}
