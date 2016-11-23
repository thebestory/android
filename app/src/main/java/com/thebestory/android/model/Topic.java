/*
 * The Bestory Project
 */

package com.thebestory.android.model;

import android.util.JsonReader;

import java.io.IOException;

public final class Topic {
    /**
     * Topic unique ID
     */
    public final int id;

    public final String title;
    public final String slug;
    public final String description;
    public final String icon;

    public final int storiesCount;

    public Topic(int id,
                 String title,
                 String slug,
                 String description,
                 String icon,
                 int storiesCount) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.storiesCount = storiesCount;
    }

    /**
     * Parses a topic information ({@link Topic}) from json object ({@link JsonReader}).
     *
     * @param jr json object data
     * @return {@link Topic} instance with parsed information
     * @throws IOException
     */
    public static Topic parse(JsonReader jr) throws IOException {
        int id = 0;
        String title = null;
        String slug = null;
        String description = null;
        String icon = null;
        int storiesCount = 0;

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "id":
                    id = jr.nextInt();
                    break;
                case "title":
                    title = jr.nextString();
                    break;
                case "slug":
                    slug = jr.nextString();
                    break;
                case "description":
                    description = jr.nextString();
                    break;
                case "icon":
                    icon = jr.nextString();
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

        return new Topic(
                id,
                title,
                slug,
                description,
                icon,
                storiesCount
        );
    }
}
