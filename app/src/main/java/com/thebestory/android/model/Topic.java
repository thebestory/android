/*
 * The Bestory Project
 */

package com.thebestory.android.model;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;

public final class Topic {
    /**
     * Topic unique ID
     */
    public final String slug;
    public final String title;
    public final String description;
    public final String icon;

    public final int storiesCount;

    public Topic(
                 String title,
                 String slug,
                 String description,
                 String icon,
                 int storiesCount) {
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
        String title = null;
        String slug = null;
        String description = null;
        String icon = null;
        int storiesCount = 0;

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
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
                    icon = "https://thebestory.github.io/icons/128/" + jr.nextString() + ".png";
                    Log.w("TOPIC", icon);
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
                title,
                slug,
                description,
                icon,
                storiesCount
        );
    }
}
