/*
 * The Bestory Project
 */

package com.thebestory.android.api;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.JsonReader;
import android.util.Log;

import com.thebestory.android.api.parseResponse.ParseResponse;
import com.thebestory.android.api.parseResponse.ParseResponseStatus;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.TypeOfCollection;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class ApiAsyncTask<T> extends AsyncTaskLoader<LoaderResult<T>> {

    private final ParseUrl parseUrlRequest;
    private final ParseResponse<T> parseResponse;
    private Bundle args;
    private LoaderResult<T> loaderResult;
    private TypeOfCollection cashType = null;

    public TypeOfCollection getRequestType() {
        if (cashType != null) {
            return cashType;
        }

        if (args == null) {
            cashType = TypeOfCollection.NONE;
            return cashType;
        }

        if (args.containsKey("after")) {
            cashType = TypeOfCollection.AFTER;
            return cashType;
        }

        if (args.containsKey("before")) {
            cashType = TypeOfCollection.BEFORE;
            return cashType;
        }

        if (args.containsKey("around")) {
            cashType = TypeOfCollection.AROUND;
            return cashType;
        }
        cashType = TypeOfCollection.NONE;
        return cashType;
    }

    public ApiAsyncTask(Context context, ParseUrl parseUrlRequest, ParseResponse<T> parseResponse) {
        this(context, parseUrlRequest, parseResponse, null);
    }

    public ApiAsyncTask(Context context, ParseUrl parseUrlRequest, ParseResponse<T> parseResponse, Bundle args) {
        super(context);
        this.parseUrlRequest = parseUrlRequest;
        this.parseResponse = parseResponse;
        this.args = args;
    }

    @Override
    public void onStartLoading() {
        if (loaderResult == null) {
            forceLoad();
        } else {
            deliverResult(loaderResult);
        }
    }

    @Override
    public LoaderResult<T> loadInBackground() {
        try {
            HttpURLConnection urlConnection = parseUrlRequest.parse(args);

            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            Log.d("Api async Task", urlConnection.getURL().toString());

            JsonReader jr = new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
            LoaderStatus status = ParseResponseStatus.parse(jr);
            if (status == LoaderStatus.ERROR) {
                loaderResult = new LoaderResult<>(status);
                return loaderResult;
            }

            T response = parseResponse.parse(jr);
            loaderResult = new LoaderResult<>(status, response);
            return loaderResult;

        } catch (Exception error) {
            error.printStackTrace();
            Log.e("Api async Task", error.toString());
        }
        loaderResult = new LoaderResult<>(LoaderStatus.ERROR);
        return loaderResult;
    }

}
