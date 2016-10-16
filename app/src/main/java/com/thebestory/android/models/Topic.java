package com.thebestory.android.models;


/**
 * Created by Alex on 16.10.2016.
 */

public final class Topic {
    public final int id;
    public final String title;
    public final String description;
    public final String icon;
    public final int stories_count;

    public Topic(int id, String title, String description, String icon, int stories_count) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.stories_count = stories_count;
    }
}
