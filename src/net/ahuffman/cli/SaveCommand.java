package net.ahuffman.cli;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Command to save the passages list.
 */
public class SaveCommand extends Command {
    private static final String NAME = "save";
    private static final String SYNTAX = "<filepath>";
    private static final String HELP = "Saves the current passages to a file. This overwrites the file.";

    public SaveCommand() {
        super(NAME, SYNTAX, HELP);
    }

    @Override
    public Object execute(CommandArgs args, Object[] input) throws InvalidCommandOperationException {

        // Algorithm:
        // 1. Argument checking. If the input array does not have the passages list as its input,
        // throw an internal error.
        // 2. If there are no arguments, throw a help error.
        // 3. Otherwise...
            // a. Find the file and handle IO errors.
            // b. Write to the file.

        // 1. Argument checking. If the input array does not have the passages list as its input,
        // throw an internal error.

        if (input.length != 1 || !(input[0] instanceof PassagesList list)) {
            throw new InvalidCommandOperationException("Internal error.");
        }

        // 2. If there are no arguments, throw a help error.

        else if (args.isEmpty()) {
            throw new InvalidCommandOperationException(this);
        }

        // 3. Otherwise...

        else {
            PrintWriter writer;

            // a. Find the file and handle IO errors.

            try {
                writer = new PrintWriter(args.get(0));
            }
            catch (FileNotFoundException ex) {
                throw new InvalidCommandOperationException(String.format( "File not found: %s", args.get(0) ));
            }

            // b. Write to the file.

            list.save(writer);
            writer.close();
        }

        return null;
    }
}
