package net.ahuffman.cli;

import net.ahuffman.common.PrintHelpers;
import net.ahuffman.common.StringHelpers;
import net.ahuffman.memorization.BlankingMemorizationGame;
import net.ahuffman.passage.Passage;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BlankCommand extends Command {
    private static final String NAME = "blank";
    private static final String SYNTAX = "<name> (or <number> or tag <tag>) [blanks per round]=1";
    private static final String HELP = "Plays a blanking game with the given passage or passages.\n";

    public BlankCommand() {
        super(NAME, SYNTAX, HELP);
    }

    /**
     * Keyword to stop the execution of this command.
     */
    public static final String STOP_KEYWORD = "exit";

    private static final int DEFAULT_BLANK_PER = 1;
    private static final int SNIPPET_BEFORE_AFTER = 15;

    @Override
    public Object execute(CommandArgs args, Object[] input) throws InvalidCommandOperationException {
        final String passageName;
        final Passage passage;
        final int blanksPerRound;
        final boolean isByTag;

        // Algorithm:
        // 1. Argument checking. If input list is not a PrintWriter, scanner, and the passages list, throw
        // an internal error.
        // 2. Print help if the incorrect number of arguments are given.
        // 3. Determine the blanks per round.
        // 4. Determine if playing by tag.
        // 5. Otherwise, Determine the passage being played.
            // a. Use the input as a passage name.
            // b. If not found, use it as a rank in the passages list.
            // c. If an invalid rank, throw.
        // 6. Play the game.

        // 1. Argument checking. If input list is not a PrintWriter, scanner, and the passages list, throw
        // an internal error.

        if (!(
                input.length == 3 &&
                input[1] instanceof final PrintStream out &&
                input[2] instanceof final Scanner scanner &&
                input[0] instanceof final PassagesList plist
            )) {
            throw new InvalidCommandOperationException("Internal error.");
        }

        // 2. Print help if the incorrect number of arguments are given.

        if (args.isEmpty()) {
            throw new InvalidCommandOperationException(this);
        }

        isByTag = args.get(0).equals("tag");

        if (isByTag && args.size() < 2) {
            throw new InvalidCommandOperationException(this);
        }

        // 3. Determine the blanks per round.

        blanksPerRound = findBlanksPerRound(args, isByTag);

        // 4. Determine if playing by tag (the spec for this is if the first argument is the word "tag."

        if (isByTag) {
            handleTagPlay(args, input, plist);
            return null;
        }

        // 5. Otherwise, Determine the passage being played.

        passageName = args.get(0);
        passage = findNamedPassage(passageName, plist);

        // 6. Play the game.

        blankingGame(passage, blanksPerRound, out, scanner);

        return null;
    }

    private void handleTagPlay(CommandArgs args, Object[] input, PassagesList plist) throws InvalidCommandOperationException {
        final int blankPer; // The number of words to blank every round.
        final String tag; // The tag by which to use passages.
        final PassagesList passagesByTag; // The passages with the given tag.

        // Algorithm:
        // 1. Check argument formatting.
            // a. If there are less than 2 arguments, there are not enough to proceed; throw.
            // b. If there are 2 arguments, there is no provided blank per round value by the user; default this.
            // c. If there are more than 2 arguments, use the third as the blanks per round, if it is valid.
            // If it isn't valid, default the value.
        // 2. Find the list of passages with the user-inputted tag.
            // a. If no tags are found, throw, explaining what happened.
        // 3. For each passage found, call the command by passage name.

        // 1. Check argument formatting.
        // a. If there are less than 2 arguments, there are not enough to proceed; throw.

        if (args.size() < 2) {
            throw new InvalidCommandOperationException(this);
        }

        // b. If there are 2 arguments, there is no provided blank per round value by the user; default this.

        if (args.size() == 2) {
            blankPer = DEFAULT_BLANK_PER;
        }

        // c. If there are more than 2 arguments, use the third as the blanks per round, if it is valid.
        // If it isn't valid, default the value.

        else {
            final String bpr = args.get(2);

            if (StringHelpers.isInt(bpr)) {
                blankPer = Integer.parseInt(bpr);
            }
            else {
                blankPer = DEFAULT_BLANK_PER;
            }
        }

        tag = args.get(1);

        // 2. Find the list of passages with the user-inputted tag.

        passagesByTag = plist.byTag(tag);

        // a. If no tags are found, throw, explaining what happened.

        if (passagesByTag.size() == 0) {
            throw new InvalidCommandOperationException(String.format("No passages with tag \"%s\" found.", tag));
        }

        // 3. For each passage found, call the command by passage name.

        passagesByTag.forEach(e -> {
            final String name = e.getTitle();

           execute(new CommandArgs( String.format("\"%s\" %d", name, blankPer) ), input);
        });
    }

    private Passage findNamedPassage(String name, PassagesList plist) throws InvalidCommandOperationException {
        final Passage result;

        // Algorithm:
        // 1. Use the argument as a name, and search for it in the passages list.

        if (plist.hasName(name)) {
            result = plist.get(name);
        }

        // 2. If not found, try to use it as a rank in the passages list.

        else if (StringHelpers.isInt(name)) {
            final int rank = Integer.parseInt(name) - 1;

            if (rank >= 0 && rank < plist.size()) {
                result = plist.at(rank);
            }
            else {
                throw new InvalidCommandOperationException(
                        String.format("Input rank %d is an invalid number. There are %d passages in the list.", rank + 1, plist.size())
                );
            }
        }

        // 3. If this doesn't work, throw, explaining what happened.

        else {
            throw new InvalidCommandOperationException(String.format("There is no passage entitled \"%s\" loaded.", name));
        }

        return result;
    }

    private int findBlanksPerRound(CommandArgs args, boolean byTag) {
        final int BLANKS_LOC_BY_NAME = 1;
        final int BLANKS_LOC_BY_TAG = BLANKS_LOC_BY_NAME + 1;

        final int result;
        final String resultString;
        final int blanksLoc;

        // Algorithm:
        // 1. Get the string of the blanks per round, whether it's by tag or by name.
        // 2. If the determined location of the value does not exist in the arguments, default the value. Otherwise,
        // get the value.

        // 1. Get the string of the blanks per round, whether it's by tag or by name.

        if (byTag) {
            blanksLoc = BLANKS_LOC_BY_TAG;
        }
        else {
            blanksLoc = BLANKS_LOC_BY_NAME;
        }

        // 2. If the determined location of the value does not exist in the arguments, default the value. Otherwise,
        // get the value.

        if (blanksLoc >= args.size()) {
            result = DEFAULT_BLANK_PER;
        }
        else {
            resultString = args.get(blanksLoc);

            if (StringHelpers.isInt(resultString)) {
                result = Integer.parseInt(resultString);
            }
            else {
                result = DEFAULT_BLANK_PER;
            }
        }

        return result;
    }

    private void blankingGame(Passage passage, int blanksPerRound, PrintStream out, Scanner in) {
        final BlankingMemorizationGame game = new BlankingMemorizationGame(passage); // The game to play.

        // Algorithm:
        // 1. While the game isn't done...
            // a. Clear the screen, and display necessary information to the user.
            // b. Get user input.
            // c. If the user's input matches,
                // 1. Continue by the given number of blanks (or until the blanks are full).
                // 2. Print congratulatory messages and wait a couple of seconds.
            // d. Otherwise,
                // 1. If the user typed the "exit" keyword, exit.
                // 2. If the user inputted nothing, ask if they were correct (this is for people who don't want to
                // retype a potentially long passage).
                // 3. Otherwise, print the correct passage and halt until the user is ready to try again.


        // 1. While the game isn't done...

        while (!game.done()) {
            final String userInput; // The user's guess at the passage.
            final int matchResult; // The result of the match test routine; the loc of an error or a flag if correct.

            // a. Clear the screen, and display necessary information to the user.

            PrintHelpers.cls(out);
            out.printf("%s\n\n", game);

            // b. Get user input.

            PrintHelpers.printYellow(out, ">>> ");
            userInput = in.nextLine();

            // c. If the user's input matches,

            matchResult = game.matches(userInput);
            if (matchResult == BlankingMemorizationGame.A_MATCH) {

                // 1. Continue by the given number of blanks (or until the blanks are full).

                if (game.fullyBlanked()) {
                    game.next();
                }
                else {
                    for (int i = 0; i < blanksPerRound && !game.fullyBlanked(); i++) {
                        game.next();
                    }
                }

                // 2. Print congratulatory messages and wait a couple of seconds.

                out.println();
                PrintHelpers.printGreen(out, "That was correct!\n");
                PrintHelpers.delay();

            }

            // d. Otherwise,

            else {

                // 1. If the user typed the "exit" keyword, exit.

                if (userInput.equals(STOP_KEYWORD)) {
                    break;
                }

                // 2. If the user inputted nothing, ask if they were correct (this is for people who don't want to
                // retype a potentially long passage).

                else if (userInput.isEmpty()) {
                    checkUserIfCorrect(out, in, game, blanksPerRound, passage);
                }

                // 3. Otherwise, print the correct passage and halt until the user is ready to try again.

                else {
                    out.println();
                    printLoss(out, userInput, matchResult, passage);
                    out.println("Press enter to continue.");
                    in.nextLine();
                }
            }
        }
    }

    /**
     * Asks the user to check themselves if they were correct. This is for those who don't want to retype a passage.
     * @param out The output writer.
     * @param in The input reader.
     * @param game The game to advance.
     */
    private void checkUserIfCorrect(PrintStream out, Scanner in, BlankingMemorizationGame game, int blanksPerRound, Passage passage) {
        Boolean yesNo = null; // user's y/n prompt input.

        // Algorithm:
        // 1. Print the necessary information for the user to decide.

        out.print("Were you correct?\n\n");
        out.printf("%s\n\n", passage.fullText());

        // 2. Ask for a yes/no response.

        while (yesNo == null) {
            PrintHelpers.printGreen(out, "y");
            PrintHelpers.printYellow(out, "/");
            PrintHelpers.printRed(out, "n");
            PrintHelpers.printYellow(out, " >> ");

            try {
                yesNo = StringHelpers.parseYesNo(in.nextLine());
            } catch (InputMismatchException ignored) {

            }
        }

        // 3. If yes, continue the game according to the number of blanks per round.

        if (yesNo) {
            if (game.fullyBlanked()) {
                game.next();
            }
            else {
                for (int i = 0; i < blanksPerRound && !game.fullyBlanked(); i++) {
                    game.next();
                }
            }

            out.println();
            PrintHelpers.printGreen(out, "That was correct!\n");
            PrintHelpers.delay();
        }

        // 4. If no, print a loss.

        else {
            PrintHelpers.printRed(out, "Sorry, that was incorrect.\n");
            PrintHelpers.delay();
        }
    }

    /**
     * Prints the user output for a loss, informing the user where they messed up.
     * @param out The output stream.
     * @param lossLoc The location of the loss.
     * @param passage The passage.
     */
    private void printLoss(PrintStream out, String userInput, int lossLoc, Passage passage) {
        // Algorithm:
        // 1. Print consolatory messaging.
        // 2. If the loss location was greater than the full text length, that means that the user added something to the
        // string. tell them that.
        // 3. If the loss location was greater than the user input, that means that the user's input is too short. Tell them
        // that.
        // 4. If the loss location is in the string, snippet the string to show them exactly where they messed up.
        // 5. Print the correct text.

        // 1. Print consolatory messaging.

        PrintHelpers.printRed(out, "Sorry, that was incorrect.\n\b");

        // 2. If the loss location was greater than the input length, that means that the user added something to the
        // string. tell them that.

        if (lossLoc >= passage.fullText().length()) {
            out.print("Your guess was longer than the correct text.\n\n");
        }

        // 3. If the loss location was greater than the user input, that means that the user's input is too short. Tell them
        // that.

        else if (lossLoc >= userInput.length()) {
            out.print("Your guess was shorter than the correct text.\n\n");
        }

        // 4. If the loss location is in the string, snippet the string to show them exactly where they messed up.

        else {
            out.printf("At location %d,\n\n", lossLoc);
            PrintHelpers.printRed(out, String.format("%s\n\n", StringHelpers.snippet(userInput, lossLoc, SNIPPET_BEFORE_AFTER, SNIPPET_BEFORE_AFTER)));
            out.print("should be\n\n");
            PrintHelpers.printGreen(out, String.format("%s\n\n", StringHelpers.snippet( passage.fullText(), lossLoc, SNIPPET_BEFORE_AFTER, SNIPPET_BEFORE_AFTER )));
        }

        // 5. Print the correct text.

        out.printf("Full text: \"%s\"\n\n", passage.fullText());
    }
}
