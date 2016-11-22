/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main.stories;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thebestory.android.R;
import com.thebestory.android.adapter.main.StoriesFragmentForStoryAdapter;
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.api.LoaderResult;
import com.thebestory.android.api.LoaderStatus;
import com.thebestory.android.api.urlCollection.TypeOfCollection;
import com.thebestory.android.loader.main.StoriesData;
import com.thebestory.android.model.Story;

import java.util.List;

/**
 * Fragment for Recent tab on Stories screen.
 * Use the {@link RecentTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<LoaderResult<List<Story>>> {

    private View view;
    final RecentTabFragment thisFragment = this;

    private List<Story> stories;
    private RecyclerView rv;
    private TextView errorTextView;
    private ProgressBar progressView;

    private boolean flagForLoader;
    private int currentIdStories;

    @Nullable
    private StoriesFragmentForStoryAdapter adapter;
    private StoriesData storiesData;

    public RecentTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecentTabFragment.
     */
    public static RecentTabFragment newInstance() {
        return new RecentTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_recent_tab, container, false);

        FragmentManager fm = getFragmentManager();

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        adapter = new StoriesFragmentForStoryAdapter(getActivity());
        storiesData = (StoriesData) fm.findFragmentByTag(StoriesData.TAG);


        rv = (RecyclerView) view.findViewById(R.id.rv_stories_recent_tab);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rv.addItemDecoration(new RecylcerDividersDecorator(R.color.colorPrimaryDark));
        rv.setAdapter(adapter);

        if (storiesData == null) {
            storiesData = new StoriesData();
            fm.beginTransaction().add(storiesData, StoriesData.TAG).commit();
        }

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            currentIdStories = savedInstanceState.getInt("currentIdStories");
            displayNonEmptyData(storiesData.getCurrentStories());
        } else {
            currentIdStories = 0;
            flagForLoader = true;
            //Log.e("onCreateView: ", "i am here");
            getLoaderManager().initLoader(currentIdStories, null, this);
        }

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
                    getLoaderManager().initLoader(++currentIdStories, null, thisFragment);
                }
            }
        });

        return view;
    }

    @Override
    public Loader<LoaderResult<List<Story>>> onCreateLoader(int id, Bundle args) {
        //return new StoriesLoader(getActivity(), this.currentIdStories);
        Log.e("onCreateView: ", "i am here");
        Loader<LoaderResult<List<Story>>> temp = ApiMethods.getInstance().getLatestStories(getActivity(), TypeOfCollection.NONE, null, 5);
        temp.forceLoad();
        return temp;
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<Story>>> loader, LoaderResult<List<Story>> result) {
        flagForLoader = false;
        if (result.status == LoaderStatus.OK) {
            if (result.data != null && !result.data.isEmpty()) {
                displayNonEmptyData(result.data);
                storiesData.getCurrentStories().addAll(result.data);
            } else {
                displayEmptyData();
            }
        } else {
            displayError(result.status);
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
            adapter.addStories(stories);
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

    private void initializeAdapter() {
        //StoriesFragmentForStoryAdapter adapter = new StoriesFragmentForStoryAdapter(stories);
        //rv.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentIdStories", currentIdStories);
    }

}
