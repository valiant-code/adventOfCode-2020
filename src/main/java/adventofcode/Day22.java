package adventofcode;

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

        while (player1.size() > 0 && player2.size() > 0) {
            playRecursiveCombat(player1, player2);
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

    //int return represents the player that wins
    private static int playRecursiveCombat(List<Integer> player1, List<Integer> player2) {
        //Before either player deals a card, if there was a previous round in this game that had exactly the same
        // cards in the same order in the same players' decks, the game instantly ends in a win for player 1.
        // Previous rounds from other games are not considered.
        // (This prevents infinite games of Recursive Combat, which everyone agrees is a bad idea.)
        //TODO if p1Deck vs p2Deck has already happened before

        //Otherwise, this round's cards must be in a new configuration;
        // the players begin the round by each drawing the top card of their deck as normal.

        //If both players have at least as many cards remaining in their deck as the value of the card they just drew,
        // the winner of the round is determined by playing a new game of Recursive Combat (see below).

        //Otherwise, at least one player must not have enough cards left in their deck to recurse;
        // the winner of the round is the player with the higher-value card.

        //Note that the winner's card might be the **lower-valued** of the two cards
        // if they won the round due to winning a sub-game

        //note recurse using a copy of list
        return -202;
    }

}
