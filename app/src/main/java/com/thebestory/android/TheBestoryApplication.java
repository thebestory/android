/*
 * The Bestory Project
 */

package com.thebestory.android;

import android.app.Application;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;

import android.support.v4.content.AsyncTaskLoader;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.api.LoaderResult;
import com.thebestory.android.api.LoaderStatus;
import com.thebestory.android.model.Topic;
import com.thebestory.android.util.BankStoriesLocation;
import com.thebestory.android.util.BankTopics;
import com.thebestory.android.util.CacheStories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * The Bestory application entry point.
 */
public class TheBestoryApplication extends Application implements LoaderManager.LoaderCallbacks<LoaderResult<List<Topic>>> {
    public Topic currentTopic;

    @Override
    public void onCreate() {
        super.onCreate();

        BankStoriesLocation.getInstance().loadBank(getApplicationContext());
        CacheStories.getInstance().loadCache(getApplicationContext());
        BankTopics.getInstance().loadBank(getApplicationContext());
        currentTopic = BankTopics.getInstance().getAllTopic();
        Fresco.initialize(this);
    }


    @Override
    public Loader<LoaderResult<List<Topic>>> onCreateLoader(int id, Bundle args) {
        return ApiMethods.getInstance().getTopicsList(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<Topic>>> loader, LoaderResult<List<Topic>> data) {
        if (data.status == LoaderStatus.OK) {
            BankTopics.getInstance().loadAndUpdateTopics(data.data);
        } else if (data.status == LoaderStatus.WARNING) {

        } else {

        }
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult<List<Topic>>> loader) {
    }
}
