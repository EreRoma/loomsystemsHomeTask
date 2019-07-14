import com.loomsystems.logs.input.ReaderLog;
import com.loomsystems.logs.output.WriteLogResults;
import com.loomsystems.logs.service.LogFileProcessor;
import com.loomsystems.logs.service.LogsProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestAppl {
    private static final String INPUT_LOG_FILE = "inLog.log";
    private static final String OUTPUT_PROCESSED_LOG_FILE = "processedLog.log";
    private static final String CORRECT_INPUT_LINE = "01-01-2017 19:45:00 Naomi is getting into the car";
    private static final String INCORRECT_INPUT_LINE = "01!01!2017 19:45:00 Naomi is getting into the car";
    static final String OUTPUT_ERROR_LINE_FILE = "errorLines.log";

    WriteLogResults writeLogResults;
    WriteLogResults writeErrorLines;
    LogsProcessor logsProcessor;
    ReaderLog readerLog;
    LogFileProcessor logFileProcessor;
    List<String> simpleList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.writeLogResults = new WriteLogResults(OUTPUT_PROCESSED_LOG_FILE);
        this.writeErrorLines = new WriteLogResults(OUTPUT_ERROR_LINE_FILE);
        this.logsProcessor = new LogsProcessor();
        this.readerLog = new ReaderLog(INPUT_LOG_FILE);
        this.logFileProcessor = new LogFileProcessor(writeLogResults, writeErrorLines, logsProcessor, readerLog);
        simpleList.add("first word");
        simpleList.add("second word");
    }

    @Test()
    public void testReader() throws FileNotFoundException {
        ReaderLog readerLog1 = new ReaderLog("f:\\wrongName.log");
        ReaderLog readerLog2 = new ReaderLog(INPUT_LOG_FILE);
        assertThrows(FileNotFoundException.class, () -> readerLog1.readByline());
        assertDoesNotThrow(() -> readerLog2.readByline());
    }

    @Test()
    public void testWriter() throws IOException {
        WriteLogResults writeLogResults1 = new WriteLogResults("f:\\wrongName.log");
        WriteLogResults writeLogResults2 = new WriteLogResults(OUTPUT_PROCESSED_LOG_FILE);
        assertThrows(IOException.class, () -> writeLogResults1.writeResult(simpleList));
        assertDoesNotThrow(() -> writeLogResults2.writeResult(simpleList));
    }

    @Test()
    public void testLogFileProcessor() {
        assertThrows(NullPointerException.class, () -> new LogFileProcessor(null, writeErrorLines, this.logsProcessor,
                this.readerLog));
        assertThrows(NullPointerException.class, () -> new LogFileProcessor(writeLogResults, writeErrorLines, null,
                this.readerLog));
        assertThrows(NullPointerException.class, () -> new LogFileProcessor(writeLogResults, writeErrorLines, this.logsProcessor,
                null));
    }

    @Test
    public void testLogsProcessor() throws IOException {
        logsProcessor.processLine(CORRECT_INPUT_LINE);
        assertEquals(1, logsProcessor.getPatternMap().size());
        assertEquals(0, logsProcessor.getWrongFormatLines().size());
        logsProcessor.processLine(INCORRECT_INPUT_LINE);
        assertEquals(1, logsProcessor.getPatternMap().size());
        assertEquals(1, logsProcessor.getWrongFormatLines().size());
        assertTrue(logsProcessor.getResults().get(0).equals(CORRECT_INPUT_LINE));
        assertTrue(logsProcessor.getWrongFormatLines().get(0).equals(INCORRECT_INPUT_LINE));
    }
}
