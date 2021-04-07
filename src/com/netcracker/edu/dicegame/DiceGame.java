package com.netcracker.edu.dicegame;

import java.util.*;

/**
 * Simulates game of dice with numOfPlayers and numOfDice.
 * The winner is the one with the largest amount of points.
 * Whoever won is the first to roll in the next round.
 * The game is on up to 7 wins.
 *
 * You can set names of players by yourself
 * or they will be equal to Player0, Player1 and etc.
 *
 * To simulate the game after you created it, use playGame() method.
 * Result of this simulation will be printed to standard output.
 */
public class DiceGame {
    static final int MAX_VALUE_ON_DICE = 6;
    static final int NUM_OF_WINS = 7;

    /**
     * Players are stored in LinkedList as Pairs<String, Integer>,
     * where String is players name and Integer is its current number of wins.</>
     *
     * numOfPlayers defaults to 2 and numOfDice to 1,
     * as it is minimum conditions to start competitive game.
     */
    private final LinkedList<Pair<String, Integer>> players = new LinkedList<>();
    private int numOfPlayers = 2;
    private int numOfDice = 1;

    /**
     * Constructor with default conditions.
     */
    public DiceGame() {
        setDefaultPlayers();
    }

    /**
     * Constructor specifying number of players and dice.
     * @param numOfPlayers
     * @param numOfDice
     * @throws IllegalArgumentException if params can't be applied to create a competitive game.
     */
    public DiceGame(int numOfPlayers, int numOfDice) throws IllegalArgumentException{
        if (numOfPlayers < 2 || numOfDice < 1) {
            throw new IllegalArgumentException();
        }
        this.numOfPlayers = numOfPlayers;
        this.numOfDice = numOfDice;
        setDefaultPlayers();
    }

    /**
     * Constructor specifying players names.
     * @param numOfPlayers
     * @param numOfDice
     * @param playersArray
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public DiceGame(int numOfPlayers, int numOfDice, String[] playersArray)
            throws IllegalArgumentException, NullPointerException {

        if (numOfPlayers < 2 || numOfDice < 1) {
            throw new IllegalArgumentException();
        }
        if (playersArray == null) {
            throw new NullPointerException();
        }

        this.numOfPlayers = numOfPlayers;
        this.numOfDice = numOfDice;
        setPlayers(playersArray);
    }

    /**
     * Sets players into players list from start position.
     * @param start
     */
    private void setDefaultPlayers(int start) {
        for (int i = start; i < numOfPlayers; ++i) {
            players.add(new Pair<>("Player" + i, 0));
        }
    }

    /**
     * Sets players from begin.
     */
    private void setDefaultPlayers() {
        setDefaultPlayers(0);
    }

    /**
     * Sets players with names from playersArray.
     * @param playersArray
     */
    public void setPlayers(String[] playersArray) {
        for (String s : playersArray) {
            players.add(new Pair<>(s, 0));
        }

        if (playersArray.length < numOfPlayers) {
            setDefaultPlayers(playersArray.length);
        }
    }

    /**
     * Uses Math.random() to create random number.
     * @return random number from 1 to 6.
     */
    private int getRandomDiceNum() {
        return (int) (Math.random() * MAX_VALUE_ON_DICE) + 1;
    }

    /**
     * Simulates throwing dice by player @param:
     * Prints player and number of his points on each dice into System.out.
     *
     * @param player
     * @return sum of points on all dice.
     */
    private int throwDice(Pair<String, Integer> player) {
        int thrownSum = 0;
        int thrownVal;
        System.out.println(player.toString() + " wins throws dice:");

        for (int i = 1; i <= numOfDice; ++i) {
            thrownVal = getRandomDiceNum();
            thrownSum += thrownVal;
            System.out.println("\t on " + i + " gets:" + thrownVal);
        }

        return thrownSum;
    }

    /**
     * All players throw dice in turn until the beginner turn comes up again,
     * winner is that one who throws larger sum of points.
     * If there are few ones, winner is the first who got that sum.
     *
     * @param iterator on player from whom to start the round.
     * @return iterator on winner.
     */
    private ListIterator getWinnerInRound(ListIterator iterator) {
        int beginIndex = iterator.nextIndex();
        int winnerIndex = beginIndex;
        Pair<String, Integer> currentPlayer = (Pair<String, Integer>) iterator.next();
        int thrownSum = throwDice(currentPlayer);
        int maxThrownSum = thrownSum;

        if (!iterator.hasNext()) {
            iterator = players.listIterator();
        }
        currentPlayer = (Pair<String, Integer>) iterator.next();
        iterator.previous();

        while (iterator.nextIndex() != beginIndex) {
            thrownSum = throwDice(currentPlayer);

            if (maxThrownSum < thrownSum) {
                winnerIndex = iterator.nextIndex();
            }

            iterator.next();
            if (!iterator.hasNext()) {
                iterator = players.listIterator();
            }
            currentPlayer = (Pair<String, Integer>) iterator.next();
            iterator.previous();
        }

        iterator = players.listIterator(winnerIndex);
        currentPlayer = (Pair<String, Integer>) iterator.next();
        iterator.previous();
        currentPlayer.setValue(currentPlayer.getValue() + 1);

        return iterator;
    }

    /**
     * Simulates actions that happen in dice game:
     * throwing dice by each players, showing their points and
     * selecting winner in each round and game.
     */
    public void playGame() {
        System.out.println("\n[+][+] Game starts");

        //First round of game.
        ListIterator iterator = players.listIterator();
        iterator = getWinnerInRound(iterator);
        Pair<String, Integer> currentPlayer = (Pair<String, Integer>) iterator.next();
        iterator.previous();
        System.out.println("[+] Winner in round: " +
                currentPlayer.toString() + " wins");

        // Starts the round until one of the players picks up
        // number of wins to become game winner.
        while (currentPlayer.getValue() < NUM_OF_WINS) {
            iterator = getWinnerInRound(iterator);

            currentPlayer = (Pair<String, Integer>) iterator.next();
            iterator.previous();
            System.out.println("[+] Winner in round: " +
                    currentPlayer.toString() + " wins");
        }

        System.out.println("\n[+][+] Game winner: " + currentPlayer.toString());
    }
}

class DiceGameDemo{
    public static void main(String[] args) {
        DiceGame diceGame1 = new DiceGame();
        DiceGame diceGame2 = new DiceGame(3, 2);
        DiceGame diceGame3 = new DiceGame(3, 1,
            new String[]{"John", "Mike"});

        try {
            diceGame1.playGame();
            System.out.println("\n\n\n");

            diceGame2.playGame();
            System.out.println("\n\n\n");

            diceGame3.playGame();
            System.out.println("\n\n\n");
        }
        catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }
}
