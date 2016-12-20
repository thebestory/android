/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection.PostUrls;

import android.os.Bundle;

import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.UrlBox;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PostCommentLike implements ParseUrl {
    @Override
    public HttpURLConnection parse(Bundle args) throws IOException {
        return PostLikeFactory.getLikeConnection(UrlBox.commentWay, true, args);
    }
}