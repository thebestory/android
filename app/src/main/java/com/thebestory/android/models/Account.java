package com.thebestory.android.models;

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
}
