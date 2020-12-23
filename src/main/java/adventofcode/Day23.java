package adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day23 {

    private static int currentPart = 0;

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //
        boolean runPart2 = true; //
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


    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day23/input.txt", "");
        List<Integer> circle = input.stream().map(Integer::valueOf).collect(Collectors.toList());

        //Before the crab starts, it will designate the first cup in your list as the current cup.
        int currentCupIndex = 0;
        List<Integer> pickedUpCups = new ArrayList<>();
        // The crab is then going to do 100 moves.
        for (int i = 0; i < 100; i++) {
            //Each move, the crab does the following actions:
            int currentCup = circle.get(currentCupIndex);
            //The crab picks up the three cups that are immediately clockwise of the current cup.
            // They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.
            int pickupIndex = currentCupIndex + 1;
            for (int j = 0; j < 3; j++) {
                if (circle.size() > pickupIndex) {
                    pickedUpCups.add(0, circle.remove(pickupIndex));
                } else {
                    pickupIndex = 0;
                    pickedUpCups.add(0, circle.remove(pickupIndex));
                }
            }

            //The crab selects a destination cup:
            // the cup with a label equal to the current cup's label minus one.
            // If this would select one of the cups that was just picked up,
            // the crab will keep subtracting one until it finds a cup that wasn't just picked up.
            // it wraps around to the highest value on any cup's label instead.
            Integer destinationCupIndex = null;
            int destinationCupValue = currentCup - 1;
            while (destinationCupIndex == null) {
                if (destinationCupValue <= 0) {
                    // If at any point in this process the value goes below the lowest value on any cup's label,
                    destinationCupIndex = circle.indexOf(circle.stream()
                            .mapToInt(Integer::intValue).max().getAsInt());
                } else {
                    int index = circle.indexOf(destinationCupValue);
                    if (index == -1) {
                        destinationCupValue--;
                    } else {
                        destinationCupIndex = index;
                    }
                }
            }

            //The crab places the cups it just picked up so that they are immediately clockwise of the destination cup.
            // They keep the same order as when they were picked up.
            circle.add(destinationCupIndex + 1, pickedUpCups.remove(0));
            circle.add(destinationCupIndex + 1, pickedUpCups.remove(0));
            circle.add(destinationCupIndex + 1, pickedUpCups.remove(0));

            //The crab selects a new current cup: the cup which is immediately clockwise of the current cup.
            currentCupIndex = circle.indexOf(currentCup);
            currentCupIndex = currentCupIndex + 1 >= circle.size() ? 0 : currentCupIndex + 1;
        }
        String[] parts = circle.toString().split("1");


        System.out.println("Part 1 Answer: " + ("" + parts[1] + parts[0]).replaceAll("\\D", ""));
    }


    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day23/sample.txt", "");
        List<Integer> circle = input.stream().map(Integer::valueOf).collect(Collectors.toList());

        List<Integer> bigCircle = new ArrayList<>(1700000);
        bigCircle.addAll(circle);

        for (int i = 10; i < 1000001; i++) {
            bigCircle.add(i);
        }

        //Before the crab starts, it will designate the first cup in your list as the current cup.
        int currentCupIndex = 0;
        List<Integer> pickedUpCups = new ArrayList<>();
        // The crab is then going to do 100 moves.
        for (int i = 0; i < 10000000; i++) {
            //Each move, the crab does the following actions:
            int currentCup = bigCircle.get(currentCupIndex);
            //The crab picks up the three cups that are immediately clockwise of the current cup.
            // They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.
            int pickupIndex = currentCupIndex + 1;
            for (int j = 0; j < 3; j++) {
                if (bigCircle.size() > pickupIndex) {
                    pickedUpCups.add(0, bigCircle.remove(pickupIndex));
                } else {
                    pickupIndex = 0;
                    pickedUpCups.add(0, bigCircle.remove(pickupIndex));
                }
            }

            //The crab selects a destination cup:
            // the cup with a label equal to the current cup's label minus one.
            // If this would select one of the cups that was just picked up,
            // the crab will keep subtracting one until it finds a cup that wasn't just picked up.
            // it wraps around to the highest value on any cup's label instead.
            Integer destinationCupIndex = 0;
//            int destinationCupValue = currentCup - 1;
//            while (destinationCupIndex == null) {
//                if (destinationCupValue <= 0) {
//                    // If at any point in this process the value goes below the lowest value on any cup's label,
//                    destinationCupIndex = bigCircle.indexOf(bigCircle.stream()
//                            .mapToInt(Integer::intValue).max().getAsInt());
//                } else {
//                    int index = bigCircle.indexOf(destinationCupValue);
//                    if (index == -1) {
//                        destinationCupValue--;
//                    } else {
//                        destinationCupIndex = index;
//                    }
//                }
//            }

            //The crab places the cups it just picked up so that they are immediately clockwise of the destination cup.
            // They keep the same order as when they were picked up.
            bigCircle.add(destinationCupIndex + 1, pickedUpCups.remove(0));
            bigCircle.add(destinationCupIndex + 1, pickedUpCups.remove(0));
            bigCircle.add(destinationCupIndex + 1, pickedUpCups.remove(0));

            //The crab selects a new current cup: the cup which is immediately clockwise of the current cup.
            currentCupIndex = bigCircle.indexOf(currentCup);
            currentCupIndex = currentCupIndex + 1 >= bigCircle.size() ? 0 : currentCupIndex + 1;
        }

        int oneIndex = bigCircle.indexOf(1);
        Integer oneA = bigCircle.get(oneIndex + 1);
        Integer oneB = bigCircle.get(oneIndex + 2);


        System.out.println("Part 2 Answer: " + oneA * oneB.longValue());
    }
}
