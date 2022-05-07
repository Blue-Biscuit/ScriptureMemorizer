package net.ahuffman.cli;

/**
 * CLI command to exit the program. All this should really do is
 * signal to the program that the user wants to exit; it doesn't have functionality of its own.
 */
public class ExitCommand extends Command {
    private static final String NAME = "exit";
    private static final String HELP = "";

    public ExitCommand() {
        super(NAME, HELP);
    }

    @Override
    public Object execute(String args, Object[] input) throws InvalidCommandOperationException {
        return null;
    }
}
