package net.ahuffman.cli;

import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    public static void loadCommands(ArrayList<Command> commands) {
        commands.add(new NewPassageCommand());
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

            c = null;
            for (Command e : commands) {
                if (e.getName().equals(input.getCommand())) {
                    c = e;
                    break;
                }
            }

            if (c == null) {
                System.out.printf("Unknown command: %s\n\n", input.getCommand());
                c = dummy;
            }
            else {
                c.execute(input.getArgs(), p);
            }
        }
    }
}
