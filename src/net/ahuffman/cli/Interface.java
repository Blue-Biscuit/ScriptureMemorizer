package net.ahuffman.cli;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    public static void loadCommands(ArrayList<Command> commands) {
        commands.add(new NewPassageCommand());
        commands.add(new ExitCommand());
        commands.add(new PrintCommand());
        commands.add(new HelpCommand());
        commands.add(new SaveCommand());
        commands.add(new LoadCommand());
        commands.add(new LearnCommand());
        commands.add(new BlankCommand());
    }

    public static String getInput(String promptPattern, Scanner s) {
        // Get input.
        System.out.print(promptPattern);
        String input = s.nextLine();

        // Clean input.
        input = input.trim();

        return input;
    }

    public static void main(String[] args) {
        ArrayList<Command> commands = new ArrayList<>();
        Scanner s;
        PassagesList passagesList;
        final String encoding;

        // Setup scanner.
        encoding = "UTF-8";
        s = new Scanner(System.in, encoding);
        try {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, encoding));
        } catch (Exception ignored) {
            System.out.println("didn't work");
        }

        // Load the commands.
        loadCommands(commands);

        // Setup list of passages.
        passagesList = new PassagesList();

        // Initialize the currently loaded command; this should be immediately overridden.
        Command dummy = new NewPassageCommand();
        Command command = dummy;

        // Command loop.
        while (!command.getName().equals("exit")) {
            UserInput input = new UserInput(getInput(">>> ", s));

            if (input.getCommand().equals("λογος")) {
                System.out.println("Can input unicode");
            }


            // Get the command.
            command = null;
            for (Command e : commands) {
                if (e.getName().equals(input.getCommand())) {
                    command = e;
                    break;
                }
            }

            // Run commands.
            try {
                if (command == null) {
                    System.out.printf("Unknown command: %s\n\n", input.getCommand());
                    command = dummy;
                }
                else if (command.getName().equals("print")) {
                    command.execute(input.getArgs(), new Object[]{System.out, passagesList});
                }
                else if (command.getName().equals("learn") || command.getName().equals("blank")) {
                    command.execute(input.getArgs(), new Object[]{passagesList, System.out, s});
                }
                else if (command.getName().equals("help")) {
                    command.execute(input.getArgs(), new Object[]{System.out, commands});
                }
                else {
                    command.execute(input.getArgs(), new Object[]{passagesList});
                }
                System.out.println();
            }
            catch (InvalidCommandOperationException e) {
                System.out.printf("%s\n\n", e.getMessage());
                command = dummy;
            }
        }
    }
}
