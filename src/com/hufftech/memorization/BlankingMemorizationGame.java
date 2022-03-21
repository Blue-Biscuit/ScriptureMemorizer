package com.hufftech.memorization;

import com.hufftech.passage.NoWordFoundException;
import com.hufftech.passage.Passage;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BlankingMemorizationGame extends MemorizationGame {

    public BlankingMemorizationGame(@NotNull Passage p) {
        super(p);

        _blanks = new boolean[p.numWords()];
    }

    @Override
    public void next() {

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

        _blanks[index] = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

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

    private final boolean[] _blanks;
}
