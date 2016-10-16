package com.thebestory.android.models;

/**
 * Created by Alex on 16.10.2016.
 */

public final class Story {
    public final int id;
    public final int topic_id;
    //TODO: Use the time class
    //public final int timestamp;

    public final int likes_count;

    //TODO: If comments supported
    //public final int comment_count;

    public final String story;

    public Story(int id, int topic_id, int likes_count, String story) {
        this.id = id;
        this.topic_id = topic_id;
        this.likes_count = likes_count;
        this.story = story;
    }
}
