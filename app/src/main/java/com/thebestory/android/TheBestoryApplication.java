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

import static java.util.Arrays.asList;

/**
 * The Bestory application entry point.
 */
public class TheBestoryApplication extends Application {
    public String slug = "all";
    public HashMap<String, HashMap<String, ArrayList<Story>>> loadedStories;
    public ArrayList<Topic> currentLoadedTopics;
    public ArrayList<String> nameTopics;
    public HashMap<String, String> topics = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();

        loadedStories = new HashMap<>();
        currentLoadedTopics = new ArrayList<>();
        nameTopics = new ArrayList<>(asList(
                "all",
                "daydreams",
                "dreams",
                "funny",
                "gooddeeds",
                "happiness",
                "intimate", //TODO: Hardcode
                "lifehack",
                "love",
                "sad",
                "scary",
                "weird"
        ));

        topics.put("all", "All stories");
        topics.put("daydreams", "Daydreams");
        topics.put("dreams", "Dreams");
        topics.put("funny", "Funny");
        topics.put("gooddeeds", "Good deeds");
        topics.put("happiness", "Happiness");
        topics.put("intimate", "Intimate");
        topics.put("lifehack", "Lifehack");
        topics.put("love", "Love");
        topics.put("sad", "Sad");
        topics.put("scary", "Scary");
        topics.put("weird", "Weird");

        for (String i : nameTopics) {
            HashMap<String, ArrayList<Story>> item = new HashMap<>();
            item.put("latest", new ArrayList<Story>());
            item.put("hot", new ArrayList<Story>());
            item.put("top", new ArrayList<Story>());
            item.put("random", new ArrayList<Story>());

            loadedStories.put(i, item);
        }

        Fresco.initialize(this);
    }
}
