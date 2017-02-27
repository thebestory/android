/*
 * The Bestory Project
 */


package com.thebestory.android.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.thebestory.android.model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CacheStories {

    private CacheStories() {
        stories = CacheBuilder.newBuilder().maximumSize(1000).build();
    }

    private static CacheStories ourInstance = new CacheStories();

    public static CacheStories getInstance() {
        return ourInstance;
    }

    private Cache<String, Story> stories;

    public void updateStory(Story story) {
        if (story != null && story.id != null) {
            stories.put(story.id, story);
        }
    }

    public Story getStory(String id) {
        return stories.getIfPresent(id);
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonStoriesArray = new JSONArray();
        for (Story i : stories.asMap().values()) {
            jsonStoriesArray.put(i.toJSONObject());
        }
        try {
            jsonObject.put("stories", jsonStoriesArray);
        } catch (JSONException error) {
            jsonObject = new JSONObject();
        }

        return jsonObject;
    }

    public boolean setStoriesFromJSONObject(JSONObject jsonObject) {
        try {
            JSONArray storiesArray = jsonObject.getJSONArray("stories");
            int len = storiesArray.length();
            for (int i = 0; i < len; ++i) {
                updateStory(Story.parseJSONObject(storiesArray.optJSONObject(i)));
            }
            return true;
        } catch (JSONException error) {
            stories.invalidateAll();
            return false;
        }
    }
}
