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
     * The number of words in the passage
     * @return the number of words
     */
    int numWords();
}
