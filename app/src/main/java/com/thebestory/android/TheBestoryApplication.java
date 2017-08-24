/*
 * The Bestory Project
 */

package com.thebestory.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;

import com.apollographql.apollo.ApolloClient;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.api.LoaderResult;
import com.thebestory.android.api.LoaderStatus;
import com.thebestory.android.apollo.TopicsQuery;
import com.thebestory.android.files.FilesSystem;
import com.thebestory.android.model.AuthModel;
import com.thebestory.android.model.Topic;
import com.thebestory.android.new_api.AuthModelDeserializer;
import com.thebestory.android.new_api.NoTokenTheBestoryAPI;
import com.thebestory.android.util.BankTopics;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The Bestory application entry point.
 */
public class TheBestoryApplication extends Application implements LoaderManager.LoaderCallbacks<LoaderResult<List<Topic>>> {

    public Topic currentTopic; //Old
    public String currentTitleTopic;
    public ArrayList<String> currentIdTopic;
    private static NoTokenTheBestoryAPI noTokenTheBestoryAPI;
    public static SharedPreferences sharedPrefs;
    private static ApolloClient apolloClient;
    private final String BASE_URL_REST = "https://thebestory.herokuapp.com";
    private final String BASE_URL_GRAPHQL = "http://92.63.100.195:3000/graphql";

    @Override
    public void onCreate() {
        super.onCreate();

        //Fresco
        Fresco.initialize(this);

        // SharedPreferences
        sharedPrefs = getSharedPreferences("info_user", Context.MODE_PRIVATE);

        //Gson, Retrofit
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AuthModel.class, new AuthModelDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_REST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        noTokenTheBestoryAPI = retrofit.create(NoTokenTheBestoryAPI.class);

        //OkHttpClient, Apollo
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL_GRAPHQL)
                .okHttpClient(okHttpClient)
                .build();


        //Old cache, it will disappear from here!
        FilesSystem.getInstance().onOpenApp(getApplicationContext());
        currentIdTopic = new ArrayList<>();
        currentTitleTopic = "All Stories";
    }

    public static NoTokenTheBestoryAPI getNoTokenTheBestoryAPI() {
        return noTokenTheBestoryAPI;
    }

    public static ApolloClient getApolloClient() {
        return apolloClient;
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
