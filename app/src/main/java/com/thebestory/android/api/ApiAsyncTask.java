package com.thebestory.android.api;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.thebestory.android.api.parseResponse.parseResponse;
import com.thebestory.android.api.parseUrlRequest.parseUrl;

/**
 * Created by Alex on 16.10.2016.
 */

class ApiAsyncTask<T> extends AsyncTaskLoader<T> {

    private final parseUrl parseUrlRequest;
    private final parseResponse<T> parseT;
    private Bundle args;

    public ApiAsyncTask(Context context, parseUrl parseUrlRequest, parseResponse<T> parseT) {
        this(context, parseUrlRequest, parseT, null);
    }

    public ApiAsyncTask(Context context, parseUrl parseUrlRequest, parseResponse<T> parseT, Bundle args) {
        super(context);
        this.parseUrlRequest = parseUrlRequest;
        this.parseT = parseT;
        this.args = args;
    }

    @Override
    public T loadInBackground() {
        String urlRequest = parseUrlRequest.parse(args);

        String jsonResponse = null; //TODO: request to urlRequest

        T response = parseT.parse(jsonResponse);
        return response;
    }

    public void setBundle (Bundle args) {
        this.args = args;
    }


}
