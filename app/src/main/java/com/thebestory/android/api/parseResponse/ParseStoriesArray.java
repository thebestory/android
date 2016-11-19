package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;
import android.util.Log;

import com.thebestory.android.models.Story;
import com.thebestory.android.models.Topic;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Alex on 02.11.2016.
 */

public class ParseStoriesArray implements ParseResponse<ArrayList<Story>> {
    @Override
    public ArrayList<Story> parse(JsonReader response) throws IOException {
        ArrayList<Story> stories = new ArrayList();
        //while (!jr.nextName().equals("stories")) {
        //    jr.skipValue();
        //}
        Log.e("WRONG","Start");
        response.beginArray();
        while (response.hasNext()) {
            stories.add(Story.parseStory(response));
        }
        response.endArray();
        Log.e("WRONG","End");
        return stories;
    }
}
