/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import java.io.IOException;
import java.text.ParseException;

public interface ParseResponse<T> {
    T parse(JsonReader response) throws IOException, ParseException;
}
