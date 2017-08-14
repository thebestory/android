package com.thebestory.android.util;

import android.content.Context;

import com.thebestory.android.files.FilesSystem;
import com.thebestory.android.model.Topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 06.03.2017.
 */

public class BankTopics implements Iterable<Topic>, FilesSystem.FileCache {

    private static final String BANK_TOPICS_FILE_NAME = "Bank_of_topics";
    private List<Topic> topics;
    private static final Topic allTopic = new Topic(String.valueOf(0), "all", "All Stories",
            "All stories", "null", 0, true);


    private BankTopics() {
        topics = new ArrayList<>();
    }

    public Topic getAllTopic() {
        return allTopic;
    }

    public Topic getTopicAt(int position) {
        if (position < 0 || position >= topics.size()) {
            return allTopic;
        }
        return topics.get(position);
    }

    public int getCount() {
        return topics.size();
    }

    public void loadBank(Context context) {
        try (InputStream fileRead = context.openFileInput(BANK_TOPICS_FILE_NAME)) {
            byte[] buffer = new byte[fileRead.available()];
            fileRead.read(buffer);
            JSONObject jObect = new JSONObject(new String (buffer, "UTF-8"));
            deserialize(jObect);
        } catch (IOException | JSONException ignored) {
        }
    }

    public void saveBank(Context context) {
        try (OutputStream fileWrite = context.openFileOutput(BANK_TOPICS_FILE_NAME, Context.MODE_PRIVATE)) {
            fileWrite.write(serialize().toString().getBytes());
        } catch (IOException ignored) {
        }

    }

    private static BankTopics ourInstance = new BankTopics();

    public static BankTopics getInstance() {
        return ourInstance;
    }

    public JSONObject serialize() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Topic i : topics) {
            jsonArray.put(i.toJSONObject());
        }
        try {
            jsonObject.putOpt("topics", jsonArray);
        } catch (JSONException error) {
            return new JSONObject();
        }

        return jsonObject;
    }

    public void deserialize(JSONObject jsonObject) {
        JSONArray temp = jsonObject.optJSONArray("topics");
        if (temp == null) {
            return;
        }
        int len = temp.length();
        for (int i = 0; i < len; ++i) {
            JSONObject entryObject = temp.optJSONObject(i);
            if (entryObject == null) {
                continue;
            }
            topics.add(Topic.parseJSONObject(entryObject));
        }
    }

    @Override
    public Iterator<Topic> iterator() {
        return topics.iterator();
    }

    public void loadAndUpdateTopics(List<Topic> topics) {
        this.topics = new ArrayList<>(topics);
    }

    public ArrayList<Topic> getList() {
        return new ArrayList<>(topics);
    }

    @Override
    public void onOpenApp(Context context) {
        loadBank(context);
    }

    @Override
    public void onExitApp(Context context) {
        saveBank(context);
    }

    @Override
    public void onDeleteCashe(Context context) {
        topics.clear();
        context.deleteFile(BANK_TOPICS_FILE_NAME);
    }

    @Override
    public long sizeFile(Context context) {
        saveBank(context);
        return context.getFileStreamPath(BANK_TOPICS_FILE_NAME).length();
    }
}
