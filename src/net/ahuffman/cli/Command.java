package net.ahuffman.cli;

/**
 * A command line command abstract class.
 */
public abstract class Command {
    /**
     * Constructor
     * @param name The name of the command, by which to call it.
     */
    public Command(String name) {
        _name = name;
    }

    /**
     * Abstract method to perform the command
     * @param args The arguments of the command
     */
    abstract void call(String[] args);

    /**
     * Gets the name of the command
     * @return The name
     */
    public String getName() {
        return _name;
    }

    private final String _name;
}
