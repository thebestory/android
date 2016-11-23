/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.Topic;

import java.io.IOException;

public class ParseTopic implements ParseResponse<Topic> {
    @Override
    public Topic parse(JsonReader response) throws IOException {
        return Topic.parse(response);
    }
}
