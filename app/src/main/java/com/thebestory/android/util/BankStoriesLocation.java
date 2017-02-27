package com.thebestory.android.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.provider.Settings;
import android.util.Log;
import android.util.Xml;

import com.thebestory.android.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 26.02.2017.
 */

public class BankStoriesLocation{
    private static final String BANK_STORIES_FILE_NAME = "Bank_of_stories";

    private class UnionStoryInfo {
        private final String slug;
        private final StoriesType type;

        UnionStoryInfo(StoriesType type, String slug) {
            this.slug = slug;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null) {
                return  false;
            }

            if (! (o instanceof UnionStoryInfo)) {
                return false;
            }

            UnionStoryInfo temp = (UnionStoryInfo) o;

            return (slug.compareTo(temp.slug) == 0 && type.compareTo(temp.type) == 0);
        }

        @Override
        public int hashCode() {
            return slug.hashCode()*13 + type.hashCode();
        }
    }
    private BankStoriesLocation() {
        bank = new HashMap<UnionStoryInfo, StoriesArray>();
    }

    public void loadBank(Context context) {
        try (InputStream fileRead = context.openFileInput(BANK_STORIES_FILE_NAME)) {
            byte[] buffer = new byte[fileRead.available()];
            fileRead.read(buffer);
            JSONObject jObect = new JSONObject(new String (buffer, "UTF-8"));
            deserialize(jObect);
        } catch (IOException | JSONException ignored) {
        }
    }

    public void saveBank(Context context) {
        try (OutputStream fileWrite = context.openFileOutput(BANK_STORIES_FILE_NAME, Context.MODE_PRIVATE)) {
            fileWrite.write(serialize().toString().getBytes());
        } catch (IOException ignored) {
        }

    }

    private static BankStoriesLocation ourInstance = new BankStoriesLocation();

    public static BankStoriesLocation getInstance() {
        return ourInstance;
    }

    private final Map<UnionStoryInfo, StoriesArray> bank;


    public StoriesArray getStoriesArray(StoriesType type, String slug) {
        if (type == null || slug == null) {
            return null;
        }

        UnionStoryInfo temp = new UnionStoryInfo(type, slug);
        StoriesArray result = bank.get(temp);
        if (result == null) {
            result = new StoriesArray();
            bank.put(temp, result);
        }

        return result;
    }


    public JSONObject serialize() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<UnionStoryInfo, StoriesArray> i : bank.entrySet()) {
            JSONObject entryObject = new JSONObject();
            try {
                entryObject.putOpt("type", i.getKey().type);
                entryObject.putOpt("slug", i.getKey().slug);
                entryObject.putOpt("stories", i.getValue().serialize());
            } catch (JSONException e) {
                continue;
            }
            jsonArray.put(entryObject);
        }
        try {
            jsonObject.putOpt("bank", jsonArray);
        } catch (JSONException error) {
            return null;
        }

        return jsonObject;
    }

    public void deserialize(JSONObject jsonObject) {

        try {
            JSONArray temp = jsonObject.getJSONArray("bank");
            if (temp == null) {
                return;
            }
            int len = temp.length();
            for (int i = 0; i < len; ++i) {
                JSONObject entryObject = temp.optJSONObject(i);
                if (entryObject == null) {
                    continue;
                }
                String tempType = entryObject.optString("type", null), tempSlug = entryObject.optString("slug", null);

                if (tempType == null || tempSlug == null) {
                    continue;
                }

                StoriesArray storiesTemp = new StoriesArray(entryObject.optJSONObject("stories"));
                bank.put(new UnionStoryInfo(StoriesType.valueOf(tempType), tempSlug), storiesTemp);
            }
        } catch (JSONException e) {
            bank.clear();
        }
    }






}
