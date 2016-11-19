package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;

import com.thebestory.android.models.Topic;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02.11.2016.
 */

public class ParseTopicsArray implements ParseResponse<ArrayList<Topic>> {
    @Override
    public ArrayList<Topic> parse(JsonReader response) throws IOException {
        ArrayList<Topic> topics = new ArrayList();
        //while (!jr.nextName().equals("topics")) {}
        response.beginArray();
            while (response.hasNext())
            {
                topics.add(Topic.parseTopic(response));
            }
        response.endArray();
        return topics;
    }
}
