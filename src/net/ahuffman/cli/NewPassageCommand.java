package net.ahuffman.cli;

import net.ahuffman.passage.Passage;
import net.ahuffman.passage.StringPassage;

import java.util.ArrayList;

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
    Object call(String[] args, Object input) throws CommandSyntaxException {
        if (args.length < 3) {
            throw new CommandSyntaxException("Not enough arguments");
        }

        ArrayList<Passage> p = (ArrayList<Passage>) input;
        Passage result = new StringPassage(args[1], args[2]);

        p.add(result);

        return result;

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
