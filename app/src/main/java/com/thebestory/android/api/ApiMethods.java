/*
 * The Bestory Project
 */

package com.thebestory.android.api;

import android.content.Context;
import android.os.Bundle;

import com.facebook.imagepipeline.memory.BasePool;
import com.thebestory.android.api.parseResponse.ParseComment;
import com.thebestory.android.api.parseResponse.ParseCommentArray;
import com.thebestory.android.api.parseResponse.ParseStoriesArray;
import com.thebestory.android.api.parseResponse.ParseStory;
import com.thebestory.android.api.parseResponse.ParseTopic;
import com.thebestory.android.api.parseResponse.ParseTopicsArray;
import com.thebestory.android.api.parseUrlRequest.ParseUrl;
import com.thebestory.android.api.urlCollection.Comments.GetDetailsComment;
import com.thebestory.android.api.urlCollection.PostUrls.PostComment;
import com.thebestory.android.api.urlCollection.PostUrls.PostCommentLike;
import com.thebestory.android.api.urlCollection.PostUrls.PostCommentUnlike;
import com.thebestory.android.api.urlCollection.PostUrls.PostStoryLike;
import com.thebestory.android.api.urlCollection.PostUrls.PostStory;
import com.thebestory.android.api.urlCollection.PostUrls.PostStoryUnlike;
import com.thebestory.android.api.urlCollection.Stories.GetDetailsStory;
import com.thebestory.android.api.urlCollection.Stories.GetHotStories;
import com.thebestory.android.api.urlCollection.Stories.GetLatestStories;
import com.thebestory.android.api.urlCollection.Stories.GetRandomStories;
import com.thebestory.android.api.urlCollection.Stories.GetStoryComments;
import com.thebestory.android.api.urlCollection.Stories.GetTopStories;
import com.thebestory.android.api.urlCollection.Topics.GetDetailsTopic;
import com.thebestory.android.api.urlCollection.Topics.GetTopicsList;
import com.thebestory.android.api.urlCollection.TypeOfCollection;
import com.thebestory.android.loader.AsyncLoader;
import com.thebestory.android.loader.AsyncLoader.OnAsyncLoaderListener;
import com.thebestory.android.model.Comment;
import com.thebestory.android.model.Story;
import com.thebestory.android.model.Topic;
import com.thebestory.android.util.StoriesType;

import java.util.ArrayList;
import java.util.List;

public class ApiMethods {
    private static ApiMethods ourInstance = new ApiMethods();

    public static ApiMethods getInstance() {
        return ourInstance;
    }

    private ApiMethods() {}

    private void addTypeOfCollection(Bundle requestBundle, TypeOfCollection typeOf, String id) {
        switch (typeOf) {
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
        return new ApiAsyncTask<>(context, new GetDetailsStory(), new ParseStory(), requestBundle);
    }

    private ApiAsyncTask<List<Story>> getTemplateStories(Context context, String idTopic,
                                                         TypeOfCollection typeOf, String idStory,
                                                         int limit, ParseUrl parserUrl) {
        Bundle requestBundle = new Bundle();
        addTypeOfCollection(requestBundle, typeOf, idStory);
        requestBundle.putInt("limit", limit);
        requestBundle.putString("id", idTopic);
        return new ApiAsyncTask<>(context, parserUrl, new ParseStoriesArray(), requestBundle);
    }

    private ApiAsyncTask<List<Story>> getLatestStories(Context context, String idTopic,
                                                       TypeOfCollection typeOf, String idStory,
                                                       int limit) {
        return getTemplateStories(context, idTopic, typeOf, idStory, limit, new GetLatestStories());
    }

    private ApiAsyncTask<List<Story>> getHotStories(Context context, String idTopic,
                                                    TypeOfCollection typeOf, String idStory,
                                                    int limit) {
        return getTemplateStories(context, idTopic, typeOf, idStory, limit, new GetHotStories());
    }

    private ApiAsyncTask<List<Story>> getRandomStories(Context context, String idTopic,
                                                       TypeOfCollection typeOf, String idStory,
                                                       int limit) {
        return getTemplateStories(context, idTopic, typeOf, idStory, limit, new GetRandomStories());
    }

    private ApiAsyncTask<List<Story>> getTopStories(Context context, String idTopic,
                                                    TypeOfCollection typeOf, String idStory,
                                                    int limit) {
        return getTemplateStories(context, idTopic, typeOf, idStory, limit, new GetTopStories());
    }

    public ApiAsyncTask<List<Story>> getStories(StoriesType type, Context context, String idTopic,
                                                TypeOfCollection typeOf, String idStory, int limit) {
        switch (type) {
            case LATEST:
                return getLatestStories(context, idTopic, typeOf, idStory, limit);
            case HOT:
                return getHotStories(context, idTopic, typeOf, idStory, limit);
            case RANDOM:
                return getRandomStories(context, idTopic, typeOf, idStory, limit);
            case TOP:
                return getTopStories(context, idTopic, typeOf, idStory, limit);
            default:
                throw new BasePool.InvalidValueException("Invalid type");
        }
    }


    public ApiAsyncTask<Topic> getDetailsTopic(Context context, String id) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("id", id);
        return new ApiAsyncTask(context, new GetDetailsTopic(), new ParseTopic(), requestBundle);
    }

    public ApiAsyncTask<List<Topic>> getTopicsList(Context context) {
        return new ApiAsyncTask<>(context, new GetTopicsList(), new ParseTopicsArray(), null);
    }

    public ApiAsyncTask<Story> postStory(Context context, String content) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("content", content);
        return new ApiAsyncTask(context, new PostStory(), new ParseStory(), requestBundle);
    }


    public void postStoryLike(String id, OnAsyncLoaderListener listener) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("id", id);

//        //TODO: Add more information
//        ApiAsyncTask task = new ApiAsyncTask(context, new PostStoryLike(), new ParseStory(), requestBundle);
//        return task;

        AsyncLoader.load(new PostStoryLike(), requestBundle, listener);
    }

    public void postStoryUnlike(String id, OnAsyncLoaderListener listener) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("id", id);

//        //TODO: Add more information
//        ApiAsyncTask task = new ApiAsyncTask(context, new PostStoryUnlike(), new ParseStory(), requestBundle);
//        return task;

        AsyncLoader.load(new PostStoryUnlike(), requestBundle, listener);
    }

    public ApiAsyncTask<Comment> postCommentLike(Context context, String id) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("id", id);
        //TODO: Add more information
        return new ApiAsyncTask(context, new PostCommentLike(), new ParseComment(), requestBundle);
    }

    public ApiAsyncTask<Comment> postCommentUnlike(Context context, String id) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("id", id);
        //TODO: Add more information
        return new ApiAsyncTask(context, new PostCommentUnlike(), new ParseComment(), requestBundle);
    }

    public ApiAsyncTask<Comment> postCommentToStory(Context context, String storyId, String content) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("storyId", storyId);
        requestBundle.putString("content", content);
        return new ApiAsyncTask(context, new PostComment(), new ParseComment(), requestBundle);
    }

    public ApiAsyncTask<Comment> postCommentToComment(Context context, String commentId, String content) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("commentId", commentId);
        requestBundle.putString("content", content);
        return new ApiAsyncTask(context, new PostComment(), new ParseComment(), requestBundle);
    }

    public ApiAsyncTask<Comment> getDetailsComment(Context context, String id) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("id", id);
        return new ApiAsyncTask(context, new GetDetailsComment(), new ParseComment(), requestBundle);
    }

    public ApiAsyncTask<ArrayList<Comment>> getStoryComments(Context context, String id) {
        Bundle requestBundle = new Bundle();
        requestBundle.putString("id", id);
        return new ApiAsyncTask(context, new GetStoryComments(), new ParseCommentArray(), requestBundle);
    }

}
