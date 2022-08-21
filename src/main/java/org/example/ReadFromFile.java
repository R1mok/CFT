package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFromFile {
    public boolean EOF;
    private String currentString;
    private Scanner scanner;

    private final File file;

    public ReadFromFile(File file) {
        this.file = file;
        try {
            this.scanner = new Scanner(file);
            if (scanner.hasNext()) {
                currentString = scanner.nextLine();
            } else {
                System.out.println("File is empty");
                EOF = true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + file.getName() + " not found");
            EOF = true;
        }
    }

    public File getFile() {
        return file;
    }

    public String getCurrentString() {
        return currentString;
    }

    public String toNextString() {
        if (scanner.hasNext()) {
            this.currentString = scanner.nextLine();
        } else {
            EOF = true;
        }
        return currentString;
    }
}
