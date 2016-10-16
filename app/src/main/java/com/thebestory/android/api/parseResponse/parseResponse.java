package com.thebestory.android.api.parseResponse;

/**
 * Created by Alex on 16.10.2016.
 */

public interface parseResponse<T> {
    T parse (String response);
}
