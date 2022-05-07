package net.ahuffman.passage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A list of searchable tags.
 */
public class TagList implements Iterable<String> {
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
     * @throws InvalidTagException If the tag is null or contains an invalid character (space).
     */
    public void addTag(String tag) throws InvalidTagException {
        if (tag == null) {
            throw new InvalidTagException("Cannot add a null value as a tag.");
        }
        else if (tag.contains("\s")) {
            throw new InvalidTagException("Tags cannot contain a space character.");
        }

        _tags.add(tag);
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
     * Removes the given tag.
     * @param tag The tag to remove.
     * @throws InvalidTagException If the list does not contain the tag.
     */
    public void removeTag(String tag) throws InvalidTagException {
        Integer result = null;

        for (int i = 0; i < _tags.size() && result == null; i++) {
            String e = _tags.get(i);

            if (e.equals(tag)) {
                result = i;
            }
        }

        if (result == null) {
            throw new InvalidTagException("Tags list did not contain the tag " + tag);
        }
        else {
            _tags.remove(result.intValue());
        }
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

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return _tags.iterator();
    }
}
