package com.thebestory.android.api;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.thebestory.android.api.parseResponse.ParseStoriesArray;
import com.thebestory.android.api.parseResponse.ParseTopicsArray;
import com.thebestory.android.api.urlCollection.GetStoryUrl;
import com.thebestory.android.api.urlCollection.GetTopicsUrl;
import com.thebestory.android.models.Story;
import com.thebestory.android.models.Topic;

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

    public AsyncTaskLoader<ArrayList<Story>> getStoryesTask (Context context, int topicId, int startStoryId, int count) {
        Bundle requestBundle = new Bundle();
        requestBundle.putInt("topicId", topicId);
        requestBundle.putInt("startStoryId", startStoryId);
        requestBundle.putInt("count", count);

        ApiAsyncTask task = new ApiAsyncTask(context, new GetStoryUrl(), new ParseStoriesArray(), requestBundle);

        return task;
    }

    public AsyncTaskLoader<ArrayList<Topic>>  getTopicsTask (Context context) {
        ApiAsyncTask task = new ApiAsyncTask(context, new GetTopicsUrl(), new ParseTopicsArray(), null);
        return task;
    }
}
