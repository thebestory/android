/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.Stories;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.RequestType;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;
import com.thebestory.android.api.urlCollection.UtilsParameters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRandomStories implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        Uri.Builder url = Uri.parse(UrlBox.baseUrl).buildUpon().appendPath(UrlBox.topicWay).appendPath(args.getString("slug","")).appendPath("random");
        UtilsParameters.addParametersToStoryRequest(url, args);

        HttpURLConnection connection = (HttpURLConnection) (new URL(url.toString())).openConnection();
        return connection;
    }

}
