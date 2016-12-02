/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.Topics;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.RequestType;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;
import com.thebestory.android.api.urlCollection.UtilsParameters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetTopicStories implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        Uri.Builder url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(UrlBox.topicWay).appendPath(Integer.toString(args.getInt("topicId", 0))).appendEncodedPath("stories");
        UtilsParameters.addParametersToStoryRequest(url, args);
        HttpURLConnection connection = (HttpURLConnection) (new URL(url.toString())).openConnection();
        return connection;
    }


}
