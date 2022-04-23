package com.hufftech.passage;


import java.util.Arrays;
import java.util.Iterator;

/**
 * A passage implementation using a simple String.
 */
public class StringPassage extends Passage {

    public StringPassage(String title, String text) throws NullPointerException {
        super(title);

        if (text == null) {
            throw new NullPointerException("Argument 'text' cannot be null.");
        }

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
    public void saveToFile(String filepath) throws SavePassageException {

    }

    @Override
    public void loadFromFile(String filepath) throws LoadPassageException {

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
