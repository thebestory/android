/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseUrlRequest;

import android.os.Bundle;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface ParseUrl {
    HttpURLConnection parse(Bundle args) throws IOException;
}
