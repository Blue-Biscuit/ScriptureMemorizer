package net.ahuffman.memorization;

import net.ahuffman.passage.Passage;

/**
 * An abstract memorization game class
 */
public abstract class MemorizationGame {
    /**
     * The value match() returns on a match.
     */
    public static final int A_MATCH = -1;

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
     * @return A_MATCH if it's a match, or the location of the first error, otherwise.
     */
    public int matches(String input) {
        if (input == null) {
            throw new NullPointerException("Input cannot be null.");
        }

        String passage = _passage.fullText();
        boolean matches = _passage.fullText().equals(input.trim());

        if (matches) {
            return A_MATCH;
        }
        else {
            int errLocation;

            for (errLocation = 0; errLocation < input.length() && errLocation < passage.length(); errLocation++) {
                if (passage.charAt(errLocation) != input.charAt(errLocation)) {
                    break;
                }
            }

            return errLocation;
        }

    }

    protected Passage _passage;
}
