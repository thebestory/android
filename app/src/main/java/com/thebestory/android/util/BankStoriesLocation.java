package com.thebestory.android.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 26.02.2017.
 */

public class BankStoriesLocation{

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
