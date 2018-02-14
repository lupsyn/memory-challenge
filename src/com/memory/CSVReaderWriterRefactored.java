package com.memory;

/**
 * Your task:
 * Refactor the CSVReaderWriter implementation into clean, elegant, rock-solid & well performing code, without over-engineering.
 */

import java.io.IOException;
import java.util.concurrent.*;

public class CSVReaderWriterRefactored {
    private CSVReader reader;
    private CSVWriter writer;
    private static final int N_THREADS = 10;
    private static final int CORE_POOL_SIZE = 2;
    private static final long KEEP_ALIVE_TIME = 0L;
    private BlockingQueue<Runnable> queue = new SynchronousQueue<>();
    private ExecutorService executor;
    private final static String SEPARATOR = "\t";

    public enum Mode {READ, WRITE}

    //Multiple exception are handled from IOException
    public void open(String fileName, Mode mode) throws IOException {
        if (executor == null) {
            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, N_THREADS, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, queue);
        }
        if (mode == Mode.READ) {
            reader = new CSVReaderImpl(fileName, SEPARATOR);
        } else if (mode == Mode.WRITE) {
            writer = new CSVWriterImpl(fileName, SEPARATOR);
        } else {
            throw new IllegalArgumentException("Unknown file mode for " + fileName);
        }
    }

    public synchronized void write(String... columns) throws IOException {
        if (writer == null) {
            throw new IOException("Open the writer before try to read!");
        }
        //Here the rejected execution exception is not implemented because out of time
        //thread operation could be considered very fast (I/O operation) anyway,
        //if there is not place in the queue the execute can call RejectedExecutionException and we
        //need to handle the logic manually
        executor.execute(() -> {
            try {
                writer.write(columns);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean read(String[] columns) throws ExecutionException, InterruptedException {
        Future<Boolean> toReturn = executor.submit(() -> reader.read(columns));
        return toReturn.get();
    }

    //Values passed by reference are not valid in Java.
    @Deprecated
    public boolean read(String column1, String column2) throws IOException {
        throw new IOException("String in java are passed by value, not by reference as C# son column1 and 2 will be never modified!");
    }

    public void close() throws IOException {
        closeWriter();
        closeReader();
        closeQueue();
    }

    private void closeQueue() {
        if (reader == null && writer == null) {
            executor.shutdown();
        }
    }

    private void closeReader() throws IOException {
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }

    private void closeWriter() throws IOException {
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }
}
