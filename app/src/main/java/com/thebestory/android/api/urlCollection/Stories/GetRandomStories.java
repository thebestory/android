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

public class GetRandomStories implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        Uri.Builder url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(UrlBox.storyWay).appendPath("random");
        if (args.containsKey("after")) {
            url.appendQueryParameter("after", args.getString("after", ""));
        } else if (args.containsKey("before")) {
            url.appendQueryParameter("before", args.getString("before", ""));
        } else if (args.containsKey("around")) {
            url.appendQueryParameter("around", args.getString("around", ""));
        }
        url.appendQueryParameter("limit", Integer.toString(args.getInt("limit", 0)));

        HttpURLConnection connection = (HttpURLConnection) (new URL(url.toString())).openConnection();
        return connection;
    }
}
