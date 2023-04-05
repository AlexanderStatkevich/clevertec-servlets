package com.statkevich.receipttask.reader;

import com.statkevich.receipttask.dto.InputValuesDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader implements Reader {

    public static final String FILE = "file";
    private final ConsoleReader consoleReader;

    public FileReader(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    @Override
    public InputValuesDto read(List<String> orderList) {
        try {
            String parameterFile = orderList.stream()
                    .filter(row -> row.startsWith(FILE))
                    .map(row -> row.split("-")[1])
                    .findAny()
                    .orElseThrow(IllegalArgumentException::new);

            java.io.FileReader fileReader = new java.io.FileReader(parameterFile);
            Scanner scan = new Scanner(fileReader);
            List<String> args = new ArrayList<>();
            while (scan.hasNextLine()) {
                args.add(scan.nextLine());
            }
            fileReader.close();
            return consoleReader.read(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
