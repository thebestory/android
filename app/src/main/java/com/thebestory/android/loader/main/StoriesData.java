/*
 * The Bestory Project
 */

package com.thebestory.android.loader.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.thebestory.android.model.Story;

import java.util.ArrayList;

/**
 * TODO: create interface StoriesData and make four class for everyone Fragment
 */
public class StoriesData extends Fragment {

    public static final String TAG = StoriesData.class.getCanonicalName();
    private ArrayList<Story> currentStories = new ArrayList<>();

    public StoriesData() {
        this.setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ArrayList<Story> getCurrentStories() {
        return currentStories;
    }

}
