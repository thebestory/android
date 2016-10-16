package com.thebestory.android.api;

import com.thebestory.android.models.Story;

import java.util.ArrayList;

/**
 * Created by Alex on 16.10.2016.
 */
public class ApiMethods {
    private static ApiMethods ourInstance = new ApiMethods();

    public static ApiMethods getInstance() {
        return ourInstance;
    }

    private ApiMethods() {
    }

    public void getStoryes (int topicId, int startStoryId, int count, CallBack<ArrayList<Story>> func) {
        ArrayList<Story> response = new ArrayList<>();
        response.add(new Story(startStoryId, topicId, 0, "Hello world!"));
        func.callBack(response);
    }

    public void getTopics() {

    }
}
