package adventofcode.vo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//recursive combat
public class Game {
    LinkedList<Integer> p1Deck;
    LinkedList<Integer> p2Deck;
    Set<String> gameIterations = new HashSet<>();

    //return represents the winning player, 1 or 2
    public int play() {
        //Before either player deals a card, if there was a previous round in this game that had exactly the same
        // cards in the same order in the same players' decks, the game instantly ends in a win for player 1.
        // Previous rounds from other games are not considered.
        // (This prevents infinite games of Recursive Combat, which everyone agrees is a bad idea.)
        while (p1Deck.size() > 0 && p2Deck.size() > 0) {
            String key = p1Deck.toString() + p2Deck.toString();
            if (gameIterations.contains(key)) {
                return 1; //game ends, with p1 win
            } else {
                gameIterations.add(key);
            }
            //Otherwise, this round's cards must be in a new configuration;
            // the players begin the round by each drawing the top card of their deck as normal.
            Integer p1Card = p1Deck.pop();
            Integer p2Card = p2Deck.pop();
            boolean p1Wins;
            //If both players have at least as many cards remaining in their deck as the value of the card they just drew,
            // the winner of the round is determined by playing a new game of Recursive Combat (see below).
            if (p1Card <= p1Deck.size() && p2Card <= p2Deck.size()) {
                p1Wins = new Game(
                        new LinkedList<>(p1Deck.subList(0, p1Card)),
                        new LinkedList<>(p2Deck.subList(0, p2Card))).play() == 1;
            } else {
                //Otherwise, at least one player must not have enough cards left in their deck to recurse;
                // the winner of the round is the player with the higher-value card.
                p1Wins = p1Card > p2Card;
            }
            if (p1Wins) {
                p1Deck.add(p1Card);
                p1Deck.add(p2Card);
            } else {
                p2Deck.add(p2Card);
                p2Deck.add(p1Card);
            }
        }
        return p1Deck.size() > p2Deck.size() ? 1 : 2;
    }

    public Game(LinkedList<Integer> p1Deck, LinkedList<Integer> p2Deck) {
        this.p1Deck = p1Deck;
        this.p2Deck = p2Deck;
    }

    public List<Integer> getP1Deck() {
        return p1Deck;
    }

    public List<Integer> getP2Deck() {
        return p2Deck;
    }
}
