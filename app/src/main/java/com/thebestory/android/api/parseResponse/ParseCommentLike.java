package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.CommentLike;

import java.io.IOException;

/**
 * Created by Alex on 02.12.2016.
 */

public class ParseCommentLike implements ParseResponse<CommentLike> {
    @Override
    public CommentLike parse(JsonReader response) throws IOException {
        return CommentLike.parse(response);
    }
}
