package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.models.Topic;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Alex on 04.11.2016.
 */

public class ParseTopic implements ParseResponse<Topic> {
    @Override
    public Topic parse(JsonReader response) throws IOException {
        return Topic.parseTopic(response);
    }
}
