package com.loomsystems.logs.service;

import com.loomsystems.logs.input.IReaderLog;
import com.loomsystems.logs.output.IWriteLogResults;

import java.io.IOException;

public class LogFileProcessor implements ILogFileProcessor {
    private IWriteLogResults writeLogResults;
    private ILogsProcessor logsProcessor;
    private IReaderLog readerLog;

    public LogFileProcessor(IWriteLogResults writeLogResults, ILogsProcessor logsProcessor, IReaderLog readerLog) {
        if (validate(writeLogResults, logsProcessor, readerLog)) {
            this.writeLogResults = writeLogResults;
            this.logsProcessor = logsProcessor;
            this.readerLog = readerLog;
        } else throw new NullPointerException("Argument can't be NULL");
    }

    private boolean validate(IWriteLogResults writeLogResults, ILogsProcessor logsProcessor, IReaderLog readerLog) {
        return writeLogResults != null && logsProcessor != null && readerLog != null;
    }

    public void processLogs() throws IOException {
        readerLog.readByline()
                .forEach(logsProcessor::processLine);
        writeLogResults.writeResult(logsProcessor.getResults());
    }
}
