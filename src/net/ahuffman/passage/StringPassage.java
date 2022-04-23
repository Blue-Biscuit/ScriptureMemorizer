package net.ahuffman.passage;


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
        Scanner reader;

        // Get the file.
        try {
            reader = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            throw new LoadPassageException("File does not exist");
        }

        // find the passage within the database file.
        String passage = null;
        boolean found = false;
        String line;
        while (passage == null && reader.hasNextLine()) {
            line = reader.nextLine().trim();

            // If the previous line was the title, load the passage.
            if (found) {
                try {
                    passage = line.substring(1, line.length() - 1);
                }
                catch (IndexOutOfBoundsException e) {
                    throw new LoadPassageException("Passage file is corrupted.");
                }
            }

            // If this line is the title, mark the next for passage loading.
            else if (line.equals("title: " + getTitle())) {
                found = true;
            }
        }

        if (passage == null) {
            throw new LoadPassageException("Passage was not saved at that file.");
        }
        else {
            load(passage);
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