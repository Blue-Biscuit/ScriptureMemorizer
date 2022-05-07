package net.ahuffman.cli;

import net.ahuffman.passage.Passage;

import java.util.ArrayList;

/**
 * Simple data structure to hold a list of passages.
 */
public class PassagesList {
    public PassagesList() {
        _passages = new ArrayList<>();
    }

    public void add(Passage p) {
        _passages.add(p);
    }

    public Passage get(String name) {
        for (Passage e : _passages) {
            if (e.getTitle().equals(name)) {
                return e;
            }
        }

        return null;
    }

    private final ArrayList<Passage> _passages;
}
