package adventofcode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day17 {

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //218
        boolean runPart2 = false; //1908
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
        List<String> input = InputUtil.readFileAsStringList("day17/input.txt");
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
            Set<List<Integer>> activeCubes = universeMap.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toSet());
            Set<List<Integer>> cubesToConsider = new HashSet<>(activeCubes);
            activeCubes.forEach(activeCube -> cubesToConsider.addAll(getAdjacentCubesIndexesIterate(activeCube.get(0), activeCube.get(1), activeCube.get(2))));

            for (List<Integer> cube : cubesToConsider) {
                //If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active.
                // Otherwise, the cube becomes inactive.
                if (universeMap.getOrDefault(cube, false)) {
                    int activeNeighbors = countActiveNeighbors(cube.get(0), cube.get(1), cube.get(2), universeMap);
                    if (activeNeighbors != 2 && activeNeighbors != 3) {
                        //nextUniverseState.put(cube, false);
                        nextUniverseState.remove(cube);
                    }
                } else {
                    //If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active.
                    // Otherwise, the cube remains inactive.
                    int activeNeighbors = countActiveNeighbors(cube.get(0), cube.get(1), cube.get(2), universeMap);
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

    public static List<Integer> key(Integer... coordinates) {
        return Arrays.asList(coordinates);
    }

    public static List<List<Integer>> getAdjacentCubesIndexes(int x, int y, int z) {
        List<List<Integer>> adjacentCoordinates = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    adjacentCoordinates.add(key(x + i, y + j, z + k));
                }
            }
        }
        adjacentCoordinates.remove(key(x, y, z));
        return adjacentCoordinates;
    }

    public static int countActiveNeighbors(int x, int y, int z, Map<List<Integer>, Boolean> universe) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    if (universe.getOrDefault(key(x + i, y + j, z + k), false)) {
                        count++;
                    }
                }
            }
        }
        //don't count the cube under test
        if (universe.getOrDefault(key(x, y, z), false)) {
            count--;
        }

        return count;
    }

    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day17/input.txt");
        Map<List<Integer>, Boolean> universeMap = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            List<Boolean> line = Arrays.stream(input.get(i).split("")).map("#"::equals).collect(Collectors.toList());
            for (int j = 0; j < line.size(); j++) {
                if (line.get(j)) {
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
            activeCubes.forEach(activeCube -> cubesToConsider.addAll(getAdjacentCubesIndexesRecursive(new ArrayList<>(), 0, activeCube.get(0), activeCube.get(1), activeCube.get(2), activeCube.get(3))));

            for (List<Integer> cube : cubesToConsider) {
                //If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active.
                // Otherwise, the cube becomes inactive.
                if (universeMap.getOrDefault(cube, false)) {
                    int activeNeighbors = countActiveNeighbors4D(cube.get(0), cube.get(1), cube.get(2), cube.get(3), universeMap);
                    if (activeNeighbors != 2 && activeNeighbors != 3) {
                        //nextUniverseState.put(cube, false);
                        nextUniverseState.remove(cube);
                    }
                } else {
                    //If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active.
                    // Otherwise, the cube remains inactive.
                    int activeNeighbors = countActiveNeighbors4D(cube.get(0), cube.get(1), cube.get(2), cube.get(3), universeMap);
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

    public static int countActiveNeighbors4D(int x, int y, int z, int w, Map<List<Integer>, Boolean> universe) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int p = -1; p < 2; p++) {
                        if (universe.getOrDefault(key(x + i, y + j, z + k, w + p), false)) {
                            count++;
                        }
                    }
                }
            }
        }
        //don't count the cube under test
        if (universe.getOrDefault(key(x, y, z, w), false)) {
            count--;
        }

        return count;
    }

    public static List<List<Integer>> getAdjacentCubesIndexesRecursive(List<List<Integer>> runningList, Integer currIndex, Integer... coordinate) {
        if (runningList.isEmpty()) {
            runningList.add(new ArrayList<>(Collections.singletonList(coordinate[currIndex] - 1)));
            runningList.add(new ArrayList<>(Collections.singletonList(coordinate[currIndex])));
            runningList.add(new ArrayList<>(Collections.singletonList(coordinate[currIndex] + 1)));
        } else {
            runningList.forEach(coord -> {
                coord.add(currIndex, coordinate[currIndex] - 1);
                coord.add(currIndex, coordinate[currIndex]);
                coord.add(currIndex, coordinate[currIndex] + 1);
            });
        }

        if (currIndex + 1 < coordinate.length) {
            runningList = getAdjacentCubesIndexesRecursive(runningList, currIndex + 1, coordinate);
        } else {
            runningList.remove(coordinate);
        }


        return runningList;
    }

    public static List<List<Integer>> getAdjacentCubesIndexes4D(int x, int y, int z, int w) {
        List<List<Integer>> adjacentCoordinates = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int p = -1; p < 2; p++) {
                        adjacentCoordinates.add(key(x + i, y + j, z + k, w + p));
                    }
                }
            }
        }
        adjacentCoordinates.remove(key(x, y, z, w));
        return adjacentCoordinates;
    }

    public static List<List<Integer>> getAdjacentCubesIndexesIterate(Integer... coordinate) {
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
        adjacentCoordinates.remove(coordinate);
        return adjacentCoordinates;
    }
    //for (int index : xIndexes) {
    //            //loop through our list of current permutations, and replace each X with its two permutations of 0 & 1
    //            permutations = permutations.stream()
    //                    .flatMap(a -> {
    //                        //using flat map to make each array into 2 different ones
    //                        String[] scenario0 = Arrays.stream(a).toArray(String[]::new);
    //                        scenario0[index] = "0";
    //                        String[] scenario1 = a;
    //                        scenario1[index] = "1";
    //                        return Stream.of(scenario0, scenario1);
    //                    })
    //                    .collect(Collectors.toSet());
    //        }

}
