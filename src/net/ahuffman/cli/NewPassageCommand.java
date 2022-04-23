package net.ahuffman.cli;

import net.ahuffman.passage.StringPassage;

/**
 * Command to create a new command
 */
public class NewPassageCommand extends Command {
    public static final String COMMAND_NAME = "new";
    public static final String USAGE_PRINT = "usage: new <title> <text>";

    /**
     * Creates a new passage
     * @param args The arguments of the command
     * @return the passage created
     * @throws CommandSyntaxException If the syntax of the command is incorrect
     */
    @Override
    Object call(String[] args) throws CommandSyntaxException {
        if (args.length < 2) {
            throw new CommandSyntaxException("Not enough arguments");
        }

        return new StringPassage(args[0], args[1]);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String usagePrint() {
        return USAGE_PRINT;
    }

}
