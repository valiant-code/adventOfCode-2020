package adventofcode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {


    public static void main(String[] args) throws IOException {
//        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day11/input.txt");

//        int row = 0;
//        int col = 0;


        List<List<String>> nextState = input.stream().map(j -> Arrays.asList(j.split(""))).collect(Collectors.toList());
        List<List<String>> currentState = new ArrayList<>();
        while (!currentState.equals(nextState)) {
            currentState = nextState;
            nextState = new ArrayList<>(currentState);
            for (int row = 0; row < currentState.size(); row++) {
                nextState.set(row, new ArrayList<>(currentState.get(row)));
                for (int col = 0; col < currentState.get(0).size(); col++) {
                    nextState.get(row).set(col, applyRules(row, col, currentState));
                }
            }
        }

        System.out.println("Part 1");
        printGraph(currentState);

        System.out.println("Count of seats occupied: " + currentState.stream().flatMap(List::stream).filter("#"::equals).count());
    }

    private static void printGraph(List<List<String>> currentState) {
        for (List<String> innerLs : currentState) {
            System.out.println(String.join("", innerLs));
        }
        System.out.println();
    }

    private static String applyRules(int row, int col, List<List<String>> currentState) {
        String seat = currentState.get(row).get(col);
        int length = currentState.get(row).size();
        //If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
        //If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
        //Otherwise, the seat's state does not change.
        if (seat.equals("L")) {
            List<String> rowAbove = row - 1 >= 0 ? currentState.get(row - 1).subList(Math.max(0, col - 1), Math.min(length, col + 2)) : Collections.emptyList();
            List<String> rowBelow = row + 1 < currentState.size() ? currentState.get(row + 1).subList(Math.max(0, col - 1), Math.min(length, col + 2)) : Collections.emptyList();
            String left = col - 1 >= 0 ? currentState.get(row).get(col - 1) : null;
            String right = col + 1 < length ? currentState.get(row).get(col + 1) : null;
            boolean occupy = Stream.concat(Stream.concat(Stream.of(left, right), rowAbove.stream()), rowBelow.stream())
                    .filter(Objects::nonNull).noneMatch(s -> s.contains("#"));
            return occupy ? "#" : "L";
        } else if (seat.equals("#")) {
            List<String> rowAbove = row - 1 >= 0 ? currentState.get(row - 1).subList(Math.max(0, col - 1), Math.min(length, col + 2)) : Collections.emptyList();
            List<String> rowBelow = row + 1 < currentState.size() ? currentState.get(row + 1).subList(Math.max(0, col - 1), Math.min(length, col + 2)) : Collections.emptyList();
            String left = col - 1 >= 0 ? currentState.get(row).get(col - 1) : null;
            String right = col + 1 < length ? currentState.get(row).get(col + 1) : null;
            boolean vacate = Stream.concat(Stream.concat(Stream.of(left, right), rowAbove.stream()), rowBelow.stream())
                    .filter(Objects::nonNull).flatMap(s -> Arrays.stream(s.split(""))).filter(s -> s.equals("#")).count() >= 4;
            return vacate ? "L" : "#";
        } else return seat;
    }

    private static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day11/input.txt");

        List<List<String>> nextState = input.stream().map(j -> Arrays.asList(j.split(""))).collect(Collectors.toList());
        List<List<String>> currentState = new ArrayList<>();
        while (!currentState.equals(nextState)) {
            currentState = nextState;
            nextState = new ArrayList<>(currentState);
            for (int row = 0; row < currentState.size(); row++) {
                nextState.set(row, new ArrayList<>(currentState.get(row)));
                for (int col = 0; col < currentState.get(0).size(); col++) {
                    nextState.get(row).set(col, applyRules_p2(row, col, currentState));
                }
            }
        }

        System.out.println("Part 2");
        printGraph(currentState);
        System.out.println("Count of seats occupied: " + currentState.stream().flatMap(List::stream).filter("#"::equals).count());

    }

    private static String searchAlongLine(int rowStart, int colStart, int rowDelta, int colDelta, List<List<String>> currentState) {
        String ret = ".";
        int currRow = rowStart;
        int currCol = colStart;
        while (".".equals(ret)) {
            currRow = currRow += rowDelta;
            currCol = currCol += colDelta;
            if (currRow < 0 || currRow >= currentState.size() ||
                    currCol < 0 || currCol >= currentState.get(currRow).size()) {
                ret = null;
            } else {
                ret = currentState.get(currRow).get(currCol);
            }
        }
        return ret;
    }

    private static String applyRules_p2(int row, int col, List<List<String>> currentState) {
        String seat = currentState.get(row).get(col);
        int length = currentState.get(row).size();
        int height = currentState.size();

        List<String> seatsInSight = Arrays.asList(
                //above
                searchAlongLine(row, col, -1, -1, currentState),
                searchAlongLine(row, col, -1, 0, currentState),
                searchAlongLine(row, col, -1, +1, currentState),
                //left + right
                searchAlongLine(row, col, 0, -1, currentState),
                searchAlongLine(row, col, 0, +1, currentState),
                //below
                searchAlongLine(row, col, +1, -1, currentState),
                searchAlongLine(row, col, +1, 0, currentState),
                searchAlongLine(row, col, +1, +1, currentState)
        );

        if (seat.equals("L")) {
            // The other rules still apply: empty seats that see no occupied seats become occupied,
            boolean occupy = seatsInSight.stream().filter(Objects::nonNull).noneMatch(s -> s.contains("#"));
            return occupy ? "#" : "L";
        } else if (seat.equals("#")) {
            //five or more visible occupied seats for an occupied seat to become empty (rather than four or more from the previous rules).
            boolean vacate = seatsInSight.stream().filter(Objects::nonNull).filter(s -> s.contains("#")).count() >= 5;
            return vacate ? "L" : "#";
        } else return seat;
    }
}
