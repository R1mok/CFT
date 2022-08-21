package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Sorter {
    private Map<ReadFromFile, String> buffer = new HashMap<>();

    Map<ReadFromFile, Integer> getIntegerMap(Map<ReadFromFile, String> stringMap) {
        Map<ReadFromFile, Integer> resultMap;
        try {
            resultMap = stringMap.entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey, entry -> Integer.parseInt(entry.getValue())
            ));
        } catch (NumberFormatException e) {
            System.out.println("Can`t recognize integer from file");
            throw new RuntimeException(e.getMessage());
        }
        return resultMap;
    }

    void sort() {
        for (ReadFromFile file : Main.inputFiles) {
            if (!file.EOF) {
                buffer.put(file, file.getCurrentString());
            }
        }

        while (!buffer.isEmpty()) {
            Map.Entry<ReadFromFile, ?> entry;
            if (Main.isString) {
                if (Main.isDeskSort) {
                    entry = Collections.max(buffer.entrySet(), Map.Entry.comparingByValue());
                } else {
                    entry = Collections.min(buffer.entrySet(), Map.Entry.comparingByValue());
                }
            } else {
                Map<ReadFromFile, Integer> integerMap = getIntegerMap(buffer);
                if (Main.isDeskSort) {
                    entry = Collections.max(integerMap.entrySet(), Map.Entry.comparingByValue());
                } else {
                    entry = Collections.min(integerMap.entrySet(), Map.Entry.comparingByValue());
                }
            }
            if (!entry.getKey().EOF) {
                Main.outputFile.write(String.valueOf(entry.getValue()));
                String nextElem = entry.getKey().toNextString();
                buffer.put(entry.getKey(), nextElem);
            } else {
                buffer.remove(entry.getKey());
            }
        }
    }
}
