package net.ahuffman.cli;

import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    public static void loadCommands(ArrayList<Command> commands) {
        commands.add(new NewPassageCommand());
        commands.add(new ExitCommand());
        commands.add(new PrintCommand());
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
        PassagesList p;

        // Setup scanner.
        s = new Scanner(System.in);

        // Load the commands.
        loadCommands(commands);

        // Setup list of passages.
        p = new PassagesList();

        // Initialize the currently loaded command; this should be immediately overriden.
        Command dummy = new NewPassageCommand();
        Command c = dummy;

        // Command loop.
        while (!c.getName().equals("exit")) {
            UserInput input = new UserInput(getInput(">>> ", s));

            // Get the command.
            c = null;
            for (Command e : commands) {
                if (e.getName().equals(input.getCommand())) {
                    c = e;
                    break;
                }
            }

            // Run commands.
            try {
                if (c == null) {
                    System.out.printf("Unknown command: %s\n\n", input.getCommand());
                    c = dummy;
                } else if (c.getName().equals("print")) {
                    c.execute(input.getArgs(), new Object[]{System.out, p});
                } else {
                    c.execute(input.getArgs(), new Object[]{p});
                }
                System.out.println();
            }
            catch (InvalidCommandOperationException e) {
                System.out.printf("%s\n\n", e.getMessage());
                c = dummy;
            }
        }
    }
}
