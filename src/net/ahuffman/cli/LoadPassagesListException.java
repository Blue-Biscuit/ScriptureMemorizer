package net.ahuffman.cli;

/**
 * Exception generated when the passages list fails to load from a file.
 */
public class LoadPassagesListException extends RuntimeException {
    public LoadPassagesListException(String message) {
        super(message);
    }
}
