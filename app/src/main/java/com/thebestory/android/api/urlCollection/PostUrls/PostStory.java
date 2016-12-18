/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.PostUrls;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostStory implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException, JSONException {
        String url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(UrlBox.storyWay).toString();
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        JSONObject obj = new JSONObject();
        obj.put("content", args.getString("content", ""));
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        PostInit.init(connection, obj.toString());
        return connection;
    }
}
