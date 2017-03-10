/*
 * The Bestory Project
 */


package com.thebestory.android.util;

import android.content.Context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.thebestory.android.files.FilesSystem;
import com.thebestory.android.model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class CacheStories implements FilesSystem.FileCache {

    private static final String CACHE_STORIES_FILE_NAME  = "Stories";

    private CacheStories() {
        stories = CacheBuilder.newBuilder().maximumSize(1000).build();
        bookmarkedStories = new HashMap<>();
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

    private final Cache<String, Story> stories;
    private final Map<String, Story> bookmarkedStories;

    public void updateStory(Story story) {
        if (story != null && story.id != null && getBookmarkedStory(story.id) == null) {
            stories.put(story.id, story);
        }
    }

    public boolean isBookmarked(String id) {
        return bookmarkedStories.containsKey(id);
    }

    public void removeBookmarked(String id) {
        updateStory(bookmarkedStories.remove(id));
    }

    public void setBookmarked(Story story) {
        if (story != null && story.id != null) {
            stories.invalidate(story.id);
            bookmarkedStories.put(story.id, story);
        }
    }

    public void setBookmarked(String id) {
        Story story = getStory(id);
        setBookmarked(story);
    }


    public Story getStory(String id) {
        Story story = stories.getIfPresent(id);
        return story == null ? bookmarkedStories.get(id) : story;
    }

    public Story getNotBookmarkedStory(String id) {
        return stories.getIfPresent(id);
    }

    public Story getBookmarkedStory(String id) {
        return bookmarkedStories.get(id);
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonStoriesArray = new JSONArray();
        JSONArray jsonBookmarkedArray = new JSONArray();
        for (Story i : stories.asMap().values()) {
            jsonStoriesArray.put(i.toJSONObject());
        }

        for (Story i : bookmarkedStories.values()) {
            jsonBookmarkedArray.put(i.toJSONObject());
        }

        try {
            jsonObject.putOpt("stories", jsonStoriesArray);
            jsonObject.putOpt("bookmarked", jsonBookmarkedArray);
        } catch (JSONException error) {
            jsonObject = new JSONObject();
        }

        return jsonObject;
    }

    public void setStoriesFromJSONObject(JSONObject jsonObject) {
        JSONArray storiesArray = jsonObject.optJSONArray("stories");
        int len = 0;
        if (storiesArray != null) {
            len = storiesArray.length();

            for (int i = 0; i < len; ++i) {
                if (storiesArray.isNull(i)) {
                    continue;
                }
                updateStory(Story.parseJSONObject(storiesArray.optJSONObject(i)));
            }
        }


        storiesArray = jsonObject.optJSONArray("bookmarked");
        if (storiesArray != null) {
            len = storiesArray.length();
            for (int i = 0; i < len; ++i) {
                if (storiesArray.isNull(i)) {
                    continue;
                }
                setBookmarked(Story.parseJSONObject(storiesArray.optJSONObject(i)));
            }
        }

    }

    @Override
    public void onOpenApp(Context context) {
        loadCache(context);
    }

    @Override
    public void onExitApp(Context context) {
        saveCache(context);
    }

    @Override
    public void onDeleteCashe(Context context) {
        stories.invalidateAll();
        stories.cleanUp();
        bookmarkedStories.clear();
        context.deleteFile(CACHE_STORIES_FILE_NAME);
    }

    @Override
    public long sizeFile(Context context) {
        saveCache(context);
        return context.getFileStreamPath(CACHE_STORIES_FILE_NAME).length();
    }
}
