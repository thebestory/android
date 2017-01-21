/*
 * The Bestory Project
 */

package com.thebestory.android.data;

import com.thebestory.android.model.Story;

import java.util.HashMap;

public class StoryDataSet {
    private final HashMap<String, Story> dataSet = new HashMap<>();

    /**
     * Get a story by their id, or `null`, if the story with this id is not present in the data set.
     */
    Story get(String id) {
        return dataSet.get(id);
    }

    /**
     * Put a story to the data set.
     */
    Story put(Story story) {
        // We replace the previous story, assuming that it contains outdated data
        dataSet.put(story.id, story);
        return story;
    }
}
