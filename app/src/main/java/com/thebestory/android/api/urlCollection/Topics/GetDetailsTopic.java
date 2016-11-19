package com.thebestory.android.api.urlCollection.Topics;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 04.11.2016.
 */

public class GetDetailsTopic implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        String url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(UrlBox.topicWay).appendEncodedPath(Integer.toString(args.getInt("id", 0))).toString();
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        return connection;
    }
}
