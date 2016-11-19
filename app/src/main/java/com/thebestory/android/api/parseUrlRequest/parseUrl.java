package com.thebestory.android.api.parseUrlRequest;

import android.os.Bundle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Created by Alex on 16.10.2016.
 */

public interface ParseUrl {
    HttpURLConnection parse(Bundle args) throws IOException;
}
