package com.loomsystems.logs.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WriteLogResults implements IWriteLogResults {
    private String outputFileName;

    public WriteLogResults(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    @Override
    public void writeResult(List<String> resultList) throws IOException {
        Files.write(Paths.get(outputFileName), resultList, StandardOpenOption.CREATE);
    }
}
