package net.ahuffman;

import net.ahuffman.passage.Passage;
import net.ahuffman.passage.StringPassage;

import java.io.File;
import java.util.Scanner;

public class Test {
    public static Passage load(Scanner s) {
        String name;
        String loadPath;

        System.out.println("Enter the name of the passage to load:");
        System.out.print(">>> ");
        name = s.nextLine();

        loadPath = name + ".dat";

        Passage p = new StringPassage(name, new File(loadPath));

        System.out.println("Echo:");
        System.out.println(p.getTitle());
        System.out.println(p.fullText());
        for (String tag : p.getTags()) {
            System.out.print(tag);
            System.out.print(" ");
        }

        return p;
    }

    public static Passage save(Scanner s) {
        String name;
        String passageText;
        String savePath;
        String input = null;

        System.out.println("Enter the name of your new passage.");
        System.out.print(">>> ");
        name = s.nextLine();

        System.out.println("Enter the text of your new passage.");
        System.out.print(">>> ");
        passageText = s.nextLine();



        savePath = name + ".dat";

        Passage p = new StringPassage(name, passageText);

        System.out.println("Enter the tags for the new passage (one per line, empty line to finish)");
        System.out.print(">>> ");
        input = s.nextLine();

        while (!input.equals("")) {
            p.getTags().addTag(input);
            input = s.nextLine();
        }

        p.saveToFile(new File(savePath));

        Passage load = new StringPassage(name, new File(savePath));
        System.out.println("Echo:");
        System.out.println(load.getTitle());
        System.out.println(load.fullText());

        return p;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Passage p;

        p = load(s);
        //p = save(s);
    }
}
