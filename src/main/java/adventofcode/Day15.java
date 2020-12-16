package adventofcode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 {

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true;
        boolean runPart2 = true;
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

    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day15/input.txt", ",");
        List<Integer> numberList = input.stream().map(Integer::parseInt).collect(Collectors.toList());
        Integer numberUnderConsideration = numberList.remove(numberList.size() - 1);
        while (numberList.size() < 2020) {
            int lastIndexOfNum = numberList.lastIndexOf(numberUnderConsideration);
            numberList.add(numberUnderConsideration);
            numberUnderConsideration = lastIndexOfNum == -1 ? 0 : (numberList.size() - 1) - lastIndexOfNum;
        }

        System.out.println("Part 1: " + numberList.get(2019));
    }


    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day15/input.txt", ",");
        List<Integer> numberInput = input.stream().map(Integer::parseInt).collect(Collectors.toList());
        //remove the last number to store it in the numberUnderConsideration field
        Integer numberUnderConsideration = numberInput.remove(numberInput.size() - 1);
        //30000000 is a much larger target.. fine I'll use a map
        Map<Integer, Integer> numberMap = new HashMap<>(3911423, .9f);
        for (int i = 0; i < numberInput.size(); i++) { //put our initial input into the map
            numberMap.put(numberInput.get(i), i);
        }
        for (int turnIndex = numberInput.size(); turnIndex < 30000000 - 1; turnIndex++) {
            //find out if our numberUnderConsideration is already in the map, and if so what its last index was
            Integer indexOfLastNum = numberMap.get(numberUnderConsideration);
            //with that index figured out, we can now go ahead and put this number into the map
            numberMap.put(numberUnderConsideration, turnIndex);
            //figure out what the next number to go in the map should be
            numberUnderConsideration = indexOfLastNum == null ? 0 : turnIndex - indexOfLastNum;
        }

        System.out.println("Part 2: " + numberUnderConsideration);
    }


}
