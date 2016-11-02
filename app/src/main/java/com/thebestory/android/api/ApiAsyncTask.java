package com.thebestory.android.api;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.thebestory.android.api.parseResponse.ParseResponse;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;

/**
 * Created by Alex on 16.10.2016.
 */

class ApiAsyncTask<T> extends AsyncTaskLoader<T> {

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
    public T loadInBackground() {
        String urlRequest = parseUrlRequest.parse(args);

        String jsonResponse = null; //TODO: request to urlRequest

        T response = parseResponse.parse(jsonResponse);
        return response;
    }

}
