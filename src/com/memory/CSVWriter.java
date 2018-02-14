package com.memory;

import java.io.IOException;

public interface CSVWriter extends CSVOperation {
    void write(String... columns) throws IOException, InterruptedException;
}
