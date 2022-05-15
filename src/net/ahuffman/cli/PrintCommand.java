package net.ahuffman.cli;

import net.ahuffman.passage.Passage;
import net.ahuffman.passage.TagList;

import java.io.PrintStream;

/**
 * CLI command to print a passage. Expects a scanner to print to.
 */
public class PrintCommand extends Command {
    private static final String NAME = "print";
    private static final String SYNTAX = "(passage name)";
    private static final String HELP = "Print details for a passage. If no passage name is provided, prints all passages.";

    public PrintCommand() {
        super(NAME, SYNTAX, HELP);
    }

    @Override
    public Object execute(CommandArgs args, Object[] input) throws InvalidCommandOperationException {
        if (!(input[0] instanceof PrintStream w)) {
            throw new InvalidCommandOperationException("Expected a PrintStream as internal command input.");
        }
        if (!(input[1] instanceof PassagesList p)) {
            throw new InvalidCommandOperationException("Expected a PassagesList as second internal command input.");
        }

        if (args.isEmpty()) {
            System.out.printf("%s\n", (p));
        }
        else {

            String nameToPrint;
            Passage toPrint;


            // Find the name to print.
            nameToPrint = args.get(0);

            // Find the correct passage.
            toPrint = p.get(nameToPrint);

            // Print the passage to the scanner, if it is found. Otherwise, throw.
            if (toPrint == null) {
                throw new InvalidCommandOperationException(String.format("Passage \"%s\" not found.", nameToPrint));
            } else {
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
        }

        return null;
    }
}
