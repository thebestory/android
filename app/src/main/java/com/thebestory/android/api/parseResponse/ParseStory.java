/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.Story;

import java.io.IOException;
import java.text.ParseException;


public class ParseStory implements ParseResponse<Story> {
    @Override
    public Story parse(JsonReader response) throws IOException, ParseException {
        return Story.parse(response);
    }
}
