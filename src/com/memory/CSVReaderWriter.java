package com.memory;

/*
A developer was tasked with writing a reusable implementation for a mass mailing application to read and write text files that hold tab separated data. He proceeded and as a result produced the CSVReaderWriter class.

His implementation, although it works and meets the needs of the application, is of very low quality.

Your task:
     - Identify and annotate the shortcomings in the current implementation as if you were doing a code review, using comments in the CSVReaderWriter.java file.
     - Refactor the CSVReaderWriter implementation into clean, elegant, rock-solid & well performing code, without over-engineering.
     - Where you make trade offs, comment & explain.
     - Assume this code is in production and backwards compatibility must be maintained. Therefore if you decide to change the public interface,
       please deprecate the existing methods. Feel free to evolve the code in other ways though.
     - You can add modules and jars as you see fit as long as everything will build on our systems without manual intervention.
*/

import java.io.*;

public class CSVReaderWriter {
    private BufferedReader _bufferedReader = null;
    private BufferedWriter _bufferedWriter = null;

    public enum Mode {
        Read (1), Write(2);

        private int _mode;
        Mode(int mode) {
            this._mode = mode;
        }
        public int getMode() {
            return _mode;
        }
    }

    public void open(String fileName, Mode mode) throws Exception {
        if (mode == Mode.Read)
        {
            _bufferedReader = new BufferedReader(new FileReader(fileName));
        }
        else if (mode == Mode.Write)
        {
            FileWriter fileWriter = new FileWriter(fileName);
            _bufferedWriter = new BufferedWriter(fileWriter);
        }
        else
        {
            throw new Exception("Unknown file mode for " + fileName);
        }
    }

    public void write(String... columns) throws IOException {
        String outPut = "";

        for (int i = 0; i < columns.length; i++)
        {
            outPut += columns[i];
            if ((columns.length - 1) != i)
            {
                outPut += "\t";
            }
        }

        writeLine(outPut);
    }

    public boolean read(String[] columns) throws IOException {
        final int FIRST_COLUMN = 0;
        final int SECOND_COLUMN = 1;

        String line;
        String[] splitLine;

        String separator = "\t";

        line = readLine();
        splitLine = line.split(separator);

        if (splitLine.length == 0)
        {
            columns[0] = null;
            columns[1] = null;

            return false;
        }
        else
        {
            columns[0] = splitLine[FIRST_COLUMN];
            columns[1] = splitLine[SECOND_COLUMN];

            return true;
        }
    }

    public boolean read(String column1, String column2) throws IOException {
        final int FIRST_COLUMN = 0;
        final int SECOND_COLUMN = 1;

        String line;
        String[] splitLine;

        String separator = "\t";

        line = readLine();

        if (line == null)
        {
            column1 = null;
            column2 = null;

            return false;
        }

        splitLine = line.split(separator);

        if (splitLine.length == 0)
        {
            column1 = null;
            column2 = null;

            return false;
        }
        else
        {
            column1 = splitLine[FIRST_COLUMN];
            column2 = splitLine[SECOND_COLUMN];

            return true;
        }
    }

    private void writeLine(String line) throws IOException {
        _bufferedWriter.write(line);
    }

    private String readLine() throws IOException {
        return _bufferedReader.readLine();
    }

    public void close() throws IOException {
        if (_bufferedWriter != null)
        {
            _bufferedWriter.close();
        }

        if (_bufferedWriter != null)
        {
            _bufferedWriter.close();
        }
    }
}
