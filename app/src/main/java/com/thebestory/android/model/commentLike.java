package com.thebestory.android.model;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by Alex on 02.12.2016.
 */

public class CommentLike {

    public final String userId;
    public final String id;
    public final boolean state;
    public final String timestamp;

    public CommentLike(String userId,
                 String id,
                 String timestamp,
                 boolean state) {
        this.id = id;
        this.userId = userId;
        this.state = state;
        this.timestamp = timestamp;
    }


    /**
     * Parses a story information ({@link CommentLike}) from json object ({@link JsonReader}).
     *
     * @param jr json object data
     * @return {@link CommentLike} instance with parsed information
     * @throws IOException
     */
    public static CommentLike parse(JsonReader jr) throws IOException {
        String userId = null,  id = null, timestamp = null;
        boolean state = false;

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "user_id ":
                    userId = jr.nextString();
                    break;
                case "comment_id":
                    id = jr.nextString();
                    break;
                case "state ":
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

        return new CommentLike(
                userId,
                id,
                timestamp,
                state
        );
    }
}
