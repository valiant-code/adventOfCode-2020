package adventofcode.vo;

/**
 * A Tile is used to represent a puzzle piece
 *
 * @param grid       The 2D image on the puzzle piece
 * @param e1         A string representation of the top edge of the grid
 *                   (left-right)
 * @param e2         A string representation of the right edge of the grid
 *                   (top-down)
 * @param e3         A string representation of the bottom edge of the grid
 *                   (left-right)
 * @param e4         A string representation of the left edge of the grid
 *                   (top-down)
 * @param tileNumber The id # assigned to this puzzle piece
 * @param isCorner   True if this piece has been designated a corner piece
 */
public class Tile {

    private char[][] grid;
    private String e1, e2, e3, e4;
    private int tileNumber;
    private boolean isCorner;

    public Tile(int n, char[][] g) {
        grid = g;
        tileNumber = n;
        isCorner = false;
        e1 = e2 = e3 = e4 = "";
        for (int c = 0; c < g[0].length; c++)
            e1 += g[0][c];
        for (int r = 0; r < g.length; r++)
            e2 += g[r][g[0].length - 1];
        for (int c = 0; c < g[0].length; c++)
            e3 += g[g.length - 1][c];
        for (int r = 0; r < g.length; r++)
            e4 += g[r][0];
    }

    public void setIsCorner(boolean c) {
        isCorner = c;
    }

    public boolean isCorner() {
        return isCorner;
    }

    public String toString() {
        String str = "Tile " + tileNumber + ":\n";
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                str += grid[r][c];
            }
            str += "\n";
        }
        return str;
    }

    public char[][] getGrid() {
        return grid;
    }

    public String getE1() {
        return e1;
    }

    public String getE2() {
        return e2;
    }

    public String getE3() {
        return e3;
    }

    public String getE4() {
        return e4;
    }

    public int getTileNumber() {
        return tileNumber;
    }

    /**
     * Flips the grid horizontally and reassigns e1, e2, e3 and e4 accordingly
     */
    public void flipHorizontal() {
        char[][] copy = new char[grid.length][grid[0].length];
        for (int r = 0; r < copy.length; r++) {
            for (int c = 0; c < copy[0].length; c++) {
                copy[r][c] = grid[r][grid[0].length - 1 - c];
            }
        }

        String temp = e4;
        e4 = e2;
        e2 = temp;
        e1 = rev(e1);
        e3 = rev(e3);

        grid = copy;
    }

    private String rev(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    /**
     * Rotates the grid clockwise a given number of times
     *
     * @param i The number of times to rotate
     */
    public void rotateClockwise(int i) {
        for (int j = 1; j <= i; j++) {
            char[][] copy = new char[grid.length][grid[0].length];
            for (int r = 0; r < copy.length; r++) {
                for (int c = 0; c < copy[0].length; c++) {
                    copy[r][c] = grid[grid.length - 1 - c][r];
                }
            }

            String temp = e1;
            e1 = rev(e4);
            e4 = e3;
            e3 = rev(e2);
            e2 = temp;

            grid = copy;
        }

    }

    /**
     * Traverses the entire grid in search of sea monsters by looking at 15 specific
     * points. When a sea monster is found, the coordinates of the monster are
     * changed from # -> O.
     */
    public void findMonsters() {
//		time to find sea monsters...
//		.#.#...#.###...#.##.O#..
//		#.O.##.OO#.#.OO.##.OOO##
//		..#O.#O#.O##O..O.#O##.##
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (c + 19 < grid[0].length && r + 2 < grid.length)
                    if (grid[r][c + 18] == '#' && grid[r + 1][c] == '#' && grid[r + 1][c + 5] == '#'
                            && grid[r + 1][c + 6] == '#' && grid[r + 1][c + 11] == '#' && grid[r + 1][c + 12] == '#'
                            && grid[r + 1][c + 17] == '#' && grid[r + 1][c + 18] == '#' && grid[r + 1][c + 19] == '#'
                            && grid[r + 2][c + 1] == '#' && grid[r + 2][c + 4] == '#' && grid[r + 2][c + 7] == '#'
                            && grid[r + 2][c + 10] == '#' && grid[r + 2][c + 13] == '#' && grid[r + 2][c + 16] == '#') {

                        grid[r][c + 18] = 'O';
                        grid[r + 1][c] = 'O';
                        grid[r + 1][c + 5] = 'O';
                        grid[r + 1][c + 6] = 'O';
                        grid[r + 1][c + 11] = 'O';
                        grid[r + 1][c + 12] = 'O';
                        grid[r + 1][c + 17] = 'O';
                        grid[r + 1][c + 18] = 'O';
                        grid[r + 1][c + 19] = 'O';
                        grid[r + 2][c + 1] = 'O';
                        grid[r + 2][c + 4] = 'O';
                        grid[r + 2][c + 7] = 'O';
                        grid[r + 2][c + 10] = 'O';
                        grid[r + 2][c + 13] = 'O';
                        grid[r + 2][c + 16] = 'O';

                    }
            }
        }

    }

}