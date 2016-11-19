package com.thebestory.android.models;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Alex on 16.10.2016.
 */

public final class Story {
    public final String id;

    public final int likesCount;

    public final int commentsCount;

    public final String content;

    public final String submitDate;
    public final String publishDate;

    public final Topic topic;

    public Story(String id, int likesCount, int commentsCount, String content, String submitDate, String publishDate, Topic topic) {
        this.id = id;
        this.likesCount = likesCount;
        this.content = content;
        this.submitDate = submitDate;
        this.publishDate = publishDate;
        this.topic = topic;
        this.commentsCount = commentsCount;
    }

    public static Story parseStory(JsonReader jr) throws IOException {
        String id = null;
        int commentsCount = 0, likesCount = 0;
        String content = null, publishDate = null, submitDate = null;
        Topic topic = null;
        jr.beginObject();
        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "id":
                    id = jr.nextString();
                    Log.e("WRONG", id);
                    break;
                case "likes_count":
                    likesCount = jr.nextInt();
                    Log.e("WRONG", String.valueOf(likesCount));
                    break;
                case "content":
                    content = jr.nextString();
                    Log.e("WRONG", content);//wrong
                    break;
                case "comments_count":
                    commentsCount = jr.nextInt();
                    Log.e("WRONG", String.valueOf(commentsCount));
                    break;
                case "submitted_date":
                    submitDate = jr.nextString();
                    Log.e("WRONG", submitDate);
                    break;
                case "published_date":
                    publishDate = jr.nextString();
                    Log.e("WRONG", publishDate);
                    break;
                case "topic":
                    topic = Topic.parseTopic(jr);
                    Log.e("WRONG", String.valueOf(topic.id));
                    break; //WOW
                default:
                    jr.skipValue();
                    break;
            }
        }
        jr.endObject();
        return new Story(id, likesCount, commentsCount, content, submitDate, publishDate, topic);
    }
}
