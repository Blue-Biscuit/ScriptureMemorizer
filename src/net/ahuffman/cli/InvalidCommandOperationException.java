package net.ahuffman.cli;

/**
 * If a command is used wrongfully (wrong input/output).
 */
public class InvalidCommandOperationException extends RuntimeException {
    public InvalidCommandOperationException(String message){
        super(message);
    }
}
