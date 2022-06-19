package net.ahuffman.cli;

import net.ahuffman.common.StringHelpers;
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
        // 1. Internal argument checking. Make sure that a PrintStream and the passages list is passed in by
        // input[].
        // 2. User argument checking. If none, print every passage.
        // 3. Otherwise, execute as normally.

        // 1. Internal argument checking. Make sure that a PrintStream and the passages list is passed in by
        // input[].

        if (!(input[0] instanceof PrintStream w)) {
            throw new InvalidCommandOperationException("Expected a PrintStream as internal command input.");
        }
        if (!(input[1] instanceof PassagesList p)) {
            throw new InvalidCommandOperationException("Expected a PassagesList as second internal command input.");
        }

        // 2. User argument checking. If none, print every passage.

        if (args.isEmpty()) {

            p.forEach(passage -> {
                w.printf("%s\n", passage.getTitle());
            });

        }

        // 3. Otherwise, execute as normally.

        else {
            // 1. Fetch the passage to print, whether it be a name or an index. If not found, report.
            // 2. Print the passage data.

            final String strToPrint = args.get(0);
            final Passage toPrint;

            // 1. Fetch the passage to print, whether it be a name or an index. If not found, report.

            if (p.hasName(strToPrint)) {
                toPrint = p.get(strToPrint);
            }
            else if (StringHelpers.isInt(strToPrint)) {
                final int pIndex = Integer.parseInt(strToPrint) - 1;

                if (pIndex >= 0 && pIndex < p.size()) {
                    toPrint = p.at(pIndex);
                }
                else {
                    throw new InvalidCommandOperationException( String.format("Passage number %d is out of range; there are %d passages.", pIndex + 1, p.size()) );
                }
            }
            else {
                throw new InvalidCommandOperationException( String.format("Passage \"%s\" not found.", strToPrint) );
            }

            // 2. Print the passage data.

            w.printf("\n%s\n\n%s\n\n", toPrint.getTitle(), toPrint.fullText());
        }

        return null;
    }
}
