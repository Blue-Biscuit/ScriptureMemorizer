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

    private final ArrayList<Passage> _passages;
}
