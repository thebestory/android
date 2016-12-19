/*
 * The Bestory Project
 */

package com.thebestory.android.model;

import android.util.JsonReader;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    public Story(String id,
                 Topic topic,
                 String content,
                 int likesCount,
                 int commentsCount,
                 Date submitDate,
                 Date publishDate) {
        this.id = id;
        this.topic = topic;
        this.content = content;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.submitDate = submitDate;
        this.publishDate = publishDate;
    }

    public boolean isPublished() {
        // TODO: Check, that publish date present and <= now.
        return publishDate != null;
    }

    public boolean isScheduled() {
        // TODO: Check, that publish date present and > now.
        return publishDate != null;
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

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "id":
                    id = jr.nextString();
                    break;
                case "topic":
                    topic = Topic.parse(jr);
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
                    publishDate = new DateTime(jr.nextString()).toDate();
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
                publishDate
        );
    }
}
