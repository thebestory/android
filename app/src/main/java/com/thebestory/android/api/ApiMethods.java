/*
 * The Bestory Project
 */

package com.thebestory.android.api;

import android.content.Context;
import android.os.Bundle;

import com.thebestory.android.api.parseResponse.ParseStoriesArray;
import com.thebestory.android.api.parseResponse.ParseStory;
import com.thebestory.android.api.parseResponse.ParseTopic;
import com.thebestory.android.api.parseResponse.ParseTopicsArray;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.Stories.GetDetailsStory;
import com.thebestory.android.api.urlCollection.Stories.GetHotStories;
import com.thebestory.android.api.urlCollection.Stories.GetLatestStories;
import com.thebestory.android.api.urlCollection.Stories.GetRandomStories;
import com.thebestory.android.api.urlCollection.Stories.GetTopStories;
import com.thebestory.android.api.urlCollection.Stories.SubmitStory;
import com.thebestory.android.api.urlCollection.Topics.GetDetailsTopic;
import com.thebestory.android.api.urlCollection.Topics.GetTopicStories;
import com.thebestory.android.api.urlCollection.Topics.GetTopicsList;
import com.thebestory.android.api.urlCollection.TypeOfCollection;
import com.thebestory.android.model.Story;
import com.thebestory.android.model.Topic;

import java.util.List;

public class ApiMethods {
    private static ApiMethods ourInstance = new ApiMethods();

    public static ApiMethods getInstance() {
        return ourInstance;
    }

    private ApiMethods() {
    }

    private void addTypeOfCollection(Bundle requestBundle, TypeOfCollection typeOf, String id) {
        switch (typeOf) {
            case AROUND:
                requestBundle.putString("around", id);
                break;
            case BEFORE:
                requestBundle.putString("before", id);
                break;
            case AFTER:
                requestBundle.putString("after", id);
                break;
            default:
                break;
        }
    }

    public ApiAsyncTask<Story> getDetailsStory(Context context, String id) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("id", id);
        ApiAsyncTask task = new ApiAsyncTask(context, new GetDetailsStory(), new ParseStory(), requestBundle);
        return task;
    }

    private ApiAsyncTask<List<Story>> getTeamplateStories(Context context, TypeOfCollection typeOf, String id, int limit, ParseUrl parserUrl) {
        Bundle requestBundle = new Bundle();
        addTypeOfCollection(requestBundle, typeOf, id);
        requestBundle.putInt("limit", limit);
        ApiAsyncTask task = new ApiAsyncTask(context, parserUrl, new ParseStoriesArray(), requestBundle);
        return task;
    }

    public ApiAsyncTask<List<Story>> getLatestStories(Context context, TypeOfCollection typeOf, String id, int limit) {
        return getTeamplateStories(context, typeOf, id, limit, new GetLatestStories());
    }

    public ApiAsyncTask<List<Story>> getHotStories(Context context, TypeOfCollection typeOf, String id, int limit) {
        return getTeamplateStories(context, typeOf, id, limit, new GetHotStories());
    }

    public ApiAsyncTask<List<Story>> getRandomStories(Context context, TypeOfCollection typeOf, String id, int limit) {
        return getTeamplateStories(context, typeOf, id, limit, new GetRandomStories());
    }

    public ApiAsyncTask<List<Story>> getTopStories(Context context, TypeOfCollection typeOf, String id, int limit) {
        return getTeamplateStories(context, typeOf, id, limit, new GetTopStories());
    }

    public ApiAsyncTask<Boolean> SubmitStory(Context context, String story) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("story", story);
        ApiAsyncTask task = new ApiAsyncTask(context, new SubmitStory(), null, requestBundle);
        return task;
    }

    public ApiAsyncTask<Topic> getDetailsTopic(Context context, int id) {
        Bundle requestBundle = new Bundle();
        requestBundle.putInt("id", id);
        ApiAsyncTask task = new ApiAsyncTask(context, new GetDetailsTopic(), new ParseTopic(), requestBundle);
        return task;
    }

    public ApiAsyncTask<List<Topic>> getTopicsList(Context context) {
        ApiAsyncTask task = new ApiAsyncTask(context, new GetTopicsList(), new ParseTopicsArray(), null);
        return task;
    }

    public ApiAsyncTask<List<Story>> getTopicStories(Context context, int topicId, TypeOfCollection typeOf, String timeStamp, int limit) {
        Bundle requestBundle = new Bundle();
        requestBundle.putInt("topicId", topicId);
        addTypeOfCollection(requestBundle, typeOf, timeStamp);

        ApiAsyncTask task = new ApiAsyncTask(context, new GetTopicStories(), new ParseStoriesArray(), requestBundle);
        return task;
    }

}
