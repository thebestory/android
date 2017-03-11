/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.StoryLike;

import java.io.IOException;

public class ParseStoryLike implements ParseResponse<StoryLike> {
    @Override
    public StoryLike parse(JsonReader response) throws IOException {
        return StoryLike.parse(response);
    }
}
