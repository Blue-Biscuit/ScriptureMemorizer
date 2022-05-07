package net.ahuffman.cli;

import net.ahuffman.passage.Passage;
import net.ahuffman.passage.TagList;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * CLI command to print a passage. Expects a scanner to print to.
 */
public class PrintCommand extends Command {
    private static final String NAME = "print";
    private static final String HELP = "<passage name>";

    public PrintCommand() {
        super(NAME, HELP);
    }

    @Override
    public Object execute(String args, Object[] input) throws InvalidCommandOperationException {
        if (!(input[0] instanceof PrintStream w)) {
            throw new InvalidCommandOperationException("Expected a PrintStream as internal command input.");
        }
        if (!(input[1] instanceof PassagesList p)) {
            throw new InvalidCommandOperationException("Expected a PassagesList as second internal command input.");
        }
        if (args.isEmpty()) {
            throw new InvalidCommandOperationException(String.format("Usage: %s %s", getName(), getHelp()));
        }

        int firstSpace;
        String nameToPrint;
        Passage toPrint;

        // Find the first space.
        firstSpace = args.indexOf(' ');

        // Find the name to print.
        if (firstSpace == -1) {
            nameToPrint = args;
        }
        else {
            nameToPrint = args.substring(0, firstSpace);
        }

        // Find the correct passage.
        toPrint = p.get(nameToPrint);

        // Print the passage to the scanner, if it is found. Otherwise, throw.
        if (toPrint == null) {
            throw new InvalidCommandOperationException(String.format("Passage \"%s\" not found.", nameToPrint));
        }
        else {
            w.printf("%s\n\"%s\"\nTags: ", toPrint.getTitle(), toPrint.fullText());

            TagList tags = toPrint.getTags();
            int numTags = tags.numTags();

            if (numTags > 0) {
                for (int i = 0; i < numTags - 1; i++) {
                    w.printf("%s ", tags.getTag(i));
                }
                w.printf("%s", tags.getTag(numTags - 1));
            }
            w.println();
        }

        return null;
    }
}
