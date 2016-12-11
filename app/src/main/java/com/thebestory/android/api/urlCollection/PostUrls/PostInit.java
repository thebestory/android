/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.PostUrls;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

final class PostInit {
    private PostInit() {
        throw new AssertionError();
    }

    public static void init(HttpURLConnection connection, String params) throws IOException {
        connection.setDoOutput(true);
        connection.setDoInput(true);
        OutputStream os = connection.getOutputStream();
        os.write(params.getBytes("UTF-8"));
        connection.connect();
    }
}
