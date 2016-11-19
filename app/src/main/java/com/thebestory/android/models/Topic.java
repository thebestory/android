package com.thebestory.android.models;


import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by Alex on 16.10.2016.
 */

public final class Topic {
    public final int id;
    public final String title;
    public final String description;
    public final String icon;
    public final int storiesCount;
    public final String slug;

    public Topic(int id, String title, String description, String slug, String icon, int storiesCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.storiesCount = storiesCount;
        this.slug = slug;
    }

    public static Topic parseTopic (JsonReader jr) throws IOException {
        int id = 0;
        String title = null;
        String description = null;
        String icon = null, slug = null;
        int storiesCount = 0;

        jr.beginObject();
        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "id" :
                    id = jr.nextInt();
                    break;
                case "title" :
                    title = jr.nextString();
                    break;
                case "description" :
                    description = jr.nextString();
                    break;
                case "icon" :
                    icon = jr.nextString();
                    break;
                case "stories_count" :
                    storiesCount = jr.nextInt();
                    break;
                case "slug" :
                    slug = jr.nextString();
                    break;
                default:
                    jr.skipValue();
                    break;
            }
        }
        jr.endObject();

        return new Topic(id, title, description, slug, icon, storiesCount);
    }
}
