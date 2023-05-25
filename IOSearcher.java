package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOSearcher {

    public static boolean search(String word, String... fileNames) {
        for (String fileName : fileNames) {
            try {
                File file = new File(fileName);
                if (file.exists() && file.canRead()) {
                    Scanner scnr = new Scanner(file);
                    while (scnr.hasNextLine() == true) {
                        String line = scnr.nextLine();
                        if (line.contains(word)) {
                            scnr.close();
                            return true;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Given file name doesnt exist");
            }
        }
        return false;
    }
}