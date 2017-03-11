/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.Comment;
import com.thebestory.android.model.Story;

import java.io.IOException;
import java.text.ParseException;

public class ParseComment implements ParseResponse<Comment> {
    @Override
    public Comment parse(JsonReader response) throws IOException, ParseException {
        return Comment.parse(response);
    }
}