/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.Stories;

import android.os.Bundle;

import com.thebestory.android.api.parseUrlRequest.ParseUrl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubmitStory implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) (new URL("")).openConnection();
        return connection;
    }
}
