/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.Comment;
import com.thebestory.android.model.Story;

import java.io.IOException;

public class ParseComment implements ParseResponse<Comment> {
    @Override
    public Comment parse(JsonReader response) throws IOException {
        return Comment.parse(response);
    }
}