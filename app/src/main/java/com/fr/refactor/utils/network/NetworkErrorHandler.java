package com.fr.refactor.utils.network;

import android.content.Context;

import com.fr.refactor.R;
import com.fr.refactor.utils.AppConstants;


import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

public class NetworkErrorHandler {
    private static String getErrorMessage(Throwable throwable, Context context, String message) {
        if (throwable instanceof java.net.SocketTimeoutException || throwable instanceof java.net.ConnectException) {
            return context.getResources().getString(R.string.could_not_connect_to_server);
        }
        return message;
    }

    private static void logThrowable(Throwable throwable) {
        if (throwable instanceof java.net.SocketTimeoutException || throwable instanceof java.net.ConnectException) {
            return;
        }
        Timber.e(throwable);
    }

    public static Response handleThrowable(Throwable throwable, Context context, String message) {
        logThrowable(throwable);
        return Response.error(AppConstants.ERROR_CODE
                , ResponseBody.create(null
                        , NetworkErrorHandler.getErrorMessage(throwable, context, message)));
    }

    public static void logUnsuccessfulResponse(Response response) throws Throwable {
        String errorString = response.errorBody().string();
        int statusCode = response.code();
        Timber.e("Statuscode: %s Error: '%s'", statusCode, errorString);
    }
}
