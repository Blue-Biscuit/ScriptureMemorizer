package net.ahuffman.gameplay;

import net.ahuffman.memorization.MemorizationGame;

/**
 * A simple "scoring" class which keeps track of play data about a certain memorization game.
 */
public class MemorizationGamePlayer {

    /**
     * Constructor.
     * @param game The game to track.
     */
    public MemorizationGamePlayer(MemorizationGame game) {
        if (game == null) {
            throw new NullPointerException("Argument 'game' cannot be null.");
        }

        _game = game;
    }

    /**
     * Advances to the next turn, based upon the user's input.
     * @param input The user's answer.
     * @return Whether this turn was a success or a fail.
     */
    public boolean next(String input) {
        boolean result = _game.matches(input) == MemorizationGame.A_MATCH;

        if (result) {
            successes++;
            _game.next();
        }
        else {
            fails++;
        }

        turn++;
        return result;
    }

    /**
     * Gets the number of turns which have been taken.
     * @return the number of turns.
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Gets the number of successes the user has had.
     * @return the number of successes.
     */
    public int getSuccesses() {
        return successes;
    }

    /**
     * Gets the number of fails the user has had.
     * @return the number of fails.
     */
    public int getFails() {
        return fails;
    }


    private final MemorizationGame _game;
    private int turn = 0;
    private int successes = 0;
    private int fails = 0;
}
