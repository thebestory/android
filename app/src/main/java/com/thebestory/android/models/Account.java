package com.thebestory.android.models;

/**
 * Created by Alex on 16.10.2016.
 */

public final class Account {
    public final int id;
    public final String email;
    public final String username;
    public final int stories_count;
    public final int likes_count;

    public Account(int id, String email, String username, int stories_count, int likes_count) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.stories_count = stories_count;
        this.likes_count = likes_count;
    }
}
