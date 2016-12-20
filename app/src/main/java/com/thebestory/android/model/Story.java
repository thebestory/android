/*
 * The Bestory Project
 */

package com.thebestory.android.model;

import android.util.JsonReader;

import org.joda.time.DateTime;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public final class Story {
    /**
     * Story unique ID
     */
    public final String id;

    /**
     * {@link Topic} instance with topic information
     */
    public final Topic topic;

    public final String content;

    public final int likesCount;
    public final int commentsCount;

    public final Date submitDate;
    public final Date publishDate;

    private final boolean isLiked;
    private boolean isLikedLocal;

    public Story(String id,
                 Topic topic,
                 String content,
                 int likesCount,
                 int commentsCount,
                 Date submitDate,
                 Date publishDate,
                 boolean isLiked) {
        this.id = id;
        this.topic = topic;
        this.content = content;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.submitDate = submitDate;
        this.publishDate = publishDate;
        this.isLiked = isLiked;

        this.isLikedLocal = isLiked;
    }

    public int getLikesCount() {
        return isLiked
                ? isLikedLocal ? this.likesCount : this.likesCount - 1
                : isLikedLocal ? this.likesCount + 1 : this.likesCount;
    }

    public int like() {
        isLikedLocal = true;
        return getLikesCount();
    }

    public int unlike() {
        isLikedLocal = false;
        return getLikesCount();
    }

    public boolean isLiked() {
        return isLikedLocal;
    }

    /**
     * Parses a story information ({@link Story}) from json object ({@link JsonReader}).
     *
     * @param jr json object data
     * @return {@link Story} instance with parsed information
     * @throws IOException
     */
    public static Story parse(JsonReader jr) throws IOException, ParseException {
        String id = null;
        Topic topic = null;
        int likesCount = 0;
        int commentsCount = 0;
        String content = null;
        Date publishDate = null;
        Date submitDate = null;
        boolean isLiked = false;

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "id":
                    id = jr.nextString();
                    break;
                case "topic":
                    try {
                        topic = Topic.parse(jr);
                    } catch (IllegalStateException e) {
                        topic = null;
                        jr.skipValue();
                    }

                    break;
                case "content":
                    content = jr.nextString();
                    break;
                case "likes_count":
                    likesCount = jr.nextInt();
                    break;
                case "comments_count":
                    commentsCount = jr.nextInt();
                    break;
                case "submitted_date":
                    submitDate = new DateTime(jr.nextString()).toDate();
                    break;
                case "published_date":
                    try {
                        publishDate = new DateTime(jr.nextString()).toDate();
                    } catch (IllegalStateException e) {
                        publishDate = null;
                        jr.skipValue();
                    }

                    break;
                case "is_liked":
                    isLiked = jr.nextBoolean();
                    break;
                default:
                    jr.skipValue();
                    break;
            }
        }

        jr.endObject();

        return new Story(
                id,
                topic,
                content,
                likesCount,
                commentsCount,
                submitDate,
                publishDate,
                isLiked
        );
    }
}
