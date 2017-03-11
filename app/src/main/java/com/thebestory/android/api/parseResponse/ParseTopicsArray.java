/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.model.Topic;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ParseTopicsArray implements ParseResponse<List<Topic>> {
    @Override
    public ArrayList<Topic> parse(JsonReader response) throws IOException {
        ArrayList<Topic> topics = new ArrayList<Topic>();
        //while (!jr.nextName().equals("topics")) {}
        response.beginArray();
        while (response.hasNext()) {
            topics.add(Topic.parse(response));
        }
        response.endArray();
        return topics;
    }
}
