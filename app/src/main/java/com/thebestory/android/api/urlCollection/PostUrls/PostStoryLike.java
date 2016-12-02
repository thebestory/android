package com.thebestory.android.api.urlCollection.PostUrls;

import android.net.Uri;
import android.os.Bundle;

import com.thebestory.android.api.RequestType;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 02.12.2016.
 */

public class PostStoryLike implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        return PostLikeFactory.getLikeConnection(UrlBox.storyWay, "like", args);
    }


}
