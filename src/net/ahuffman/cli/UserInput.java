package net.ahuffman.cli;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * A simple data structure to handle and parse user input into command and argument.
 */
public class UserInput {
    /**
     * Constructor.
     * @param input The raw user input.
     * @throws NullPointerException If the raw input was null.
     */
    public UserInput(String input) throws NullPointerException {
        int firstSpace;

        // Clean the input.
        if (input == null) {
            throw new NullPointerException("UserInput to constructor cannot be null.");
        }
        else {
            input = input.trim().replaceAll("\\s", " ").replaceAll(" +", " ");
        }

        // Find the first space in the string.
        firstSpace = input.indexOf(' ');

        // If there is no space, then null the arguments portion and set the whole string to "command."
        if (firstSpace == -1) {
            _args = new CommandArgs();
            _command = input;
        }

        // If there is a space, then set that which is before the space as "command" and after as "argument."
        else {
            _command = input.substring(0, firstSpace);
            _args = new CommandArgs(input.substring(firstSpace + 1)); // This shouldn't go out of bounds, since the string was trimmed.
        }
    }

    /**
     * Gets the command.
     * @return The command.
     */
    public String getCommand() {
        return _command;
    }

    /**
     * True if the user input contains arguments.
     * @return True if the user input contains arguments.
     */
    public boolean hasArgs() {
        return _args != null;
    }

    /**
     * Gets the arguments.
     * @return the arguments, or an empty string if the user hasn't provided them.
     */
    public CommandArgs getArgs() {
        return _args;
    }

    @Override
    public String toString() {
        if (hasArgs()) {
            return String.format("%s %s", getCommand(), getArgs());
        }
        else {
            return getCommand();
        }
    }

    /**
     * Test driver.
     * @param args Ignored.
     */
    public static void main(String[] args) {

        BiConsumer<String, Integer> run = (input, testNum) -> {
            System.out.printf("Test %d: input = \"%s\"\n", testNum, input);
            UserInput ui = new UserInput(input);
            System.out.printf("getCommand(): \"%s\"\n", ui.getCommand());
            System.out.printf("getArgs(): \"%s\"\n", ui.getArgs());
            System.out.printf("Finish Test %d.\n\n", testNum);
        };

        run.accept("command arg1 arg2", 1);

        run.accept("command", 2);

        run.accept("  command      arg1       arg2", 3);

        run.accept("", 4);

        run.accept("         ", 5);

        run.accept("command\narg1\narg2", 6);
    }

    private final String _command;
    private final CommandArgs _args;
}
