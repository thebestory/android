/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.Stories;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHotStories implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        String url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(UrlBox.storyWay).appendEncodedPath("hot")
                .appendQueryParameter("after", Integer.toString(args.getInt("after", 0)))
                .appendQueryParameter("before", Integer.toString(args.getInt("before", 0)))
                .appendQueryParameter("limit", Integer.toString(args.getInt("limit", 0)))
                .toString();

        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        return connection;
    }
}
