package com.thebestory.android.loader.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.thebestory.android.model.Story;

import java.util.ArrayList;

/**
 * Created by Октай on 28.11.2016.
 */

public class HotStoriesData extends Fragment implements StoriesData  {
    public static final String TAG = HotStoriesData.class.getCanonicalName();
    private ArrayList<Story> currentStories = new ArrayList<>();

    public HotStoriesData() {
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
