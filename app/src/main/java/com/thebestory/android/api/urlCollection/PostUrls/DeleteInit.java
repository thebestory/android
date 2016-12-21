/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.PostUrls;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

final class DeleteInit {
    private DeleteInit() {
        throw new AssertionError();
    }

    public static void init(HttpURLConnection connection) throws IOException {
        connection.connect();
    }
}
