package net.ahuffman.memorization;

import net.ahuffman.passage.Passage;

/**
 * A simple free recall memorization game. The game ends on the first
 * next() call.
 */
public class FreeRecallMemorizationGame extends MemorizationGame {
    public FreeRecallMemorizationGame(Passage p) {
        super(p);
    }

    @Override
    public void next() {
        _done = true;
    }

    @Override
    public void reset() {
        _done = false;
    }

    @Override
    public String toString() {
        return _passage.getTitle();
    }

    @Override
    public boolean done() {
        return _done;
    }

    private boolean _done = false;
}
