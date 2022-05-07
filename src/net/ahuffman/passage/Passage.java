package net.ahuffman.passage;

import java.io.File;

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
        _tags = new TagList();
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
     * Gets the list of tags.
     * @return The tags list.
     */
    public TagList getTags() {
        return _tags;
    }

    /**
     * Saves the passage to the filepath. If the filepath doesn't exist, the file
     * is created. If it does, it is overwritten.
     * @param file the file to save to.
     * @throws SavePassageException If there's an error saving.
     */
    public abstract void saveToFile(File file) throws SavePassageException;

    /**
     * Loads a passage from a filepath.
     * @param file The file to load from.
     * @throws LoadPassageException If there is an error in loading.
     */
    protected abstract void loadFromFile(File file) throws LoadPassageException;

    private final String _title;
    private final TagList _tags;
}
