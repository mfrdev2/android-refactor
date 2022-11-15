package com.fr.refactor.utils.applogger;
/**
 * Created by FRabbi on 19-May-2022.
 */
public class ErrorReportingUtils {
    public static void log(int priority, String tag, String message) {
        // TODO add log entry to circular buffer.
    }

    public static void logWarning(Throwable t) {
        // TODO report non-fatal warning.
    }

    public static void logError(Throwable t) {
        // TODO report non-fatal error.
    }

    private ErrorReportingUtils() {
        throw new AssertionError("No instances.");
    }
}
