package adventofcode;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day10 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        List<Integer> input = InputUtil.readFileAsIntList("day10/input.txt");
        Map<Integer, AtomicInteger> counts = new HashMap<>();
        counts.put(1, new AtomicInteger());
        counts.put(2, new AtomicInteger());
        counts.put(3, new AtomicInteger());
        Collections.sort(input);
        input.add(input.get(input.size() - 1) + 3);

        int voltage = 0;
        while (input.size() > 0) {
            int oneOffIndex = findIndexWithDifferenceOf(voltage, 1, input);
            if (oneOffIndex >= 0) {
                counts.get(1).incrementAndGet();
                voltage = input.remove(oneOffIndex);
            } else {
                int twoOffIndex = findIndexWithDifferenceOf(voltage, 2, input);
                if (twoOffIndex >= 0) {
                    counts.get(2).incrementAndGet();
                    voltage = input.remove(twoOffIndex);
                } else {
                    int threeOffIndex = findIndexWithDifferenceOf(voltage, 3, input);
                    if (threeOffIndex >= 0) {
                        counts.get(3).incrementAndGet();
                        voltage = input.remove(threeOffIndex);
                    } else {
                        throw new RuntimeException("error in algorithm");
                    }
                }
            }
        }

        System.out.println("Part 1 counts: " + counts);
        System.out.println("Part 1 voltage: " + voltage);
        System.out.println("Part 1 math: " + (counts.get(1).get() * counts.get(3).get()));

    }

    private static int findIndexWithDifferenceOf(int val, int difference, List<Integer> input) {
        int target = val + difference;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) == target) {
                return i;
            }
        }
        return -1;
    }

    private static long waysToArriveAt(int num, Map<Integer, Long> memory) throws IOException {
        long acc = 0L;
        if (memory.containsKey(num - 1)) {
            acc += memory.get(num - 1);
        }
        if (memory.containsKey(num - 2)) {
            acc += memory.get(num - 2);
        }
        if (memory.containsKey(num - 3)) {
            acc += memory.get(num - 3);
        }
        return acc;
    }

    private static void partTwo() throws IOException {
        List<Integer> input = InputUtil.readFileAsIntList("day10/input.txt");
        //num, waysToGetThere
        Map<Integer, Long> memory = new HashMap<>();
        memory.put(0, 1L);
        memory.put(1, 1L);
        Collections.sort(input);
        input.add(input.get(input.size() - 1) + 3);
        for (int i = 0; i < input.size(); i++) {
            Integer val = input.get(i);
            memory.put(val, waysToArriveAt(val, memory));
        }


        System.out.println("Part 2 total ways to get to " + input.get(input.size() - 1) +
                ": " + memory.get(input.get(input.size() - 1)));
    }
}
