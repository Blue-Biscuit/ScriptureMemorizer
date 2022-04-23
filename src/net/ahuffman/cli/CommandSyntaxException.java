package net.ahuffman.cli;

public class CommandSyntaxException extends RuntimeException {
    public CommandSyntaxException(String msg) { super(msg); }
}
