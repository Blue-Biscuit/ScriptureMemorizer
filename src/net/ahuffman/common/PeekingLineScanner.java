package net.ahuffman.common;

import java.io.Closeable;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A scanner, in which you can peek the next token and next line.
 */
public class PeekingLineScanner implements Closeable {

    /**
     * Constructor.
     * @param s The scanner to peek.
     */
    public PeekingLineScanner(Scanner s) {
        scanner = s;
        closed = false;

        internalNextLine();
    }

    /**
     * Gets the next line of the scanner, or throws an exception if there is no next line.
     * @return The next line.
     * @throws NoSuchElementException If there is not a next line.
     * @throws IllegalStateException If the scanner has been closed.
     */
    public String nextLine() throws NoSuchElementException, IllegalStateException {

        // Algorithm:
        // 1. If the scanner is closed, throw.
        // 2. Otherwise...
            // a. If the external scanner has another line,
            // return it and update the internal scanner.
            // b. Otherwise, throw an exception.

        // 1. If the scanner is closed, throw.

        if (closed) {
            throw new IllegalStateException("Cannot get the next line of a closed scanner.");
        }

        // 2. Otherwise...

        else {

            // a. If the external scanner has another line,
            // return it and update the internal scanner.

            if (hasNextLine()) {

                String result = nextLine;
                internalNextLine();

                return result;
            }

            // b. Otherwise, throw an exception.

            else {
                throw new NoSuchElementException("Scanner does not have a next line.");
            }

        }
    }

    /**
     * Peeks the next line without advancing the scanner.
     * @return The next line.
     * @throws NoSuchElementException If there is not a next line.
     * @throws IllegalStateException If the scanner has been closed.
     */
    public String peekNextLine() throws NoSuchElementException, IllegalStateException {

        // Algorithm:
        // 1. If the scanner has been closed, throw.
        // 2. Otherwise, if there is no next line, throw.
        // 3. Otherwise, return the next line.

        // 1. If the scanner has been closed, throw.

        if (closed) {
            throw new IllegalStateException("Cannot peek the next line of a closed scanner.");
        }

        // 2. Otherwise, if there is no next line, throw.

        else if (nextLine == null) {
            throw new NoSuchElementException("Scanner does not have a next line.");
        }

        // 3. Otherwise, return the next line.

        else {
            return nextLine;
        }

    }

    /**
     * Returns true if the scanner has another line.
     * @return True if the scanner has another line.
     * @throws IllegalStateException If the scanner has been closed.
     */
    public boolean hasNextLine() throws IllegalStateException {
        if (closed) {
            throw new IllegalStateException("Cannot check the next line of a closed scanner.");
        }
        else {
            return nextLine != null;
        }
    }

    /**
     * Closes the scanner.
     * @throws IllegalStateException If the scanner has already been closed.
     */
    public void close() throws IllegalStateException {
        if (closed) {
            throw new IllegalStateException("Cannot close scanner twice.");
        }
        else {
            scanner.close();
            nextLine = null;
            closed = true;
        }
    }

    /**
     * True if the scanner has been closed.
     * @return Whether the scanner has been closed.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Updates the pointer to the next line of the scanner, such that
     * nextLine is null if there is not one.
     */
    private void internalNextLine() {

        // Algorithm:
        // 1. If the internal scanner has a next line, update the internal nextLine
        // field to reflect the next line of the scanner.
        // 2. Otherwise, set the nextLine field to null.

        // 1. If the internal scanner has a next line, update the internal nextLine
        // field to reflect the next line of the scanner.

        if (scanner.hasNextLine()) {
            nextLine = scanner.nextLine();
        }

        // 2. Otherwise, set the nextLine field to null.

        else {
            nextLine = null;
        }
    }

    private final Scanner scanner;
    private String nextLine;
    private boolean closed;
}
