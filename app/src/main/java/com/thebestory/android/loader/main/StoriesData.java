package com.thebestory.android.loader.main;

import android.app.Fragment;
import android.os.Bundle;

import com.thebestory.android.models.Story;

import java.util.ArrayList;

/**
 * Created by Октай on 16.11.2016.
 */

public class StoriesData extends Fragment { //TODO: create interface StoriesData and make four class for everyone Fragment

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
