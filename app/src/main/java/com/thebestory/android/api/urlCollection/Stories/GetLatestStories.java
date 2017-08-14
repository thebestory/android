/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.Stories;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.thebestory.android.api.RequestType;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;
import com.thebestory.android.api.urlCollection.UtilsParameters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetLatestStories implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        Uri.Builder url = Uri.parse(UrlBox.baseUrl)
                .buildUpon()
                .appendPath(UrlBox.topicWay)
                .appendPath(args.getString("id", "0"))
                .appendPath("latest");
        UtilsParameters.addParametersToStoryRequest(url, args);

        Log.d("TOPIC: ", url.toString());

        return (HttpURLConnection) (new URL(url.toString())).openConnection();
    }


}
