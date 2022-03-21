package com.hufftech.passage;

/**
 * Exception for if a word (or a word index) does not exist
 */
public class NoWordFoundException extends RuntimeException {
    NoWordFoundException(String msg) {
        super(msg);
    }
}