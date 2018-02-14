package com.memory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVReaderImplTest {

    private String testInputFile = "testdata.csv";
    private CSVReaderImpl reader;
    final String SEPARATOR = "\t";

    @BeforeEach
    void setUp() {
        try {
            String filePath = this.getClass().getClassLoader().getResource(testInputFile).getFile();
            reader = new CSVReaderImpl(filePath, SEPARATOR);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldConstructorThrowExceptionIfParametersAreNull() {
        assertThrows(IllegalArgumentException.class, () -> new CSVReaderImpl(null, null));
    }

    @Test
    void shouldReadMethodReadTwoColumsFromTestData() {
        String[] columns = new String[2];
        try {
            reader.read(columns);
            assertEquals(columns[0], "ciccio");
            assertEquals(columns[1], "pasticcio");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldReadMethodThrowExceptionIfSupportArrayIsNotInitializated() {
        String[] columns = null;
        assertThrows(IOException.class, () -> reader.read(columns));
    }
}