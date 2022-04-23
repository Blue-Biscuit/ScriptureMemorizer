package com.hufftech.passage;

/**
 * An interface for a passage of text.
 */
public abstract class Passage implements Iterable<String> {
    /**
     * Constructor for Passage class.
     * @param title Title field.
     */
    public Passage(String title) {
        _title = title;
    }

    /**
     * Gets the word at the given number
     * @param wordNum the word number to get
     * @return the word at the index
     */
    public abstract String wordAt(int wordNum) throws NoWordFoundException;

    /**
     * Gets the full text of the passage
     * @return the full text
     */
    public abstract String fullText();

    /**
     * The number of words in the passage
     * @return the number of words
     */
    public abstract int numWords();

    /**
     * Gets the title of the passage.
     * @return The title.
     */
    public String getTitle() {
        return _title;
    }

    private final String _title;
}
