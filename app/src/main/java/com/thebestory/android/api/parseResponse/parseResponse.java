package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by Alex on 16.10.2016.
 */

public interface ParseResponse<T> {
    T parse (JsonReader response) throws IOException;
}
