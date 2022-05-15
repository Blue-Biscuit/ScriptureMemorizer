package net.ahuffman.cli;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Help command.
 */
public class HelpCommand extends Command {
    private static final String NAME = "help";
    private static final String SYNTAX = "(command name)";
    private static final String HELP = "Gives usage information for a command. If no command is specified, prints a list of commands.";

    public HelpCommand() {
        super(NAME, SYNTAX, HELP);
    }

    @Override
    public Object execute(CommandArgs args, Object[] input) throws InvalidCommandOperationException {

        // Algorithm:
        // 1. Check arguments. If the first index is not a PrintStream
        // and the second is not the commands list, throw an internal error.
        // 2. If the arguments list is empty, print command names.
        // 3. Otherwise...
            // a. Find the command to print.
            // b. Print the help data.


        // 1. Check arguments. If the first index is not a PrintStream
        // and the second is not the commands list, throw an internal error.

        if (input.length < 2 || !(input[0] instanceof PrintStream writer) || !(input[1] instanceof ArrayList commands)) {
            throw new InvalidCommandOperationException("Internal error.");
        }

        // 2. If the arguments list is empty, print command names.

        if (args.isEmpty()) {
            writer.println("Commands:");
            for (Object e : commands) {
                if (e instanceof Command c) {
                    writer.printf("%s: %s\n", c.getName(), c.getHelp());
                }
                else {
                    throw new InvalidCommandOperationException("Internal error.");
                }
            }
        }

        // 3. Otherwise...

        else {
            Command toPrint = null;

            // a. Find the command to print.

            for (Object e : commands) {
                if (e instanceof Command c) {
                    if (c.getName().equals(args.get(0))) {
                        toPrint = c;
                    }
                }
                else {
                    throw new InvalidCommandOperationException("Internal error.");
                }
            }

            // b. Print the help data.

            if (toPrint == null) {
                writer.printf("Command %s not found.\n", args.get(0));
            }
            else {
                writer.printf("%s\n", toPrint.helpText());
            }
        }

        return null;
    }
}
