package com.fr.refactor.utils;

public class AppConstants {
    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "frabbi_mvvm.db";

    public static final long NULL_INDEX = -1L;

    public static final String PREF_NAME = "frabbi_pref";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final String STATUS_CODE_SUCCESS = "success";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    //for blow 400 retrofit will throw Exception so using an unused code
    public static final int ERROR_CODE = 419;

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
