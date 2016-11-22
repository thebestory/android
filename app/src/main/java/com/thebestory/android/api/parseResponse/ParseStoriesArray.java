/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;
import android.util.Log;

import com.thebestory.android.model.Story;

import java.io.IOException;
import java.util.ArrayList;

public class ParseStoriesArray implements ParseResponse<ArrayList<Story>> {
    @Override
    public ArrayList<Story> parse(JsonReader response) throws IOException {
        ArrayList<Story> stories = new ArrayList();
        //while (!jr.nextName().equals("stories")) {
        //    jr.skipValue();
        //}
        Log.e("WRONG", "Start");
        response.beginArray();
        while (response.hasNext()) {
            stories.add(Story.parse(response));
        }
        response.endArray();
        Log.e("WRONG", "End");
        return stories;
    }
}
