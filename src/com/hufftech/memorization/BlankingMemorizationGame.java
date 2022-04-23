package com.hufftech.memorization;

import com.hufftech.passage.NoWordFoundException;
import com.hufftech.passage.Passage;

import java.util.Arrays;
import java.util.Random;

public class BlankingMemorizationGame extends MemorizationGame {

    public BlankingMemorizationGame(Passage p) {
        super(p);

        _blanks = new boolean[p.numWords()];
        _rng = new Random();
    }

    @Override
    public void next() {
        // If the value was fully blanked last time, then the game is over.
        if (fullyBlanked()) {
            _done = true;
        }

        // Otherwise, blank a random word.
        else {
            int blankIndex = _rng.nextInt(_blanks.length);

            while (blanked(blankIndex)) {
                blankIndex = _rng.nextInt(_blanks.length);
            }

            blank(blankIndex);
        }

    }

    @Override
    public void reset() {

        Arrays.fill(_blanks, false);

    }

    /**
     * Blanks the word at the index
     * @param index the index to blank at
     * @throws NoWordFoundException if the index is out of bounds
     */
    public void blank(int index) throws NoWordFoundException {
        if (index >= _blanks.length || index < 0) {
            throw new NoWordFoundException("Word number " + index + " is out of bounds for passage size " + _passage.numWords() + ".");
        }

        if (!_blanks[index]) {
            _blanks[index] = true;
            _numBlanks++;
        }

    }

    /**
     * True if the given word has been blanked.
     * @param index The index at which to check.
     * @throws NoWordFoundException If the index is out of bounds.
     */
    public boolean blanked(int index) throws NoWordFoundException {
        if (index >= _blanks.length || index < 0) {
            throw new NoWordFoundException("Word number " + index + " is out of bounds for passage size " + _passage.numWords() + ".");
        }

        return _blanks[index];
    }

    /**
     * True if the passage has been fully blanked out.
     * @return True if the passage has been fully blanked.
     */
    public boolean fullyBlanked() {
        return _numBlanks == _blanks.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_passage.getTitle()).append('\n');

        for (int i = 0; i < _blanks.length - 1; i++) {

            if (_blanks[i]) {
                char[] blank = new char[_passage.wordAt(i).length()];
                Arrays.fill(blank, '_');
                sb.append(blank);
                sb.append(' ');
            }
            else {
                sb.append(_passage.wordAt(i)).append(" ");
            }

        }

        if (_blanks[_blanks.length - 1]) {
            char[] blank = new char[_passage.wordAt(_blanks.length - 1).length()];
            Arrays.fill(blank, '_');
            sb.append(blank);
        }
        else {
            sb.append(_passage.wordAt(_blanks.length - 1));
        }

        return sb.toString();
    }

    @Override
    public boolean done() {
        return _done;
    }

    private final boolean[] _blanks;
    private final Random _rng;
    private int _numBlanks = 0;
    private boolean _done = false;
}
