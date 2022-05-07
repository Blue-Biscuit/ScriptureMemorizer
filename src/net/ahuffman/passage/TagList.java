package net.ahuffman.passage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A list of searchable tags.
 */
public class TagList {
    /**
     * Constructor.
     * @param data A list of tags.
     */
    public TagList(String[] data) {
        _tags = new ArrayList<>(Arrays.stream(data).toList());
    }

    /**
     * Constructor.
     * @param data A list of tags.
     */
    public TagList(ArrayList<String> data) {
        _tags = new ArrayList<>(data);
    }

    /**
     * Default constructor.
     */
    public TagList() {
        _tags = new ArrayList<>();
    }




    /**
     * Gets the number of tags.
     * @return The number of tags.
     */
    public int numTags() {
        return _tags.size();
    }

    /**
     * Adds a tag to the list.
     * @param tag The tag to add.
     */
    public void addTag(String tag) {
        _tags.add(tag);
    }

    /**
     * Gets the tag at the specified index.
     * @param index The index to get at.
     * @return The tag.
     */
    public String getTag(int index) {
        return _tags.get(index);
    }

    /**
     * Removes the tag at the specific index.
     * @param index the index to remove at.
     * @throws InvalidTagException if the index is out of range.
     */
    public void removeTag(int index) throws InvalidTagException {
        if (index < 0 || index >= _tags.size()) {
            throw new InvalidTagException("Tag index out of bounds.");
        }

        _tags.remove(index);
    }

    /**
     * Return a list of all tags which start with a certain prefix.
     * @param prefix The prefix to check by.
     * @return An arraylist of all of the results.
     */
    public ArrayList<String> matchingTags(String prefix) {
        ArrayList<String> result = new ArrayList<>();
        _tags.forEach((e) -> {
            if (e.startsWith(prefix)) {
                result.add(e);
            }
        });

        return result;
    }

    /**
     * True if the tags list contains a specified tag.
     * @param tag The tag to check with.
     * @return True if the tags list contains the tag.
     */
    public boolean containsTag(String tag) {
        for (String s : _tags) {
            if (s.equals(tag)) {
                return true;
            }
        }

        return false;
    }

    private final ArrayList<String> _tags;
}
