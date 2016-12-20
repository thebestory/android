/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.PostUrls;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.urlCollection.UrlBox;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

final class PostLikeFactory {
    private PostLikeFactory() {
        throw new AssertionError();
    }

    public static HttpURLConnection getLikeConnection(String way, boolean isLike, Bundle args) throws IOException {
        //TODO: Add more information
        String url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(way).appendPath(args.getString("id", "")).appendPath(UrlBox.like).toString();
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        String postArgs = "";
        connection.setRequestMethod(isLike ? "POST" : "DELETE");
        PostInit.init(connection, postArgs);
        return connection;
    }
}
