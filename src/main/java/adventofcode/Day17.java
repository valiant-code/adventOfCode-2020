package adventofcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day17 {

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true;
        boolean runPart2 = false;
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
        List<String> input = InputUtil.readFileAsStringList("day17/sample.txt");
        int universeLength = 500;
        int midpoint = universeLength / 2;
        Map<List<Integer>, Boolean> universeMap = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            List<Boolean> line = Arrays.stream(input.get(i).split("")).map("#"::equals).collect(Collectors.toList());
            for (int j = 0; j < line.size(); j++) {
                if (line.get(j)) {
                    universeMap.put(key(midpoint + j, midpoint + i, midpoint), true);
                }
            }
        }

        int iterationsForPart1 = 6;
        for (int cycle = 0; cycle < iterationsForPart1; cycle++) {
            //update nextState using logic on currentState
            HashMap<List<Integer>, Boolean> nextUniverseState = new HashMap<>(universeMap);


            universeMap = nextUniverseState;
        }
        //TODO keep a hashmap of everything that is active and then find their neighbors,
        // then only those active + neighbors need logic run on them
        // map.getOrDefault(key, false);
        // also state needs to be executed all at once, which map will make easier


        System.out.println("Part 1 Answer: " + -1);
    }

    public static List<Integer> key(int x, int y, int z) {
        return Arrays.asList(x, y, z);
    }

    public static int countActiveNeighbors(int x, int y, int z, Boolean[][][] universe) {
        int count = 0;
        for (int xi = 0; xi < 3; xi++) {
            for (int yi = 0; yi < 3; yi++) {
                for (int zi = 0; zi < 3; zi++) {
                    if (universe[x + xi - 1][y + yi - 1][z + zi - 1]) {
                        count++;
                    }
                }
            }
        }
        //don't count the cube under test
        if (universe[x][y][z]) {
            count--;
        }

        return count;
    }


    public static void partTwo() throws IOException {

    }

}
