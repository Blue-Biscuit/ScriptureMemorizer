package net.ahuffman.cli;

import net.ahuffman.common.PeekingLineScanner;
import net.ahuffman.passage.LoadPassageException;
import net.ahuffman.passage.Passage;
import net.ahuffman.passage.StringPassage;
import org.jetbrains.annotations.NotNull;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Simple data structure to hold a list of passages.
 */
public class PassagesList implements Iterable<Passage> {
    /**
     * Default constructor. Initializes an empty list.
     */
    public PassagesList() {
        _passages = new ArrayList<>();
    }

    /**
     * Loads the passages from a peeking scanner.
     * @param toLoad The scanner to load the passages from.
     * @throws LoadPassagesListException If loading the passage fails.
     */
    public PassagesList(PeekingLineScanner toLoad) throws LoadPassagesListException {
        load(toLoad);
    }

    /**
     * Loads the passages from a scanner.
     * @param toLoad The scanner to load the passages from.
     * @throws LoadPassagesListException If loading the passage fails.
     */
    public PassagesList(Scanner toLoad) throws LoadPassagesListException {
        this(new PeekingLineScanner(toLoad));
    }

    /**
     * Writes the passages list to the print writer.
     * @param toWrite The writer to write to.
     * @throws NullPointerException If the writer is null.
     */
    public void save(PrintWriter toWrite) throws NullPointerException {

        // Algorithm:
        // 1. If the PrintWriter is null, throw.
        // 2. Otherwise...
            // a. For each element in the PassagesList...
                // 1. Print to the writer in readable format.

        // 1. If the PrintWriter is null, throw.

        if (toWrite == null) {
            throw new NullPointerException("The PrintWriter was null.");
        }

        // 2. Otherwise...

        else {

            // a. For each element in the PassagesList...

            for (Passage e : _passages) {

                // 1. Print to the writer in readable format.

                toWrite.printf("title: %s\n\"%s\"\n%s\n", e.getTitle(), e.fullText(), e.getTags().toString());
            }
        }
    }

    /**
     * Loads the passages list from a peeking scanner.
     * @param toLoad Scanner to load from.
     * @throws LoadPassagesListException If the source is corrupted.
     */
    public void load(PeekingLineScanner toLoad) throws LoadPassagesListException {
        _passages = new ArrayList<>();
        String passageTitle;
        Passage passage;
        final String TITLE_ID = "title: ";
        final int TITLE_ID_LEN = 7;

        // Algorithm:
        // 1. While the scanner still has lines...
        // a. If the next line of the scanner matches the title line introduction token...
        // 1. Load the passage.
        // 2. Add the passage to the passages list.

        // 1. While the scanner still has lines...

        while (toLoad.hasNextLine()) {

            // a. If the next piece of the scanner matches the title line introduction token...

            passageTitle = toLoad.peekNextLine();
            if (passageTitle.startsWith(TITLE_ID)) {
                passageTitle = passageTitle.substring(TITLE_ID_LEN);

                // 1. Load the passage.

                try {
                    passage = new StringPassage(passageTitle, toLoad);
                }
                catch (LoadPassageException exp) {
                    throw new LoadPassagesListException("Passages list source is corrupted.");
                }

                // 2. Add the passage to the passages list.

                _passages.add(passage);
            }
            else {
                toLoad.nextLine();
            }
        }
    }

    /**
     * Loads the passages list from a peeking scanner.
     * @param toLoad Scanner to load from.
     * @throws LoadPassagesListException If the source is corrupted.
     */
    public void load(Scanner toLoad) throws LoadPassagesListException {
        load(new PeekingLineScanner(toLoad));
    }

    /**
     * Adds a passage to the list.
     * @param p The passage to add.
     */
    public void add(Passage p) {
        _passages.add(p);
    }

    /**
     * Returns the subset of passages with the specified tag.
     * @param tag The tag to get by.
     * @return The subset.
     * @throws NullPointerException If the given tag is null.
     */
    public PassagesList byTag(String tag) throws NullPointerException{
        PassagesList result = new PassagesList();

        // Algorithm:
        // 1. If tag is null, throw.
        // 2. For each element in the array, if the tags list contains the given tag, add to the result.
        // 3. Return the result.



        // 1. If tag is null, throw.

        if (tag == null) {
            throw new NullPointerException("Tag was null.");
        }

        // 2. For each element in the array, if the tags list contains the given tag, add to the result.

        forEach(e -> {
           if (e.getTags().containsTag(tag)) {
               result.add(e);
           }
        });

        // 3. Return the result.

        return result;
    }

    @Override
    public String toString() {
        StringWriter out = new StringWriter();
        save(new PrintWriter(out));

        return out.toString();
    }

    /**
     * Gets a passage by name.
     * @param name The name of the passage to get from the list.
     * @return The passage within the list.
     * @throws NoSuchElementException If the title is not within the passages list.
     */
    public Passage get(String name) throws NoSuchElementException {
        for (Passage e : _passages) {
            if (e.getTitle().equals(name)) {
                return e;
            }
        }

        throw new NoSuchElementException(String.format("Passage entitled \"%s\" is not stored within the list.", name));
    }

    /**
     * Determines if the passages list has a passage of the given name.
     * @param name The name to check for.
     * @return True if the name is in the list.
     */
    public boolean hasName(String name) {
        for (Passage e : _passages) {
            if (e.getTitle().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private ArrayList<Passage> _passages;


    /**
     * Tester for PassagesList.
     * @param args Unused.
     */
    public static void main(String[] args) throws IOException {
        final String filename = "plist_test.dat";
        final int numPassages = 100;
        final int numTags = 100;
        PassagesList toTest;
        PassagesList toLoad;
        File outFile;

        // Algorithm:
        // 1. Build the list of passages.
        // 2. Save the list to a file.
        // 3. Load the list from a file.

        // 1. Build the list of passages.

        toTest = new PassagesList();
        for (int i = 1; i <= numPassages; i++) {
            Passage toAdd;

            toAdd = new StringPassage(String.format("Test %d", i), String.format("This is test passage %d.", i));
            toTest.add(toAdd);

            for (int j = 1; j <= numTags; j++) {
                toAdd.getTags().addTag(String.format("tag%d", j));
            }
        }

        // 2. Save the list to a file.

        outFile = new File(filename);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }

        PrintWriter pw = new PrintWriter(outFile);
        toTest.save(pw);
        pw.close();

        // 3. Load the list from a file.

        toLoad = new PassagesList(new Scanner(outFile));
        System.out.println(toLoad);
    }

    @NotNull
    @Override
    public Iterator<Passage> iterator() {
        return _passages.iterator();
    }

    public int size() {
        return _passages.size();
    }

    public Passage at(int rank) throws IndexOutOfBoundsException {
        return _passages.get(rank);
    }
}
