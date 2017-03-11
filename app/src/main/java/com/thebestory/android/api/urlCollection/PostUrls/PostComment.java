/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.PostUrls;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostComment implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        String url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(UrlBox.commentWay).toString();
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        JSONObject obj = new JSONObject();
        //TODO: POST COMMENT REQUEST
        connection.setRequestMethod("POST");

        PostInit.init(connection, obj.toString());
        return connection;
    }
}
