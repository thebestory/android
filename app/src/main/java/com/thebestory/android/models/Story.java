package com.thebestory.android.models;

/**
 * Created by Alex on 16.10.2016.
 */

public final class Story {
    public final int id;
    public final int topicId;
    //TODO: Use the time class
    //public final int timestamp;

    public final int likesCount;

    //TODO: If comments supported
    //public final int comment_count;

    public final String story;

    public Story(int id, int topicId, int likesCount, String story) {
        this.id = id;
        this.topicId = topicId;
        this.likesCount = likesCount;
        this.story = story;
    }
}
