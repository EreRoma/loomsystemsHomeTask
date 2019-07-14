package com.loomsystems.logs.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

public class ReaderLog implements IReaderLog {
    private String filename;

    public ReaderLog(String inputFilename) {
        this.filename = inputFilename;
    }

    @Override
    public Stream<String> readByline() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        return bufferedReader.lines();
    }
}
