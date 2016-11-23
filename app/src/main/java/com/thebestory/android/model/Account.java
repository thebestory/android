/*
 * The Bestory Project
 */

package com.thebestory.android.model;

import android.util.JsonReader;

import java.io.IOException;

public final class Account {
    /**
     * Account unique ID
     */
    public final int id;

    public final String username;

    public final int likesCount;
    public final int storiesCount;

    public Account(int id,
                   String username,
                   int likesCount,
                   int storiesCount) {
        this.id = id;
        this.username = username;
        this.likesCount = likesCount;
        this.storiesCount = storiesCount;
    }

    /**
     * Parses an account information ({@link Account}) from json object ({@link JsonReader}).
     *
     * @param jr json object data
     * @return {@link Account} instance with parsed information
     * @throws IOException
     */
    public static Account parse(JsonReader jr) throws IOException {
        int id = 0;
        String username = null;
        int likesCount = 0;
        int storiesCount = 0;

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "id":
                    id = jr.nextInt();
                    break;
                case "username":
                    username = jr.nextString();
                    break;
                case "likes_count":
                    likesCount = jr.nextInt();
                    break;
                case "stories_count":
                    storiesCount = jr.nextInt();
                    break;
                default:
                    jr.skipValue();
                    break;
            }
        }

        jr.endObject();

        return new Account(
                id,
                username,
                likesCount,
                storiesCount
        );
    }
}
