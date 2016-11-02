package com.thebestory.android.api;

/**
 * Created by Alex on 02.11.2016.
 */

public class LoaderResult<T> {

    public final T data;
    public final LoaderStatus status;

    public LoaderResult(LoaderStatus status) {
        this(status, null);
    }

    public LoaderResult(LoaderStatus status, T data) {
        this.data = data;
        this.status = status;
    }
}
