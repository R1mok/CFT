package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFromFile {
    public boolean EOF;
    private String currentString;
    private Scanner scanner;

    public ReadFromFile(File file) {
        try {
            this.scanner = new Scanner(file);
            if (scanner.hasNext()) {
                currentString = scanner.nextLine();
            } else {
                System.out.println("File is empty");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + file.getName() + " not found");
            EOF = true;
        }
    }

    public String getCurrentString() {
        return currentString;
    }

    public String toNextString() {
        String previousString = getCurrentString();
        if (scanner.hasNext()) {
            this.currentString = scanner.nextLine();
        } else {
            EOF = true;
        }
        return previousString;
    }
}
