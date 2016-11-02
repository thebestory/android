package com.thebestory.android.models;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by Alex on 16.10.2016.
 */

public final class Account {
    public final int id;
    public final String email;
    public final String username;
    public final int storiesCount;
    public final int likesCount;

    public Account(int id, String email, String username, int storiesCount, int likesCount) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.storiesCount = storiesCount;
        this.likesCount = likesCount;
    }

    public static Account parseAccount (JsonReader jr) throws IOException {
        int id = 0;
        String email = null;
        String username = null;
        int storiesCount = 0;
        int likesCount = 0;

        jr.beginObject();
        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "id" :
                    id = jr.nextInt();
                    break;
                case "email" :
                    email = jr.nextString();
                    break;
                case "username" :
                    username = jr.nextString();
                    break;
                case "stories_count" :
                    storiesCount = jr.nextInt();
                    break;
                case "likes_count" :
                    likesCount = jr.nextInt();
                    break;

                default:
                    jr.skipValue();
                    break;
            }
        }
        jr.endObject();

        return new Account(id, email, username, storiesCount, likesCount);
    }
}
