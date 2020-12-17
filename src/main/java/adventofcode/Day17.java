package adventofcode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day17 {

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //218
        boolean runPart2 = true; //1908
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

    private static List<Integer> key(Integer... coordinates) {
        return Arrays.asList(coordinates);
    }

    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day17/input.txt");
        Map<List<Integer>, Boolean> universeMap = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            List<Boolean> line = Arrays.stream(input.get(i).split("")).map("#"::equals).collect(Collectors.toList());
            for (int j = 0; j < line.size(); j++) {
                if (line.get(j)) {
                    universeMap.put(key(j, i, 0), true);
                }
            }
        }

        int iterationsForPart1 = 6;
        for (int cycle = 0; cycle < iterationsForPart1; cycle++) {
            //update nextState using logic on currentState
            HashMap<List<Integer>, Boolean> nextUniverseState = new HashMap<>(universeMap);
            Set<List<Integer>> activeCubes = universeMap.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toSet());
            Set<List<Integer>> cubesToConsider = new HashSet<>(activeCubes);
            activeCubes.forEach(activeCube -> cubesToConsider.addAll(getAdjacentCubesIndexes(activeCube.get(0), activeCube.get(1), activeCube.get(2))));

            for (List<Integer> cube : cubesToConsider) {
                //If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active.
                // Otherwise, the cube becomes inactive.
                if (universeMap.getOrDefault(cube, false)) {
                    int activeNeighbors = countActiveNeighbors(universeMap, cube.get(0), cube.get(1), cube.get(2));
                    if (activeNeighbors != 2 && activeNeighbors != 3) {
                        //nextUniverseState.put(cube, false);
                        nextUniverseState.remove(cube);
                    }
                } else {
                    //If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active.
                    // Otherwise, the cube remains inactive.
                    int activeNeighbors = countActiveNeighbors(universeMap, cube.get(0), cube.get(1), cube.get(2));
                    if (activeNeighbors == 3) {
                        nextUniverseState.put(cube, true);
                    }

                }
            }

            universeMap = nextUniverseState;
        }

        //How many cubes are left in the active state after the sixth cycle?
        System.out.println("Part 1 Answer: " + universeMap.values().stream().filter(Boolean::booleanValue).count());
    }

    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day17/input.txt");
        Map<List<Integer>, Boolean> universeMap = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            List<Boolean> line = Arrays.stream(input.get(i).split("")).map("#"::equals).collect(Collectors.toList());
            for (int j = 0; j < line.size(); j++) {
                if (line.get(j)) {
                    //4D this time
                    universeMap.put(key(j, i, 0, 0), true);
                }
            }
        }

        int iterationsForPart2 = 6;
        for (int cycle = 0; cycle < iterationsForPart2; cycle++) {
            //update nextState using logic on currentState
            HashMap<List<Integer>, Boolean> nextUniverseState = new HashMap<>(universeMap);
            Set<List<Integer>> activeCubes = universeMap.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toSet());
            Set<List<Integer>> cubesToConsider = new HashSet<>(activeCubes);
            activeCubes.forEach(activeCube -> cubesToConsider.addAll(getAdjacentCubesIndexes(activeCube.get(0), activeCube.get(1), activeCube.get(2), activeCube.get(3))));

            for (List<Integer> cube : cubesToConsider) {
                //If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active.
                // Otherwise, the cube becomes inactive.
                if (universeMap.getOrDefault(cube, false)) {
                    int activeNeighbors = countActiveNeighbors(universeMap, cube.get(0), cube.get(1), cube.get(2), cube.get(3));
                    if (activeNeighbors != 2 && activeNeighbors != 3) {
                        //nextUniverseState.put(cube, false);
                        nextUniverseState.remove(cube);
                    }
                } else {
                    //If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active.
                    // Otherwise, the cube remains inactive.
                    int activeNeighbors = countActiveNeighbors(universeMap, cube.get(0), cube.get(1), cube.get(2), cube.get(3));
                    if (activeNeighbors == 3) {
                        nextUniverseState.put(cube, true);
                    }

                }
            }

            universeMap = nextUniverseState;
        }

        //How many cubes are left in the active state after the sixth cycle?
        System.out.println("Part 2 Answer: " + universeMap.values().stream().filter(Boolean::booleanValue).count());
    }

    private static List<List<Integer>> getAdjacentCubesIndexes(Integer... coordinate) {
        List<List<Integer>> adjacentCoordinates = new ArrayList<>();
        List<Integer> baseCoord = new ArrayList<>();
        adjacentCoordinates.add(baseCoord);

        for (int i = 0; i < coordinate.length; i++) {
            int finalI = i;
            adjacentCoordinates = adjacentCoordinates.stream()
                    .flatMap(coord1 -> {
                        List<Integer> coord2 = new ArrayList<>(coord1);
                        List<Integer> coord3 = new ArrayList<>(coord1);
                        coord1.add(coordinate[finalI] - 1);
                        coord2.add(coordinate[finalI]);
                        coord3.add(coordinate[finalI] + 1);
                        return Stream.of(coord1, coord2, coord3);
                    }).collect(Collectors.toList());
        }
        adjacentCoordinates.remove(Arrays.asList(coordinate));
        return adjacentCoordinates;
    }

    private static int countActiveNeighbors(Map<List<Integer>, Boolean> universe, Integer... coordinate) {
        int count = 0;
        List<List<Integer>> neighbors = getAdjacentCubesIndexes(coordinate);
        for (List<Integer> neighbor : neighbors) {
            if (universe.getOrDefault(neighbor, false)) {
                count++;
            }
        }
        return count;
    }

}
