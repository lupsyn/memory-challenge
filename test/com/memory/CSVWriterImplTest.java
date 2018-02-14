package com.memory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CSVWriterImplTest {

    private final String testInputFile = "testdata.csv";
    private CSVWriterImpl writer;
    private final String SEPARATOR = "\t";

    @BeforeEach
    void setUp() {
        try {
            String filePath = this.getClass().getClassLoader().getResource(testInputFile).getFile();
            writer = new CSVWriterImpl(filePath, SEPARATOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void shouldConstructorThrowExceptionIfParametersAreNull() {
        assertThrows(IllegalArgumentException.class, () -> new CSVWriterImpl(null, null));
    }

    @Test
    void shouldWriteAndFlush() {
        try {
            writer.write("ciccio", "pasticcio");
            writer.write("ciccino", "pasticcino");
            writer.close();

            BufferedReader input = new BufferedReader(new FileReader(this.getClass().getClassLoader().getResource(testInputFile).getFile()));
            String last = null, line;
            String columns[] = new String[2];
            while ((line = input.readLine()) != null) {
                last = line;
            }
            if (last != null) {
                columns = last.split(SEPARATOR);
            }
            assertEquals(columns[0], "ciccino");
            assertEquals(columns[1], "pasticcino");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}