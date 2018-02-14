package com.memory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriterImpl implements CSVWriter {
    private final BufferedWriter bufferedWriter;
    private final FileWriter fileWriter;
    private final String separator;
    private final String filePath;

    public CSVWriterImpl(String filePath, String separator) throws IOException {
        if (filePath == null || separator == null) {
            throw new IllegalArgumentException("Arguments should not be null!");
        }
        this.separator = separator;
        this.filePath = filePath;
        this.fileWriter = new FileWriter(filePath);
        this.bufferedWriter = new BufferedWriter(fileWriter);
    }

    @Override
    public void close() throws IOException {
        bufferedWriter.close();
        fileWriter.close();
    }

    @Override
    public synchronized void write(String... columns) throws IOException {
        if (!isFileEmpty(filePath)) {
            bufferedWriter.write("\n");
        }
        bufferedWriter.write(String.join(separator, columns));
        bufferedWriter.flush();
    }

    private boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return (file.length() == 0);
    }
}
