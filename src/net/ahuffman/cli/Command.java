package net.ahuffman.cli;

public abstract class Command {
    /**
     * Constructor
     * @param name The name of the command.
     * @param argSyntax The help syntax of the command.
     */
    public Command(String name, String argSyntax, String help) {
        _name = name;
        _argsSyntax = argSyntax;
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
    public String getArgumentSyntax() {
        return _argsSyntax;
    }

    /**
     * Returns the help print.
     * @return The help message.
     */
    public String getHelp() {
        return _help;
    }

    /**
     * Returns the text that is used for the help command.
     * @return The text used.
     */
    public String helpText() {
        return String.format("Usage: %s %s\n%s", getName(), getArgumentSyntax(), getHelp());
    }

    /**
     * Abstract method to execute the command.
     * @param args The user-provided arguments to the command.
     * @param input The internal inputs of the command.
     * @return The output of the command.
     */
    public abstract Object execute(CommandArgs args, Object[] input) throws InvalidCommandOperationException;

    @Override
    public String toString() {
        return String.format("%s %s", _name, _argsSyntax);
    }

    private final String _name;
    private final String _argsSyntax;
    private final String _help;
}
