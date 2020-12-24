package adventofcode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 {

    private static int currentPart = 0;

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //282
        boolean runPart2 = true; //3445
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
        List<String> input = InputUtil.readFileAsStringList("day24/input.txt");
        List<List<String>> directions = input.stream()
                .map(line -> new ArrayList<>(Arrays.asList(line.split(""))))
                .collect(Collectors.toList());
        directions.forEach(line -> {
            while (line.contains("n") || line.contains("s")) {
                int n = line.indexOf("n");
                if (n > -1) {
                    line.set(n, "n" + line.get(n + 1));
                    line.remove(n + 1);
                }
                int s = line.indexOf("s");
                if (s > -1) {
                    line.set(s, "s" + line.get(s + 1));
                    line.remove(s + 1);
                }

            }
        });

        Map<List<Float>, Boolean> tileMap = new HashMap<>();
        directions.forEach(tilePath -> flipTile(tilePath, tileMap));
        long count = tileMap.values().stream().filter(b -> b).count();

        System.out.println("Part 1 Answer: " + count);
    }

    private static void flipTile(List<String> tilePath, Map<List<Float>, Boolean> tileMap) {
        float x = 0f;
        float y = 0f;
        for (String direction : tilePath) {
            switch (direction) {
                case "ne":
                    y += 1;
                    x += 0.5f;
                    break;
                case "e":
                    x += 1f;
                    break;
                case "nw":
                    y += 1;
                    x += -0.5f;
                    break;
                case "w":
                    x += -1f;
                    break;
                case "sw":
                    x += -0.5f;
                    y += -1;
                    break;
                case "se":
                    x += 0.5f;
                    y += -1;
                    break;
                default:
                    throw new RuntimeException("logic error");
            }
        }
        List<Float> key = key(x, y);
        tileMap.put(key, !tileMap.getOrDefault(key, false));
    }


    private static List<Float> key(Float... coordinates) {
        return Arrays.asList(coordinates);
    }

    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day24/input.txt");
        List<List<String>> directions = input.stream()
                .map(line -> new ArrayList<>(Arrays.asList(line.split(""))))
                .collect(Collectors.toList());
        directions.forEach(line -> {
            while (line.contains("n") || line.contains("s")) {
                //n or s is always follow by e or w, combine them into 1 string
                int nIndex = line.indexOf("n");
                if (nIndex > -1) {
                    line.set(nIndex, "n" + line.get(nIndex + 1));
                    line.remove(nIndex + 1);
                }
                int sIndex = line.indexOf("s");
                if (sIndex > -1) {
                    line.set(sIndex, "s" + line.get(sIndex + 1));
                    line.remove(sIndex + 1);
                }
            }
        });

        Map<List<Float>, Boolean> tileMap = new HashMap<>();
        for (List<String> tilePath : directions) {
            flipTile(tilePath, tileMap);
        }

        for (int i = 0; i < 100; i++) {
            long count = tileMap.values().stream().filter(b -> b).count();

            Map<List<Float>, Boolean> nextState = new HashMap<>(tileMap);

            //we only care about tiles that are black, or adjacent to black
            Set<List<Float>> tilesWeCareAbout = tileMap.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .flatMap(tile -> getAdjacent(tile, true).stream())
                    .collect(Collectors.toSet());

            for (List<Float> tile : tilesWeCareAbout) {
                Set<List<Float>> adjacent = getAdjacent(tile, false);
                Map<List<Float>, Boolean> finalTileMap = tileMap; //stupid java requiring final or effectively final variables to be used in lambdas
                long adjacentBlack = adjacent.stream().filter(t -> finalTileMap.getOrDefault(t, false)).count();

                //if the tile is in the map with value True, then it is black
                if (tileMap.getOrDefault(tile, false)) {
                    //Any black tile with zero or more than 2 black tiles immediately adjacent to it
                    if (adjacentBlack == 0 || adjacentBlack > 2) {
                        // is flipped to white.
                        nextState.remove(tile);
                    }
                } else {
                    //Any white tile with exactly 2 black tiles immediately adjacent to it
                    if (adjacentBlack == 2) {
                        // is flipped to black.
                        nextState.put(tile, true);
                    }
                }
            }
            tileMap = nextState;
        }

        long count = tileMap.values().stream().filter(b -> b).count();

        System.out.println("Part 2 Answer: " + count);
    }

    private static Set<List<Float>> getAdjacent(List<Float> tile, boolean includeSelf) {
        Set<List<Float>> adjacentTiles = new HashSet<>();
        float x = tile.get(0);
        float y = tile.get(1);

        adjacentTiles.add(key(x + 1f, y));
        adjacentTiles.add(key(x + .5f, y + 1f));
        adjacentTiles.add(key(x + .5f, y - 1f));
        adjacentTiles.add(key(x - 1f, y));
        adjacentTiles.add(key(x - .5f, y + 1f));
        adjacentTiles.add(key(x - .5f, y - 1f));

        if (includeSelf) {
            adjacentTiles.add(tile);
        }
        return adjacentTiles;
    }
}
