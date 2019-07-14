package com.loomsystems.logs.output;

import java.io.IOException;
import java.util.List;

public interface IWriteLogResults {
    void writeResult(List<String> resultList) throws IOException;
}
