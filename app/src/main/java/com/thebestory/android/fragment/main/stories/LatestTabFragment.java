/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main.stories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thebestory.android.R;
import com.thebestory.android.adapter.main.StoriesAdapter;
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.api.LoaderResult;
import com.thebestory.android.api.LoaderStatus;
import com.thebestory.android.api.urlCollection.TypeOfCollection;
import com.thebestory.android.data.main.LatestStoriesData;
import com.thebestory.android.model.Story;

import java.util.List;

/**
 * Fragment for Recent tab on Stories screen.
 * Use the {@link LatestTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LatestTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<LoaderResult<List<Story>>> {

    private View view;
    final LatestTabFragment thisFragment = this;

    private RecyclerView rv;
    private TextView errorTextView;
    private ProgressBar progressView;

    private boolean used;
    private boolean flagForLoader;

    @Nullable
    private StoriesAdapter adapter;
    private LatestStoriesData latestStoriesData;

    public LatestTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LatestTabFragment.
     */
    public static LatestTabFragment newInstance() {
        return new LatestTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StoriesAdapter(getActivity());
        Log.w("!!!!!!!", "Really onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_latest_tab, container, false);

        FragmentManager fm = getFragmentManager();

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        latestStoriesData = (LatestStoriesData) fm.findFragmentByTag(LatestStoriesData.TAG);

        rv = (RecyclerView) view.findViewById(R.id.rv_stories_latest_tab);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rv.addItemDecoration(new RecylcerDividersDecorator(R.color.colorPrimaryDark));
        rv.setAdapter(adapter);

        if (latestStoriesData == null) {
            Log.w("!!!!!!!", "latestStoriesData = null");
            latestStoriesData = new LatestStoriesData();
            fm.beginTransaction().add(latestStoriesData, LatestStoriesData.TAG).commit();
        }

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        Log.w("!!!!!!!", "onCreate");

        if (savedInstanceState != null && savedInstanceState.containsKey("Used")) {
            used = savedInstanceState.getBoolean("Used");
            flagForLoader = true;
            getLoaderManager().restartLoader(0, null, this);
        } else {
            getLoaderManager().initLoader(0, null, this);
        }

        /*if (savedInstanceState != null) {
            Log.w("!!!!!!!", "OnCreate, no null savedInstanceState");
            displayNonEmptyData(latestStoriesData.getCurrentStories());
        } else { //TODO: I have to think about this problem. What happened when fragment Destroy? LoaderManager has lived? Nariman, DON'T DELETE. It's important.
            Log.w("!!!!!!!", "OnCreate, null savedInstanceState");
            flagForLoader = true;
            getLoaderManager().initLoader(0, null, this);
        }*/


        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (flagForLoader) {
                    return;
                }
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (llm.findLastVisibleItemPosition() + 3 >= adapter.getItemCount()) {
                    flagForLoader = true;
                    getLoaderManager().restartLoader(0, null, thisFragment);
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        latestStoriesData = null;
    }

    @Override
    public Loader<LoaderResult<List<Story>>> onCreateLoader(int id, Bundle args) {
        String currentId = latestStoriesData.getLastId();
        Loader<LoaderResult<List<Story>>> temp;
        if (currentId.equals("0")) {
            temp = ApiMethods.getInstance().getLatestStories(getActivity(), TypeOfCollection.NONE, null, 10);
        } else {
            temp = ApiMethods.getInstance().getLatestStories(getActivity(), TypeOfCollection.AFTER, currentId, 10);
        }
        temp.startLoading();
        return temp;
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<Story>>> loader, LoaderResult<List<Story>> result) {
        switch (result.status) {
            case OK: {
                flagForLoader = result.data.isEmpty();
                if (!result.data.isEmpty() || result.data.isEmpty()) {
                    if (!result.data.isEmpty()) {
                        latestStoriesData.getCurrentStories().addAll(result.data);
                    }
                    displayNonEmptyData(result.data);
                } else if (latestStoriesData.getCurrentStories().isEmpty()) {
                    displayEmptyData();
                }
                break;
            }
            case ERROR: {
                displayError(result.status);
                break;
            }
            case WARNING: {
                flagForLoader = result.data.isEmpty();
                //TODO: Try to write this)))
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult<List<Story>>> loader) {
        displayEmptyData();
    }


    private void displayEmptyData() {
        progressView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.stories_not_found);
    }

    private void displayNonEmptyData(List<Story> stories) {
        if (adapter != null) {
            if (!stories.isEmpty()) {
                adapter.addStories(stories);
            }
        }
        progressView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    private void displayError(LoaderStatus resultType) {
        progressView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        final int messageResId;
        if (resultType == LoaderStatus.ERROR) { //TODO: Add in LoaderStatus NO_INTERNET
            messageResId = R.string.no_internet;
        } else {
            messageResId = R.string.error;
        }
        errorTextView.setText(messageResId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("Used", true);
    }
}
