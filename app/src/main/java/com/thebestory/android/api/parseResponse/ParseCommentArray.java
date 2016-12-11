/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.Comment;
import com.thebestory.android.model.Story;

import java.io.IOException;
import java.util.ArrayList;

public class ParseCommentArray implements ParseResponse<ArrayList<Comment>> {

    @Override
    public ArrayList<Comment> parse(JsonReader response) throws IOException {
        ArrayList<Comment> stories = new ArrayList();

        response.beginArray();
        while (response.hasNext()) {
            stories.add(Comment.parse(response));
        }
        response.endArray();
        return stories;
    }
}
