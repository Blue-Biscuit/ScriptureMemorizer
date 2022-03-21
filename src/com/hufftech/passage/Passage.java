package com.hufftech.passage;

/**
 * An interface for a passage of text.
 */
public interface Passage extends Iterable<String> {
    /**
     * Gets the word at the given number
     * @param wordNum the word number to get
     * @return the word at the index
     */
    String wordAt(int wordNum) throws NoWordFoundException;

    /**
     * Gets the full text of the passage
     * @return the full text
     */
    String fullText();

    /**
     * The number of words in the passage
     * @return the number of words
     */
    int numWords();

    @Override
    String toString();
}
