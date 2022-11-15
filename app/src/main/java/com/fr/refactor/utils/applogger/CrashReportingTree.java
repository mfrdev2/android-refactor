package com.fr.refactor.utils.applogger;

import android.util.Log;

import androidx.annotation.NonNull;

import timber.log.Timber;

public final class  CrashReportingTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        ErrorReportingUtils.log(priority, tag, message);

        if (t != null) {
            if (priority == Log.ERROR) {
                ErrorReportingUtils.logError(t);
            } else if (priority == Log.WARN) {
                ErrorReportingUtils.logWarning(t);
            }
        }
    }
}
