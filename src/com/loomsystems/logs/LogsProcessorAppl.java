package com.loomsystems.logs;

import com.loomsystems.logs.input.IReaderLog;
import com.loomsystems.logs.input.ReaderLog;
import com.loomsystems.logs.output.IWriteLogResults;
import com.loomsystems.logs.output.WriteLogResults;
import com.loomsystems.logs.service.ILogFileProcessor;
import com.loomsystems.logs.service.ILogsProcessor;
import com.loomsystems.logs.service.LogFileProcessor;
import com.loomsystems.logs.service.LogsProcessor;

import java.io.IOException;

public class LogsProcessorAppl {
    static final String INPUT_LOG_FILE = "inLog.log";
    static final String OUTPUT_PROCESSED_LOG_FILE = "processedLog.log";
    static final String OUTPUT_ERROR_LINE_FILE = "errorLines.log";

    public static void main(String[] args) {
        IWriteLogResults writeLogResults = new WriteLogResults(OUTPUT_PROCESSED_LOG_FILE);
        IWriteLogResults writeErrorLines = new WriteLogResults(OUTPUT_ERROR_LINE_FILE);
        ILogsProcessor logsProcessor = new LogsProcessor();
        IReaderLog readerLog = new ReaderLog(INPUT_LOG_FILE);
        ILogFileProcessor logFileProcessor = new LogFileProcessor(writeLogResults, writeErrorLines, logsProcessor, readerLog);
        try {
            logFileProcessor.processLogs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
