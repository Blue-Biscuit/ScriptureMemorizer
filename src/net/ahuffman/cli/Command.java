package net.ahuffman.cli;

public abstract class Command {
    /**
     * Constructor
     * @param name The name of the command.
     * @param help The help syntax of the command.
     */
    public Command(String name, String help) {
        _name = name;
        _help = help;
    }

    /**
     * Gets the name of the command.
     * @return The name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Gets the help syntax for the command.
     * @return The help syntax.
     */
    public String getHelp() {
        return _help;
    }

    /**
     * Abstract method to execute the command.
     * @param args The user-provided arguments to the command.
     * @param input The internal inputs of the command.
     * @return The output of the command.
     */
    public abstract Object execute(String args, Object input) throws InvalidCommandOperationException;

    @Override
    public String toString() {
        return String.format("%s %s", _name, _help);
    }

    private final String _name;
    private final String _help;
}
