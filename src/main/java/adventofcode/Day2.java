package adventofcode;

import java.io.IOException;
import java.util.List;

public class Day2 {

    static int p1valid = 0;
    static int p2valid = 0;

    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    //1-3 a: abcde
    private static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day2/input.txt");
        boolean done = false;

        input.stream().forEach(line -> {
            String[] parts = line.split(" ");
            String[] params = parts[0].split("-");
            int min = Integer.parseInt(params[0]);
            int max = Integer.parseInt(params[1]);
            char charr = parts[1].charAt(0);
            String pw = parts[2];
            long count = pw.chars().filter(ch -> ch == charr).count();
            if (count >= min && count <= max) {
                p1valid++;
            }
        });

        if (p1valid == 0) {
            System.out.println("Part 1 failed");
        } else {
            System.out.println("Part 1: " + p1valid);
        }
    }

    private static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day2/input.txt");
        boolean done = false;

        input.stream().forEach(line -> {
            String[] parts = line.split(" ");
            String[] params = parts[0].split("-");
            int pos1 = Integer.parseInt(params[0]) - 1;
            int pos2 = Integer.parseInt(params[1]) - 1;
            char charr = parts[1].charAt(0);
            String pw = parts[2];
            if ((pw.charAt(pos1) == charr) ^ (pw.charAt(pos2) == charr)) {
                p2valid++;
            }
        });

        if (p2valid == 0) {
            System.out.println("Part 2 failed");
        } else {
            System.out.println("Part 2: " + p2valid);
        }
    }

}
