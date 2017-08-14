/*
 * The Bestory Project
 */

package com.thebestory.android.model;

import android.util.JsonReader;

import java.io.IOException;

public class StoryLike {
    public final String userId;
    public final String id;
    public final boolean state;
    public final String timestamp;

    public StoryLike(String userId,
                     String id,
                     String timestamp,
                     boolean state) {
        this.id = id;
        this.userId = userId;
        this.state = state;
        this.timestamp = timestamp;
    }


    /**
     * Parses a story information ({@link StoryLike}) from json object ({@link JsonReader}).
     *
     * @param jr json object data
     * @return {@link StoryLike} instance with parsed information
     * @throws IOException
     */
    public static StoryLike parse(JsonReader jr) throws IOException {
        String userId = null, id = null, timestamp = null;
        boolean state = false;

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "user_id":
                    userId = jr.nextString();
                    break;
                case "story_id":
                    id = jr.nextString();
                    break;
                case "state":
                    state = jr.nextBoolean();
                    break;
                case "timestamp":
                    timestamp = jr.nextString();
                    break;
                default:
                    jr.skipValue();
                    break;
            }
        }

        jr.endObject();

        return new StoryLike(
                userId,
                id,
                timestamp,
                state
        );
    }
}
