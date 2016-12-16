/*
 * The Bestory Project
 */

package com.thebestory.android;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.thebestory.android.model.Story;
import com.thebestory.android.model.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Bestory application entry point.
 */
public class TheBestoryApplication extends Application {
    public String slug = "all";
    public HashMap<String, ArrayList<Story>> loadedStory;
    public ArrayList<Topic> currentLoadedTopic;
    @Override
    public void onCreate() {
        super.onCreate();
        loadedStory = new HashMap<>();
        currentLoadedTopic = new ArrayList<>();
        loadedStory.put("latest", new ArrayList<Story>());
        loadedStory.put("hot", new ArrayList<Story>());
        loadedStory.put("top", new ArrayList<Story>());
        loadedStory.put("random", new ArrayList<Story>());
        Fresco.initialize(this);
    }
}
