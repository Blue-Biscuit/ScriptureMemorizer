package net.ahuffman.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Loads passages from a file.
 */
public class LoadCommand extends Command {
    private static final String NAME = "load";
    private static final String SYNTAX = "<filepath>";
    private static final String HELP = "Loads passages from the given file. This overwrites the current passages.";

    public LoadCommand() {
        super(NAME, SYNTAX, HELP);
    }

    @Override
    public Object execute(CommandArgs args, Object[] input) throws InvalidCommandOperationException {

        // Algorithm:
        // 1. Argument checking. If the input array does not have the passages list, throw an internal error.
        // 2. If there are no arguments, throw a help error.
        // 3. Otherwise...
            // a. Find the file and handle IO errors.
            // b. Load from the file. If there's a duplicate, report and don't load.


        // 1. Argument checking. If the input array does not have the passages list, throw an internal error.

        if (input.length < 1 || !(input[0] instanceof PassagesList list)) {
            throw new InvalidCommandOperationException("Internal error.");
        }

        // 2. If there are no arguments, throw a help error.

        else if (args.isEmpty()) {
            throw new InvalidCommandOperationException(this);
        }

        // 3. Otherwise...

        else {
            Scanner toLoad;

            // a. Find the file and handle IO errors.

            try {
                toLoad = new Scanner(new File(args.get(0)));
            }
            catch (FileNotFoundException ex) {
                throw new InvalidCommandOperationException( String.format( "File not found: %s", args.get(0) ) );
            }

            // b. Load from the file.

            try {
                list.load(toLoad);
            }
            catch (LoadPassagesListException exc) {
                throw new InvalidCommandOperationException(String.format("Source file %s is corrupted.", args.get(0)));
            }
        }

        return null;
    }
}
