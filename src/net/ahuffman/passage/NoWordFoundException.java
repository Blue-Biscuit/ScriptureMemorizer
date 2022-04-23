package net.ahuffman.passage;

/**
 * Exception for if a word (or a word index) does not exist
 */
public class NoWordFoundException extends RuntimeException {
    public NoWordFoundException(String msg) {
        super(msg);
    }
}
