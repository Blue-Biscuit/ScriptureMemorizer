package net.ahuffman.passage;


import net.ahuffman.common.PeekingLineScanner;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.util.Scanner;

/**
 * A passage implementation using a simple String.
 */
public class StringPassage extends Passage {

    public StringPassage(String title, String text) throws NullPointerException {
        super(title);

        load(text);
    }

    public StringPassage(String title, PeekingLineScanner s) {
        super(title);

        loadFromScanner(s);
    }

    public StringPassage(String title, File file) {
        super(title);

        loadFromFile(file);
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
    public void saveToFile(File file) throws SavePassageException {
        FileWriter fw;

        try {
            fw = new FileWriter(file, true);
        }
        catch (IOException e) {
            throw new SavePassageException("Error opening file");
        }

        try {

            fw.write("title: ");
            fw.write(getTitle());
            fw.write('\n');
            fw.write('\"');
            fw.write(_text);
            fw.write('\"');
            fw.write('\n');

            TagList t = getTags();
            int tLen = t.numTags();

            if (tLen > 0) {
                for (int i = 0; i < tLen - 1; i++) {
                    fw.write(t.getTag(i));
                    fw.write(' ');
                }
                fw.write(t.getTag(tLen - 1));
            }
            fw.write('\n');

        }
        catch (IOException e) {
            throw new SavePassageException(e.getMessage());
        }
        finally {
            try {
                fw.close();
            }
            catch (IOException e) {
                throw new SavePassageException("Error closing file");
            }
        }


    }

    @Override
    protected void loadFromFile(File file) throws LoadPassageException {

        // Open the file, and call the method to load from a scanner.
        try {
            loadFromScanner(new PeekingLineScanner(new Scanner(file)));
        }
        catch (FileNotFoundException e) {
            throw new LoadPassageException("File does not exist");
        }
    }

    /**
     * The same as loadFromFile, except using a String rather than a file.
     * @param text The String text to load from.
     * @throws LoadPassageException If the passage could not be loaded from the text.
     */
    protected void loadFromText(String text) {
        loadFromScanner(new PeekingLineScanner(new Scanner(text)));
    }

    /**
     * Loads a StringPassage from a scanner.
     * @param reader The scanner to read from.
     */
    protected void loadFromScanner(PeekingLineScanner reader) throws LoadPassageException {
        String passage = null;
        String tags = null;
        boolean foundLastLine = false;
        boolean loadedPassageLastLine = false;
        String line;

        while (tags == null && reader.hasNextLine()) {
            line = reader.nextLine().trim();

            // If the previous line was the title, load the passage.
            if (foundLastLine) {
                try {
                    passage = line.substring(1, line.length() - 1);
                    loadedPassageLastLine = true;
                    foundLastLine = false;
                }
                catch (IndexOutOfBoundsException e) {
                    throw new LoadPassageException("Passage reader is corrupted.");
                }
            }

            // If the previous line was the passage, load the tags.
            else if (loadedPassageLastLine) {
                tags = line;
            }

            // If this line is the title, mark the next for passage loading.
            else if (line.equals("title: " + getTitle())) {
                foundLastLine = true;
            }
        }

        if (passage == null || tags == null) {
            throw new LoadPassageException("Passage was not saved at that file.");
        }
        else {
            // Load the passage text
            load(passage);

            // Load the tags
            TagList internalList = getTags();
            String[] tList = tags.split("\s");
            for(String e : tList) {
                internalList.addTag(e);
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(_words).iterator();
    }

    @Override
    public String toString() {
        return fullText();
    }

    private void load(String text) {
        if (text == null) {
            throw new NullPointerException("Argument 'text' cannot be null.");
        }

        _text = text.trim();

        _words = _text.split("\s");
    }

    private String _text;
    private String[] _words;
}
