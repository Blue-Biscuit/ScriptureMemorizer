package com.hufftech.passage;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A passage implementation using a simple String.
 */
public class StringPassage implements Passage {

    public StringPassage(@NotNull String text) {
        _text = text.trim();

        _words = _text.split("\s");
    }

    @Override
    public String wordAt(int wordNum) throws NoWordFoundException {
        if (wordNum >= _words.length || wordNum < 0) {
            throw new NoWordFoundException("Index " + wordNum + " is out of bound for size " + _words.length + ".");
        }

        return _words[wordNum];
    }

    @Override
    public String fullText() {
        return _text;
    }

    @Override
    public int numWords() {
        return _words.length;
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(_words).iterator();
    }

    @Override
    public String toString() {
        return fullText();
    }

    private final String _text;
    private final String[] _words;
}
