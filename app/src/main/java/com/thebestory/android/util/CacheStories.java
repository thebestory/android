/*
 * The Bestory Project
 */


package com.thebestory.android.util;

import android.content.Context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.thebestory.android.model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CacheStories {

    private static final String CACHE_STORIES_FILE_NAME  = "Stories";
    private CacheStories() {
        stories = CacheBuilder.newBuilder().maximumSize(1000).build();
    }

    private static CacheStories ourInstance = new CacheStories();

    public static CacheStories getInstance() {
        return ourInstance;
    }

    public void loadCache(Context context) {
        try (InputStream fileRead = context.openFileInput(CACHE_STORIES_FILE_NAME)) {
            byte[] buffer = new byte[fileRead.available()];
            fileRead.read(buffer);
            JSONObject jObect = new JSONObject(new String (buffer, "UTF-8"));
            setStoriesFromJSONObject(jObect);
        } catch (IOException | JSONException ignored) {
        }
    }

    public void saveCache(Context context) {
            try (OutputStream fileWrite = context.openFileOutput(CACHE_STORIES_FILE_NAME, Context.MODE_PRIVATE)) {
                fileWrite.write(getJSONObject().toString().getBytes());
            } catch (IOException ignored) {
            }

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
            jsonObject.putOpt("stories", jsonStoriesArray);
        } catch (JSONException error) {
            jsonObject = new JSONObject();
        }

        return jsonObject;
    }

    public void setStoriesFromJSONObject(JSONObject jsonObject) {
        try {
            JSONArray storiesArray = jsonObject.getJSONArray("stories");
            int len = storiesArray.length();
            for (int i = 0; i < len; ++i) {
                if (storiesArray.isNull(i)) {
                    continue;
                }
                updateStory(Story.parseJSONObject(storiesArray.optJSONObject(i)));
            }
        } catch (JSONException error) {
            stories.invalidateAll();
        }
    }
}
