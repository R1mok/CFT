package org.example;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    private File file;

    public WriteToFile(File file) {
        this.file = file;
    }

    public void write(String string) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(string + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
