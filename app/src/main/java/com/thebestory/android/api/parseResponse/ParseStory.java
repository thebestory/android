package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.models.Story;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Created by Alex on 04.11.2016.
 */

public class ParseStory implements ParseResponse<Story> {
    @Override
    public Story parse(JsonReader response) throws IOException {
        return Story.parseStory(response);
    }
}
