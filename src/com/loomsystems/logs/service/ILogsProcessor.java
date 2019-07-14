package com.loomsystems.logs.service;

import java.util.List;

public interface ILogsProcessor {
    void processLine(String logLine);
    List<String> getResults();
    List<String> getWrongFormatLines();
}
