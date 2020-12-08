package adventofcode;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Day8 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        AtomicInteger count = new AtomicInteger();
        List<String> input = InputUtil.readFileAsStringList("day8/input.txt");
        boolean repeat = false;
        int curr = 0;
        Set<Integer> runCommands = new HashSet<>();
        runCommands.add(curr);

        while (!repeat) {
            if (curr >= input.size()) {
                break;
            }
            String c = input.get(curr);
            String command = c.split(" ")[0];
            String val = c.split(" ")[1];

            switch (command) {
                case "jmp":
                    curr += Integer.parseInt(val);
                    break;
                case "acc":
                    count.addAndGet(Integer.parseInt(val));
                case "nop":
                    curr++;
            }
            if (runCommands.contains(curr)) {
                repeat = true;
            } else {
                runCommands.add(curr);
            }
        }

        System.out.println("Part 1: " + count);

    }


    private static void partTwo() throws IOException {
        AtomicInteger count = new AtomicInteger();
        int swap = 0;

        boolean looped = true;
        while (looped) {
            count = new AtomicInteger();
            List<String> input = InputUtil.readFileAsStringList("day8/input.txt");
            for (int i = swap + 1; i < input.size(); i++) {
                String comm = input.get(i).split(" ")[0];
                if (comm.equals("jmp")) {
                    input.set(i, "nop +0");
                    swap = i;
                    break;
                } else if (comm.equals("nop")) {
                    input.set(i, "jmp " + input.get(i).split(" ")[1]);
                    swap = i;
                    break;
                }
            }

            boolean repeat = false;
            int curr = 0;
            Set<Integer> runCommands = new HashSet<>();
            runCommands.add(curr);

            while (!repeat) {
                if (curr >= input.size()) {
                    looped = false;
                    break;
                }
                String c = input.get(curr);
                String command = c.split(" ")[0];
                String val = c.split(" ")[1];

                switch (command) {
                    case "jmp":
                        curr += Integer.parseInt(val);
                        break;
                    case "acc":
                        count.addAndGet(Integer.parseInt(val));
                    case "nop":
                        curr++;
                }
                if (runCommands.contains(curr)) {
                    repeat = true;
                } else {
                    runCommands.add(curr);
                }
            }
        }

        System.out.println("Part 2: " + count);
    }
}
