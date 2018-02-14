package com.memory;

import java.io.IOException;

public interface CSVReader extends CSVOperation {
    boolean read(String[] columns) throws IOException;
}
