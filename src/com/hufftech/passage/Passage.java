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

    /**
     * Saves the passage to the filepath. If the filepath doesn't exist, the file
     * is created. If it does, it is overwritten.
     * @param filepath the filepath to save to.
     * @throws SavePassageException If there's an error saving.
     */
    public abstract void saveToFile(String filepath) throws SavePassageException;

    /**
     * Loads a passage from a filepath.
     * @param filepath The filepath to load from.
     * @throws LoadPassageException If there is an error in loading.
     */
    public abstract void loadFromFile(String filepath) throws LoadPassageException;

    private final String _title;
}
