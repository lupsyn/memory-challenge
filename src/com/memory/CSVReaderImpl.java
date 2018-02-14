package com.memory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReaderImpl implements CSVReader {
    private final String separator;
    private final FileReader fileReader;
    private BufferedReader bufferedReader;

    public CSVReaderImpl(String filePath, String separator) throws FileNotFoundException {
        if (filePath == null || separator == null) {
            throw new IllegalArgumentException("Arguments should not be null!");
        }
        this.separator = separator;
        this.fileReader = new FileReader(filePath);
        this.bufferedReader = new BufferedReader(fileReader);
    }

    @Override
    public boolean read(String[] columns) throws IOException {
        return genericRead(columns);
    }

    private boolean genericRead(String[] columns) throws IOException {
        final int FIRST_COLUMN = 0;
        final int SECOND_COLUMN = 1;

        if (columns == null || columns.length < 2) {
            throw new IOException("Support array is not to right size to accept the split the line");
        }
        String line;
        String[] splitLine;

        line = readLine();
        if (line != null) {
            splitLine = line.split(separator);
            if (splitLine.length > 0) {
                columns[0] = splitLine[FIRST_COLUMN];
                columns[1] = splitLine[SECOND_COLUMN];
                return true;
            }
        }
        columns[0] = null;
        columns[1] = null;
        return false;
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
        fileReader.close();
    }

    private String readLine() throws IOException {
        return bufferedReader.readLine();
    }
}
