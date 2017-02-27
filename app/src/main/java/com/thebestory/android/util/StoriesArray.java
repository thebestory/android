package com.thebestory.android.util;

import com.thebestory.android.model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Alex on 26.02.2017.
 */

public class StoriesArray {

    private final ArrayList<String> storiesId;

    public StoriesArray() {
        storiesId = new ArrayList<String>();
    }

    public Story getStoryAt(int position) {
        return CacheStories.getInstance().getStory(storiesId.get(position));
    }

    public void addStoryAtTail(Story story) {
        addStoryAt(story, storiesId.size());
    }

    public void addStoryAtHead(Story story) {
        addStoryAt(story, 0);
    }

    public void addStoryAt(Story story, int position) {
        if (story == null || story.id == null) {
            return;
        }
        storiesId.add(position, story.id);
        CacheStories.getInstance().updateStory(story);
    }

    public int size() {
        return storiesId.size();
    }

    public JSONObject serialize() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonStoriesArray = new JSONArray();
        for (String i : storiesId) {
            jsonStoriesArray.put(i);
        }
        try {
            jsonObject.put("stories", jsonStoriesArray);
        } catch (JSONException error) {
            return null;
        }

        return jsonObject;
    }

    public StoriesArray(JSONObject object) {

        storiesId = new ArrayList<>();
        if (object == null) {
            return;
        }

        JSONArray storiesArray = object.optJSONArray("stories");
        if (storiesArray == null) {
            return;
        }

        int len = storiesArray.length();
        for (int i = 0; i < len; ++i) {
            String temp = storiesArray.optString(i, null);
            if (temp != null) {
                storiesId.add(temp);
            }
        }
    }

}
