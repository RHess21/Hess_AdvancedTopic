package com.example;

import java.io.IOException;
import java.util.logging.*;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/*
 * Utility class to log errors to a file.
 * Logger is used to print custom messages along with the file location to the log.txt file
 */
public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());

    static {
        try {
            // Configure file handler
            FileHandler fileHandler = new FileHandler("src\\main\\resources\\Log.txt", true);
            fileHandler.setFormatter(new SingleLineFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Disable console logging
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }
    //Method that gets called to write the errors
    public static void logError(Exception e, String customMessage) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace.length > 0) {
            StackTraceElement firstElement = stackTrace[0]; // Get the first stack trace element
            
            //Format the log message adding the class, filename, and line number of any error
            String logMessage = String.format(
                    "%s in %s:%d - %s",
                    e.getClass().getSimpleName(),
                    firstElement.getFileName(),
                    firstElement.getLineNumber(),
                    customMessage);

            logger.severe(logMessage);
        }
    }
}

// Custom formatter to ensure single-line log format
//My pickiness wanted just a one liner per log entry, so had to use a formatter to achieve that.
class SingleLineFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getMessage() + System.lineSeparator();
    }
}
