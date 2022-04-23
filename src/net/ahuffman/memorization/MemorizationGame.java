package net.ahuffman.memorization;

import net.ahuffman.passage.Passage;

/**
 * An abstract memorization game class
 */
public abstract class MemorizationGame {
    /**
     * Constructor
     * @param p The passage upon which to play the memorization game
     * @throws NullPointerException if the passage argument is null
     */
    public MemorizationGame(Passage p) throws NullPointerException {
        if (p == null) {
            throw new NullPointerException("Input passage cannot be null");
        }

        _passage = p;
    }

    /**
     * Advances the game to the next "turn"
     */
    public abstract void next();

    /**
     * Resets the game
     */
    public abstract void reset();

    @Override
    public abstract String toString();

    /**
     * True if the game is done.
     * @return True if the game is done.
     */
    public abstract boolean done();

    /**
     * True if the input string matches the passage
     * @param input the input string
     * @return true if the string matches, false otherwise (or if input is null)
     */
    public boolean matches(String input) {
        if (input == null) {
            return false;
        }

        return _passage.fullText().equals(input.trim());
    }

    protected Passage _passage;
}
