package com.thebestory.android.api;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import com.thebestory.android.api.parseResponse.ParseResponse;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Alex on 16.10.2016.
 */

class ApiAsyncTask<T> extends AsyncTaskLoader<LoaderResult<T>> {

    private final ParseUrl parseUrlRequest;
    private final ParseResponse<T> parseResponse;
    private Bundle args;

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
    public LoaderResult<T> loadInBackground() {
        try {
            Log.e("WRONG","Start1");
            HttpURLConnection urlConnection = parseUrlRequest.parse(args);
            Log.e("WRONG","Start2");
            JsonReader jr = new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
            Log.e("WRONG","Start3");
            T response = parseResponse.parse(jr);
            Log.e("WRONG","Start4");
            return new LoaderResult(LoaderStatus.OK, response);
        } catch (Exception error) {
            Log.e("WRONG", error.toString());
        }
        return new LoaderResult(LoaderStatus.ERROR, null);
    }

}
