package com.thebestory.android.models;

import android.util.JsonReader;

import java.io.IOException;

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

    public static Story parseStory(JsonReader jr) throws IOException {
        int id = 0;
        int topicId = 0;
        int likesCount = 0;
        String story = null;
        jr.beginObject();
            while (jr.hasNext()) {
                switch (jr.nextName()) {
                    case "id" :
                        id = jr.nextInt();
                        break;
                    case "topicId" :
                        topicId = jr.nextInt();
                        break;
                    case "likesCount" :
                        likesCount = jr.nextInt();
                        break;
                    case "story" :
                        story = jr.nextString();
                        break;
                    default:
                        jr.skipValue();
                        break;
                }
            }
        jr.endObject();
        return new Story(id, topicId, likesCount, story);
    }
}
