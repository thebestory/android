/*
 * The Bestory Project
 */

package com.thebestory.android.model;

import android.util.JsonReader;
import android.util.Log;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public final class Topic {
    /**
     * Topic unique ID
     */

    public final String id;
    public final String slug;
    public final String title;
    public final String description;
    public final String icon;
    public final int storiesCount;
    public final boolean isActive;

    public Topic(
                 String id,
                 String slug,
                 String title,
                 String description,
                 String icon,
                 int storiesCount,
                 boolean isActive) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.storiesCount = storiesCount;
        this.isActive = isActive;
    }

    /**
     * Parses a topic information ({@link Topic}) from json object ({@link JsonReader}).
     *
     * @param jr json object data
     * @return {@link Topic} instance with parsed information
     * @throws IOException
     */
    public static Topic parse(JsonReader jr) throws IOException {
        String id = null;
        String slug = null;
        String title = null;
        String description = null;
        String icon = null;
        int storiesCount = 0;
        boolean isActive = false;

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "id":
                    id = jr.nextString();
                    break;
                case "slug":
                    slug = jr.nextString();
                    break;
                case "title":
                    title = jr.nextString();
                    break;
                case "description":
                    description = jr.nextString();
                    break;
                case "icon":
                    icon = "https://thebestory.github.io/icons/128/" + jr.nextString() + ".png";
                    break;
                case "stories_count":
                    storiesCount = jr.nextInt();
                    break;
                case "is_active":
                    isActive = jr.nextBoolean();
                    break;
                default:
                    jr.skipValue();
                    break;
            }
        }

        jr.endObject();

        return new Topic(
                id,
                slug,
                title,
                description,
                icon,
                storiesCount,
                isActive
        );
    }

    public static Topic parseJSONObject(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }

        String id = null;
        String slug = null;
        String title = null;
        String description = null;
        String icon = null;
        int storiesCount = 0;
        boolean isActive = false;

        id = jsonObject.optString("id");
        slug = jsonObject.optString("slug");
        title = jsonObject.optString("title");
        description = jsonObject.optString("description");
        icon = jsonObject.optString("icon");
        storiesCount = jsonObject.optInt("stories_count");
        isActive = jsonObject.optBoolean("is_active");

        return new Topic(
                id,
                slug,
                title,
                description,
                icon,
                storiesCount,
                isActive
        );
    }

    public JSONObject toJSONObject () {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("id", id);
            jsonObject.putOpt("slug", slug);
            jsonObject.putOpt("title", title);
            jsonObject.putOpt("description", description);
            jsonObject.putOpt("icon", icon);
            jsonObject.putOpt("stories_count", storiesCount);
            jsonObject.putOpt("is_active", isActive);
            return jsonObject;
        } catch (JSONException error) {
            return new JSONObject();
        }
    }
}
