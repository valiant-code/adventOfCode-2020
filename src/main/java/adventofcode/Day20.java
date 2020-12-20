package adventofcode;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day20 {

    private static int currentPart = 0;

    private static List<Integer> key(Integer... coordinates) {
        return Arrays.asList(coordinates);
    }


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


    final static List<List<Integer>> topRowCoordinates = generateEdge(0, 0, 1, 0);
    final static List<List<Integer>> bottomRowCoordinates = generateEdge(0, 9, 1, 0);
    final static List<List<Integer>> leftColCoordinates = generateEdge(0, 0, 0, 1);
    final static List<List<Integer>> rightColCoordinates = generateEdge(9, 0, 0, 1);

    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day20/sample.txt", "\n\n");

        Pattern digitPattern = Pattern.compile("\\d+");
        Map<Integer, Map<List<Integer>, Boolean>> allTilesMap = input.stream()
                .collect(Collectors.toMap(tile -> {
                    List<String> tileLines = Arrays.asList(tile.split("\n"));
                    Matcher m = digitPattern.matcher(tileLines.get(0));
                    m.find();
                    Integer tileNum = Integer.valueOf(m.group());
                    return tileNum;
                }, tile -> {
                    List<String> tileLines = Arrays.asList(tile.split("\n"));
                    return getCoordinateMap(tileLines.subList(1, tileLines.size()));
                }));

        //Sample Tile 2311:
        //..##.#..#.
        //##..#.....
        //#...##..#.
        //####.#...#
        //##.##.###.
        //##...#.###
        //.#.#.#..##
        //..#....#..
        //###...#.#.
        //..###..###
        //test to make sure coordinate math is correct
        //printEdges(2311, allTilesMap);


        //end square is 12x12 tiles
        //CEE EEE EEE EEC
        //COO OOO OOO OOC
        //COO OOO OOO OOC
        //COO OOO OOO OOC

        //COO OOO OOO OOC
        //COO OOO OOO OOC
        //COO OOO OOO OOC
        //COO OOO OOO OOC

        //COO OOO OOO OOC
        //COO OOO OOO OOC
        //COO OOO OOO OOC
        //CEE EEE EEE EEC

        //but right now we only need to find ids of corners, no need to match anything else
        List<ImmutablePair<Integer, Map<List<Integer>, Boolean>>> possibleCorners = new ArrayList<>();

        allTilesMap.keySet().forEach(tileNum -> {
            if (isAPossibleCorner(tileNum, allTilesMap)) {
                possibleCorners.add(new ImmutablePair<>(tileNum, allTilesMap.get(tileNum)));
            }
        });


        System.out.println("Part 1 Answer: " + -1);
    }

    private static boolean isAPossibleCorner(Integer tileNum, Map<Integer, Map<List<Integer>, Boolean>> allTilesMap) {
        //TODO hoping that corners will have exactly 2 possible partners
        //but that could not be true, not sure how to fully solve the puzzle

        return false;
    }

    private static void printEdges(int tile, Map<Integer, Map<List<Integer>, Boolean>> allTilesMap) {
        System.out.println();
        topRowCoordinates.forEach(coordinate -> System.out.print(allTilesMap.get(tile).get(coordinate) ? "#" : "."));
        System.out.println();
        for (int i = 0; i < leftColCoordinates.size(); i++) {
            System.out.println((allTilesMap.get(tile).get(leftColCoordinates.get(i)) ? "#" : ".") + "        " +
                    (allTilesMap.get(tile).get(rightColCoordinates.get(i)) ? "#" : "."));
        }
        bottomRowCoordinates.forEach(coordinate -> System.out.print(allTilesMap.get(tile).get(coordinate) ? "#" : "."));
        System.out.println();
    }

    private static List<List<Integer>> generateEdge(int startX, int startY, int deltaX, int deltaY) {
        List<List<Integer>> keysList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            keysList.add(key(startX, startY));
            startX += deltaX;
            startY += deltaY;
        }

        return keysList;
    }

    private static Map<List<Integer>, Boolean> getCoordinateMap(List<String> tileLines) {
        Map<List<Integer>, Boolean> map = new HashMap<>();
        //tile is 10x10
        for (int i = 0; i < tileLines.size(); i++) {
            List<Boolean> line = Arrays.stream(tileLines.get(i).split("")).map("#"::equals).collect(Collectors.toList());
            for (int j = 0; j < line.size(); j++) {
                map.put(key(j, i), line.get(j));
            }
        }
        return map;
    }

    public static void partTwo() throws IOException {

    }

}
