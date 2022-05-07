package net.ahuffman.cli;

import net.ahuffman.passage.Passage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simple data structure to hold a list of passages.
 */
public class PassagesList {
    public PassagesList() {
        _passages = new ArrayList<>();
    }

    /**
     * Loads the passages from a file.
     * @param toLoad The file to load the passages from.
     * @throws LoadPassagesListException If loading the passage fails.
     */
    public PassagesList(File toLoad) throws LoadPassagesListException {
        _passages = new ArrayList<>();

        Scanner s;

        // Load the file into a scanner.
        try {
            s = new Scanner(toLoad);
        }
        catch (FileNotFoundException e) {
            throw new LoadPassagesListException(String.format("File not found at path %s", toLoad.getAbsolutePath()));
        }

        boolean foundTitle = false;
        boolean foundPassageText = false;
        String line;

        while (s.hasNextLine()) {
            line = s.nextLine();

            // If the previous line was the tite
            if (foundTitle) {

            }
            else if (foundPassageText) {

            }
            else {

            }
        }
    }

    public void add(Passage p) {
        _passages.add(p);
    }

    public Passage get(String name) {
        for (Passage e : _passages) {
            if (e.getTitle().equals(name)) {
                return e;
            }
        }

        return null;
    }

    private final ArrayList<Passage> _passages;
}
