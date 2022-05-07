package net.ahuffman.cli;

import net.ahuffman.passage.Passage;
import net.ahuffman.passage.StringPassage;

public class NewPassageCommand extends Command {
    private static final String NAME = "new";
    private static final String HELP = "<title> <passage>";

    public NewPassageCommand() {
        super(NAME, HELP);
    }


    @Override
    public Object execute(String args, Object input) {
        if (!(input instanceof PassagesList)) {
            throw new InvalidCommandOperationException("Internal error.");
        }

        String name;
        String passage;
        Passage p;
        int firstSpace;

        // Get the first space in the string.
        firstSpace = args.indexOf("\s");

        // If firstSpace is not found, then return an error.
        if (firstSpace == -1) {
            throw new InvalidCommandOperationException("Too few arguments.");
        }

        // Split the arguments into two name and passage.
        name = args.substring(0, firstSpace);
        passage = args.substring(firstSpace + 1);
        p = new StringPassage(name, passage);

        ((PassagesList) input).add(p);

        return input;
    }
}
