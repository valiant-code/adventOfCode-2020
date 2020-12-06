package adventofcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day6 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        AtomicInteger count = new AtomicInteger();
        List<String> input = InputUtil.readFileAsStringList("day6/input.txt", "\n\n");

        input.forEach(group -> {
            Map<String, Integer> groupAnswers = new HashMap<>();
            String[] peopleInGroup = group.split("\n");
            Arrays.stream(peopleInGroup).forEach(line -> {
                line.chars().mapToObj(c -> String.valueOf((char) c)).forEach(letter -> {
                    groupAnswers.merge(letter, 1, Integer::sum);
                });
            });

            count.addAndGet(groupAnswers.keySet().size());
        });

        System.out.println("Part 1: " + count);

    }

    private static void partTwo() throws IOException {
        AtomicInteger count = new AtomicInteger();
        List<String> input = InputUtil.readFileAsStringList("day6/input.txt", "\n\n");

        input.forEach(group -> {
            Map<String, Integer> groupAnswers = new HashMap<>();
            String[] peopleInGroup = group.split("\n");
            Arrays.stream(peopleInGroup).forEach(person -> {
                person.chars().mapToObj(c -> String.valueOf((char) c)).forEach(letter -> {
                    groupAnswers.merge(letter, 1, Integer::sum);
                });
            });

            count.addAndGet((int) groupAnswers.entrySet().stream()
                    .filter(answerCount -> answerCount.getValue() == peopleInGroup.length)
                    .count());
        });

        System.out.println("Part 2: " + count);
    }
}
