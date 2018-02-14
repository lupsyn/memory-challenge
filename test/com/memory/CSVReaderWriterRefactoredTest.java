package com.memory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderWriterRefactoredTest {

    private final String testInputFile = "testdataGlobal.csv";
    private CSVReaderWriterRefactored readerWriter;
    final String SEPARATOR = "\t";
    private String filePath = "";

    @BeforeEach
    void setUp() {
        readerWriter = new CSVReaderWriterRefactored();
        filePath = this.getClass().getClassLoader().getResource(testInputFile).getFile();
        try {
            readerWriter.open(filePath, CSVReaderWriterRefactored.Mode.WRITE);
            readerWriter.open(filePath, CSVReaderWriterRefactored.Mode.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            readerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldThrowException() {
        assertThrows(IOException.class, () -> readerWriter.read("Ciccio", "Pasticcio"));
    }

    @Test
    void shouldWriteAndRead() {
        String random = UUID.randomUUID().toString();
        String[] result = new String[2];
        try {
            readerWriter.write("test", random);
            readerWriter.write("testino", random);
            readerWriter.read(result);
            assertEquals(result[0], "test");
            assertEquals(result[1], random);
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldWriteFromTwoThreadsWithoutConflicts() throws IOException {
        String randomOne = UUID.randomUUID().toString();
        String randomTwo = UUID.randomUUID().toString();
        String randomThree = UUID.randomUUID().toString();

        String[] result = new String[2];

        readerWriter.write("test", randomOne);
        readerWriter.write("test2", randomTwo);
        readerWriter.write("test3", randomThree);

        try {

            readerWriter.read(result);
            readerWriter.read(result);
            readerWriter.read(result);

            assertNotNull(result[0]);
            assertNotNull(result[1]);

            assertEquals(result[0], "test3");
            assertEquals(result[1], randomThree);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

