package com.thebestory.android.api.urlCollection.PostUrls;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 02.12.2016.
 */

public class PostStory implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        String url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(UrlBox.storyWay).toString();
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        String postArgs = "{\"topic\": " + args.getString("id", "") + ", \"content\": " + args.getString("content", "") + "}";
        connection.setRequestMethod("POST");
        PostInit.init(connection, postArgs);
        return connection;
    }
}
