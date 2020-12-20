package adventofcode;

import adventofcode.vo.Tile;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
        List<String> input = InputUtil.readFileAsStringList("day20/input.txt", "\n\n");

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
        List<ImmutablePair<Integer, Integer>> countOfEdgeConnections = new ArrayList<>();

        allTilesMap.keySet().forEach(tileNum -> {
            Integer pairPossibilities = getNumOfEdgePairs(tileNum, allTilesMap);
            countOfEdgeConnections.add(new ImmutablePair<>(tileNum, pairPossibilities));
        });

        Long answer = countOfEdgeConnections.stream().filter(p -> p.getRight() == 2).map(p -> p.getLeft().longValue()).reduce(1L, (a, b) -> a * b);

        System.out.println("Part 1 Answer: " + answer);
    }

    private static int getNumOfEdgePairs(Integer tileNum, Map<Integer, Map<List<Integer>, Boolean>> allTilesMap) {
        AtomicInteger counter = new AtomicInteger();
        Map<List<Integer>, Boolean> tileUnderTest = allTilesMap.get(tileNum);

        List<Boolean> topRow = topRowCoordinates.stream().map(tileUnderTest::get).collect(Collectors.toList());
        List<Boolean> topRowReverse = new ArrayList<>(topRow);
        Collections.reverse(topRowReverse);

        List<Boolean> bottomRow = bottomRowCoordinates.stream().map(tileUnderTest::get).collect(Collectors.toList());
        List<Boolean> bottomRowReverse = new ArrayList<>(bottomRow);
        Collections.reverse(bottomRowReverse);

        List<Boolean> leftCol = leftColCoordinates.stream().map(tileUnderTest::get).collect(Collectors.toList());
        List<Boolean> leftColReverse = new ArrayList<>(leftCol);
        Collections.reverse(leftColReverse);

        List<Boolean> rightCol = rightColCoordinates.stream().map(tileUnderTest::get).collect(Collectors.toList());
        List<Boolean> rightColReverse = new ArrayList<>(rightCol);
        Collections.reverse(rightColReverse);

        for (Integer key : allTilesMap.keySet()) {
            if (key.equals(tileNum)) {
                continue;
            }
            Map<List<Integer>, Boolean> nextTile = allTilesMap.get(key);
            List<Boolean> nextTileTop = topRowCoordinates.stream().map(nextTile::get).collect(Collectors.toList());
            List<Boolean> nextTileBottom = bottomRowCoordinates.stream().map(nextTile::get).collect(Collectors.toList());
            List<Boolean> nextTileLeft = leftColCoordinates.stream().map(nextTile::get).collect(Collectors.toList());
            List<Boolean> nextTileRight = rightColCoordinates.stream().map(nextTile::get).collect(Collectors.toList());

            List<List<Boolean>> nextTileEdges = Arrays.asList(nextTileTop, nextTileBottom, nextTileLeft, nextTileRight);
            nextTileEdges.forEach(edge -> {
                if (edge.equals(topRow) || edge.equals(topRowReverse)) {
                    counter.getAndIncrement();
                }
                if (edge.equals(bottomRow) || edge.equals(bottomRowReverse)) {
                    counter.getAndIncrement();
                }
                if (edge.equals(leftCol) || edge.equals(leftColReverse)) {
                    counter.getAndIncrement();
                }
                if (edge.equals(rightCol) || edge.equals(rightColReverse)) {
                    counter.getAndIncrement();
                }
            });
        }

        return counter.get();
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
        List<String> input = InputUtil.readFileAsStringList("day20/input.txt", "\n\n");
        List<Tile> tiles;
        //aside from the input parsing, most of this answer was copy pasted
        // from https://repl.it/@BrianMoore1/AdventOfCode2020#Days/Days%2015-21/Day20.java
        // 2d rotational puzzle too annoying to solve myself

        Pattern digitPattern = Pattern.compile("\\d+");
        tiles = input.stream()
                .map(tile -> {
                    List<String> tileLines = Arrays.asList(tile.split("\n"));
                    Matcher m = digitPattern.matcher(tileLines.get(0));
                    m.find();
                    Integer tileNum = Integer.valueOf(m.group());

                    List<String> tileData = tileLines.subList(1, tileLines.size());
                    char[][] tileChars = new char[tileData.size()][tileData.size()];
                    for (int i = 0; i < tileData.size(); i++) {
                        for (int j = 0; j < tileData.size(); j++) {
                            tileChars[i][j] = tileData.get(i).charAt(j);
                        }
                    }

                    return new Tile(tileNum, tileChars);
                }).collect(Collectors.toList());


        int size = (int) Math.sqrt(tiles.size()); // length and width of our square puzzle
        Tile[][] puzzle = new Tile[size][size]; // a 2D array of Tiles we will use to order our solved puzzle

        // place the top left corner piece
        // hardcoding tileId from part 1 because lazy
        Tile corner = tiles.stream().filter(t -> t.getTileNumber() == 3083).findAny().get();
        puzzle[0][0] = corner;
        tiles.remove(corner);

        // ensure the corner is oriented such that top and left sides do not match any
        // other edges
        int e1m = 0; // count how many other puzzle pieces edge 1 (top) matches
        int e2m = 0; // count how many other puzzle pieces edge 2 (right) matches
        int e3m = 0; // count how many other puzzle pieces edge 3 (bottom) matches
        int e4m = 0; // count how many other puzzle pieces edge 4 (left) matches
        for (int i = 0; i < tiles.size(); i++) {
            Tile tlc = puzzle[0][0]; // top left corner piece
            Tile other = tiles.get(i); // all other puzzle pieces
            if (tlc.getE1().equals(other.getE1()))
                e1m++;
            else if (tlc.getE1().equals(other.getE2()))
                e1m++;
            else if (tlc.getE1().equals(other.getE3()))
                e1m++;
            else if (tlc.getE1().equals(other.getE4()))
                e1m++;

            else if (tlc.getE1().equals(rev(other.getE1())))
                e2m++;
            else if (tlc.getE1().equals(rev(other.getE2())))
                e2m++;
            else if (tlc.getE1().equals(rev(other.getE3())))
                e2m++;
            else if (tlc.getE1().equals(rev(other.getE4())))
                e2m++;

            if (tlc.getE2().equals(other.getE1()))
                e2m++;
            else if (tlc.getE2().equals(other.getE2()))
                e2m++;
            else if (tlc.getE2().equals(other.getE3()))
                e2m++;
            else if (tlc.getE2().equals(other.getE4()))
                e2m++;

            else if (tlc.getE2().equals(rev(other.getE1())))
                e2m++;
            else if (tlc.getE2().equals(rev(other.getE2())))
                e2m++;
            else if (tlc.getE2().equals(rev(other.getE3())))
                e2m++;
            else if (tlc.getE2().equals(rev(other.getE4())))
                e2m++;

            if (tlc.getE3().equals(other.getE1()))
                e3m++;
            else if (tlc.getE3().equals(other.getE2()))
                e3m++;
            else if (tlc.getE3().equals(other.getE3()))
                e3m++;
            else if (tlc.getE3().equals(other.getE4()))
                e3m++;

            else if (tlc.getE3().equals(rev(other.getE1())))
                e3m++;
            else if (tlc.getE3().equals(rev(other.getE2())))
                e3m++;
            else if (tlc.getE3().equals(rev(other.getE3())))
                e3m++;
            else if (tlc.getE3().equals(rev(other.getE4())))
                e3m++;

            if (tlc.getE4().equals(other.getE1()))
                e4m++;
            else if (tlc.getE4().equals(other.getE2()))
                e4m++;
            else if (tlc.getE4().equals(other.getE3()))
                e4m++;
            else if (tlc.getE4().equals(other.getE4()))
                e4m++;

            else if (tlc.getE4().equals(rev(other.getE1())))
                e4m++;
            else if (tlc.getE4().equals(rev(other.getE2())))
                e4m++;
            else if (tlc.getE4().equals(rev(other.getE3())))
                e4m++;
            else if (tlc.getE4().equals(rev(other.getE4())))
                e4m++;
        }

        // rotate top left corner piece until the two sides that don't match are on the
        // outside
        if (e1m == 0 && e2m == 0) // top&right -> rotate CW 3 times
            puzzle[0][0].rotateClockwise(3);
        else if (e2m == 0 && e3m == 0) // right&bottom -> rotate CW 2 times
            puzzle[0][0].rotateClockwise(2);
        else if (e3m == 0 && e4m == 0) // bottom&left -> rotate CW 1 time
            puzzle[0][0].rotateClockwise(1);
        // if e1m == 0 && e4m == 0, it's oriented correctly already

        // Traverse puzzle array and find pieces that fit.
        // When a piece fits, be sure to flip/rotate it before placing in array.
        // If we are in row 0, we must match pieces with the puzzle piece to the left
        // (because nothing is above)
        // If we are in any other row, we should match pieces with the puzzle piece
        // above (because there is potentially nothing to our left)
        for (int r = 0; r < puzzle.length; r++) {
            for (int c = 0; c < puzzle[0].length; c++) {
                if (puzzle[r][c] == null) { // so we skip top left corner
                    if (r == 0) {
                        // match with right side of tile to your left
                        String edgeToMatch = puzzle[r][c - 1].getE2();
                        int i = 0;
                        boolean pieceFound = false;
                        while (!pieceFound) {
                            Tile t = tiles.get(i);
                            if (t.getE1().equals(edgeToMatch)) {
                                t.flipHorizontal();
                                t.rotateClockwise(3);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (t.getE2().equals(edgeToMatch)) {
                                t.flipHorizontal();
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (t.getE3().equals(edgeToMatch)) {
                                t.rotateClockwise(1);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (t.getE4().equals(edgeToMatch)) {
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (rev(t.getE1()).equals(edgeToMatch)) {
                                t.rotateClockwise(3);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (rev(t.getE2()).equals(edgeToMatch)) {
                                t.rotateClockwise(2);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (rev(t.getE3()).equals(edgeToMatch)) {
                                t.flipHorizontal();
                                t.rotateClockwise(1);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (rev(t.getE4()).equals(edgeToMatch)) {
                                t.flipHorizontal();
                                t.rotateClockwise(2);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            }
                            i++;
                        }
                    } else {
                        // match with bottom of tile above you
                        String edgeToMatch = puzzle[r - 1][c].getE3();
                        int i = 0;
                        boolean pieceFound = false;
                        while (!pieceFound) {
                            Tile t = tiles.get(i);
                            if (t.getE1().equals(edgeToMatch)) {
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (t.getE2().equals(edgeToMatch)) {
                                t.rotateClockwise(3);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (t.getE3().equals(edgeToMatch)) {
                                t.flipHorizontal();
                                t.rotateClockwise(2);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (t.getE4().equals(edgeToMatch)) {
                                t.rotateClockwise(1);
                                t.flipHorizontal();
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (rev(t.getE1()).equals(edgeToMatch)) {
                                t.flipHorizontal();
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (rev(t.getE2()).equals(edgeToMatch)) {
                                t.flipHorizontal();
                                t.rotateClockwise(1);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (rev(t.getE3()).equals(edgeToMatch)) {
                                t.rotateClockwise(2);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            } else if (rev(t.getE4()).equals(edgeToMatch)) {
                                t.rotateClockwise(1);
                                puzzle[r][c] = t;
                                tiles.remove(i);
                                pieceFound = true;
                            }
                            i++;
                        }
                    }
                }
            }
        }

        // Traverse your 2D array of solved puzzle pieces and use each piece to
        // create a 2D char array containing all chars from puzzle pieces without edges
        char[][] image = new char[puzzle.length * 8][puzzle.length * 8];
        int ri = 0; // row of image
        int ci = 0; // col of image
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                Tile t = puzzle[i][j]; // current tile we are copying into image
                char[][] g = t.getGrid();
                // using the coordinates of our puzzle piece, calculate where to begin placing
                // chars in image array
                ri = i * 8;
                ci = j * 8;
                for (int r = 1; r <= g.length - 2; r++) {
                    for (int c = 1; c <= g[0].length - 2; c++) {
                        image[ri][ci] = g[r][c];
                        ci++;
                    }
                    ri++; // go down one row
                    ci = j * 8; // go back to starting column
                }
            }
        }

        // I'm making a Tile out of the puzzle so I can use my rotate/flip methods to
        // help find monsters. I'm afraid monsters may be oriented in all 8 possible ways.
        // findMonsters method will traverse grid in search of seaMonsters and replace
        // '#' with 'O' when found.
        Tile completedPuzzle = new Tile(0, image);
        completedPuzzle.findMonsters(); // check normal oriented puzzle for monsters
        completedPuzzle.rotateClockwise(1); // rotated once CW
        completedPuzzle.findMonsters(); // re-check
        completedPuzzle.rotateClockwise(1); // upside down puzzle
        completedPuzzle.findMonsters(); // re-check
        completedPuzzle.rotateClockwise(1); // rotated three times CW
        completedPuzzle.findMonsters(); // re-check
        completedPuzzle.rotateClockwise(1); // back to normal
        completedPuzzle.flipHorizontal(); // flip it
        completedPuzzle.findMonsters(); // check reversed puzzle
        completedPuzzle.rotateClockwise(1); // rotate once CW
        completedPuzzle.findMonsters(); // re-check
        completedPuzzle.rotateClockwise(1); // upside down and reversed
        completedPuzzle.findMonsters(); // re-check
        completedPuzzle.rotateClockwise(1); // rotated three times CW
        completedPuzzle.findMonsters(); // re-check

        // count how many chars in completedPuzzle are still '#'
        int count = 0;
        char[][] g = completedPuzzle.getGrid();
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++)
                if (g[i][j] == '#')
                    count++;

        System.out.println("Part 2 Answer: " + count);
    }


    private static String rev(String s) {
        return new StringBuilder(s).reverse().toString();
    }

}
