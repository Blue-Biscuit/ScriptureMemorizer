package net.ahuffman.cli;

/**
 * A command line command abstract class.
 */
public abstract class Command {
    /**
     * Abstract method to perform the command
     * @param args The arguments of the command
     * @throws CommandSyntaxException If the syntax for the command is incorrect
     * @return The result of the command
     */
    abstract Object call(String[] args, Object input) throws CommandSyntaxException;

    /**
     * Gets the name of the command
     * @return The name
     */
    public abstract String getName();

    /**
     * Usage print of the command
     * @return The usage print
     */
    public abstract String usagePrint();
}
