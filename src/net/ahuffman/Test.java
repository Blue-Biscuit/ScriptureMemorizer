package net.ahuffman;

import net.ahuffman.passage.Passage;
import net.ahuffman.passage.StringPassage;

import java.io.File;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String name;
        String passageText;
        String savePath;

        System.out.println("Enter the name of your new passage.");
        System.out.print(">>> ");
        name = s.nextLine();

        System.out.println("Enter the text of your new passage.");
        System.out.print(">>> ");
        passageText = s.nextLine();

        System.out.println("Enter the filepath to save to.");
        System.out.print(">>> ");
        savePath = s.nextLine();

        Passage p = new StringPassage(name, passageText);
        p.saveToFile(new File(savePath));

        Passage load = new StringPassage(name, new File(savePath));
        System.out.println("Echo:");
        System.out.println(load.getTitle());
        System.out.println(load.fullText());
    }
}
