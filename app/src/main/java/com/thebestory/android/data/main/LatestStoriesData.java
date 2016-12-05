/*
 * The Bestory Project
 */

package com.thebestory.android.data.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.thebestory.android.model.Story;

import java.util.ArrayList;

/**
 * TODO: create interface StoriesData and make four class for everyone Fragment
 */
public class LatestStoriesData extends Fragment implements StoriesData  {

    public static final String TAG = LatestStoriesData.class.getCanonicalName();
    private ArrayList<Story> currentStories = new ArrayList<>();

    public LatestStoriesData() {
        this.setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ArrayList<Story> getCurrentStories() {
        return currentStories;
    }

    public String getLastId() {
        if (!currentStories.isEmpty()) {
            return currentStories.get(currentStories.size() - 1).id;
        } else {
            return "0";
        }
    }
}
