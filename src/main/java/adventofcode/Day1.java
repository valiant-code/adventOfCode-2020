package adventofcode;

import java.io.IOException;
import java.util.List;

public class Day1 {

    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        List<Integer> input = InputUtil.readFileAsIntList("day1/input.txt");
        int accumulator = input.stream().mapToInt(Day1::formula).sum();
        System.out.println("Part 1: " + accumulator);
    }

    private static void partTwo() throws IOException {
        List<Integer> input = InputUtil.readFileAsIntList("day1/input.txt");
        int accumulator = 0;
        accumulator = input.stream().mapToInt(num -> {
            int baseFuelNeeded = formula(num);
            int totalFuelNeeded = baseFuelNeeded;
            int fuelNeededForFuelWeight = formula(baseFuelNeeded);
            while (fuelNeededForFuelWeight > 0) {
                totalFuelNeeded += fuelNeededForFuelWeight;
                fuelNeededForFuelWeight = formula(fuelNeededForFuelWeight);
            }
            return totalFuelNeeded;
        }).sum();
        System.out.println("Part 2: " + accumulator);
    }

    private static int formula(int n) {
        return n / 3 - 2;
    }

}
