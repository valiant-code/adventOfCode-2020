package adventofcode;

import adventofcode.vo.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day22 {

    private static int currentPart = 0;

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


    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day22/input.txt", "\n\n");
        List<Integer> player1 = new ArrayList<>(Arrays.asList(input.get(0).split("\n")).subList(1, input.get(0).split("\n").length).stream().map(Integer::valueOf).collect(Collectors.toList()));
        List<Integer> player2 = new ArrayList<>(Arrays.asList(input.get(1).split("\n")).subList(1, input.get(0).split("\n").length).stream().map(Integer::valueOf).collect(Collectors.toList()));

        while (player1.size() > 0 && player2.size() > 0) {
            Integer p1Card = player1.remove(0);
            Integer p2Card = player2.remove(0);

            if (p1Card > p2Card) {
                player1.add(p1Card);
                player1.add(p2Card);
            } else {
                player2.add(p2Card);
                player2.add(p1Card);
            }
        }
        List<Integer> finalDeck = new ArrayList<>(player1);
        finalDeck.addAll(player2);
        Collections.reverse(finalDeck);
        int acc = 0;
        for (int i = 1; i < finalDeck.size() + 1; i++) {
            acc += finalDeck.get(i - 1) * i;
        }

        System.out.println("Part 1 Answer: " + acc);
    }


    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day22/input.txt", "\n\n");
        List<Integer> player1 = new ArrayList<>(Arrays.asList(input.get(0).split("\n")).subList(1, input.get(0).split("\n").length).stream().map(Integer::valueOf).collect(Collectors.toList()));
        List<Integer> player2 = new ArrayList<>(Arrays.asList(input.get(1).split("\n")).subList(1, input.get(0).split("\n").length).stream().map(Integer::valueOf).collect(Collectors.toList()));

        Game recursiveCombat = new Game(player1, player2);
        int winner = recursiveCombat.play();
        List<Integer> finalDeck = new ArrayList<>();
        if (winner == 1) {
            finalDeck.addAll(recursiveCombat.getP1Deck());
        } else {
            finalDeck.addAll(recursiveCombat.getP2Deck());
        }
        Collections.reverse(finalDeck);
        int acc = 0;
        for (int i = 1; i < finalDeck.size() + 1; i++) {
            acc += finalDeck.get(i - 1) * i;
        }

        System.out.println("Part 2 Answer: " + acc);
    }
}
