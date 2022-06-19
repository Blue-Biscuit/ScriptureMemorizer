package net.ahuffman.cli;

import net.ahuffman.passage.Passage;
import net.ahuffman.passage.StringPassage;

public class NewPassageCommand extends Command {
    private static final String NAME = "new";
    private static final String SYNTAX = "<title> <passage text> (tags)...";
    private static final String HELP = "Creates a new passage.";

    public NewPassageCommand() {
        super(NAME, SYNTAX, HELP);
    }


    @Override
    public Object execute(CommandArgs args, Object[] input) {
        if (!(input[0] instanceof PassagesList)) {
            throw new InvalidCommandOperationException("Internal error.");
        }
        if (args.size() < 2) {
            throw new InvalidCommandOperationException(this);
        }

        String name;
        String passage;
        Passage p;

        // Split the arguments into two name and passage.
        name = args.get(0);
        passage = args.get(1);
        p = new StringPassage(name, passage);

        // Add the rest of the arguments as tags.
        for (int i = 2; i < args.size(); i++) {
            p.getTags().addTag(args.get(i));
        }


        ((PassagesList) input[0]).add(p);

        return input;
    }
}
