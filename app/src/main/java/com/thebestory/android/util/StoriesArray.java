package com.thebestory.android.util;

import com.thebestory.android.model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alex on 26.02.2017.
 */

public class StoriesArray {

    private final ArrayList<String> storiesId;
    private final boolean bookmarked;

    public StoriesArray(boolean bookmarked) {
        storiesId = new ArrayList<String>();
        this.bookmarked = bookmarked;
    }

    public StoriesArray() {
        this(false);
    }

    public Story getStoryAt(int position) {
        return this.bookmarked ? CacheStories.getInstance().getBookmarkedStory(storiesId.get(position)) : CacheStories.getInstance().getStory(storiesId.get(position));
    }

    public void addStoryAtTail(Story story) {
        addStoryAt(story, storiesId.size());
    }

    public void addAllStoryAtTail(Iterable<Story> stories) {
        for (Story i: stories) {
            addStoryAtTail(i);
        }
    }

    public void addAllStoryAtHead(Iterable<Story> stories) {
        for (Story i: stories) {
            addStoryAtHead(i);
        }
    }

    public void addStoryAtHead(Story story) {
        addStoryAt(story, 0);
    }

    public void clear() {
        storiesId.clear();
    }

    public boolean isEmpty() {
        return storiesId.isEmpty();
    }

    public String removeStoryAt(int pos) {
        String id = storiesId.remove(pos);
        if (id != null) {
            CacheStories.getInstance().removeBookmarked(id);
        }
        return id;
    }

    public boolean removeStory(String id) {
        boolean temp = storiesId.remove(id);
        if (temp) {
            CacheStories.getInstance().removeBookmarked(id);
        }

        return temp;
    }

    public void addStoryAt(Story story, int position) {
        if (story == null || story.id == null) {
            return;
        }
        storiesId.add(position, story.id);
        bookmarked : CacheStories.getInstance().setBookmarked(story); CacheStories.getInstance().updateStory(story);
    }

    public boolean isEntry(String id) {
        return storiesId.contains(id);
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

    public StoriesArray(JSONObject object, boolean bookmarked) {
        this.bookmarked = bookmarked;
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

    public StoriesArray(JSONObject object) {
        this(object, false);
    }

}
