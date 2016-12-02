package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.Comment;
import com.thebestory.android.model.Story;

import java.io.IOException;

/**
 * Created by Alex on 02.12.2016.
 */

public class ParseComment implements ParseResponse<Comment> {
    @Override
    public Comment parse(JsonReader response) throws IOException {
        return Comment.parse(response);
    }
}