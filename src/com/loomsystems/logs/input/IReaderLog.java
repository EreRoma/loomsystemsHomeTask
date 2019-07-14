package com.loomsystems.logs.input;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

public interface IReaderLog {
    Stream<String> readByline() throws FileNotFoundException;
}
