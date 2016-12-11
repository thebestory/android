/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.CommentLike;

import java.io.IOException;

public class ParseCommentLike implements ParseResponse<CommentLike> {
    @Override
    public CommentLike parse(JsonReader response) throws IOException {
        return CommentLike.parse(response);
    }
}
