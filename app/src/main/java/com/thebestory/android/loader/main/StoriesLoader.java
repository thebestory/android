/*
 * The Bestory Project
 */

package com.thebestory.android.loader.main;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.urlconnection.StethoURLConnectionManager;
import com.thebestory.android.model.Story;
import com.thebestory.android.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;

/*
@deprecated class was written Oktai
*/

@Deprecated
public class StoriesLoader extends AsyncTaskLoader<LoadResult<List<Story>>> {
    public final String TAG = "Stories";

    private LoadResult<List<Story>> result;

    private final int currentIdStories;

    public StoriesLoader(Context context, int currentIdStories) {
        super(context);
        this.currentIdStories = currentIdStories;
    }

    @Override
    protected void onStartLoading() {

        if (result == null || result.resultType != ResultType.OK) {
            forceLoad();
        } else {
            deliverResult(result); //return data to Activity
        }
    }


    @Override
    public LoadResult<List<Story>> loadInBackground() {

        final StethoURLConnectionManager stethoManager = new StethoURLConnectionManager("API");

        ResultType resultType = ResultType.ERROR;
        List<Story> data = null;

        HttpURLConnection connection = null;
        InputStream in = null;

        try {
            //connection = /**NAME_CLASS**/.//getRecentStoriesRequest(currentIdStories); //ToDo: Alex create/change API and create this method
            Log.d(TAG, "Performing request: " + connection.getURL());

            stethoManager.preConnect(connection, null);
            connection.connect();
            stethoManager.postConnect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                in = connection.getInputStream();
                in = stethoManager.interpretResponseStream(in);

                //data = Story.parseStory(in); //ToDo: Alex change parseStory(in) in class Story

                resultType = ResultType.OK;

            } else {
                // consider all other codes as errors
                throw new BadResponseException("HTTP: " + connection.getResponseCode()
                        + ", " + connection.getResponseMessage());
            }


        } catch (MalformedURLException e) {
            Log.e(TAG, "Failed to get movies", e);

        } catch (IOException e) {
            stethoManager.httpExchangeFailed(e);
            if (IOUtils.isConnectionAvailable(getContext(), false)) {
                resultType = ResultType.ERROR;
            } else {
                resultType = ResultType.NO_INTERNET;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to get movies: ", e);

        } finally {
            IOUtils.closeSilently(in);
            if (connection != null) {
                connection.disconnect();
            }
        }

        result = new LoadResult<>(resultType, data);
        return result;
    }

    public LoadResult<List<Story>> getResult() {
        return result;
    }
}