package com.thebestory.android.api;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;

import com.thebestory.android.api.parseResponse.parseResponse;
import com.thebestory.android.api.parseUrlRequest.parseUrl;

/**
 * Created by Alex on 16.10.2016.
 */

final class ApiLoadeCallbad<T> implements LoaderManager.LoaderCallbacks<T> {

    private final ApiAsyncTask<T> task;
    private final CallBack<T> callBackFunc;
    ApiLoadeCallbad(Context context, parseUrl parseUrlRequest, parseResponse<T> parseT, CallBack<T> callBackFunc) {
        this.task = new ApiAsyncTask<T>(context, parseUrlRequest, parseT);
        this.callBackFunc = callBackFunc;
    }

    @Override
    public Loader<T> onCreateLoader(int id, Bundle args) {
        this.task.setBundle(args);
        return this.task;
    }

    @Override
    public void onLoadFinished(Loader<T> loader, T data) {
        callBackFunc.callBack(data);
    }

    @Override
    public void onLoaderReset(Loader<T> loader) {
        //TODO: on loader reset method
    }

}
