/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main.stories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.thebestory.android.TheBestoryApplication;
import com.thebestory.android.adapter.main.StoriesAdapter;
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.api.LoaderResult;
import com.thebestory.android.api.LoaderStatus;
import com.thebestory.android.api.urlCollection.TypeOfCollection;
import com.thebestory.android.model.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for Recent tab on Stories screen.
 * Use the {@link LatestTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LatestTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<LoaderResult<List<Story>>> {

    private View view;
    public final LatestTabFragment thisFragment = this;

    private RecyclerView rv;
    private TextView errorTextView;
    private ProgressBar progressView;

    private boolean visitOnCreateLoader;
    private boolean flagForLoader;

    private ArrayList<Story> loadedLatestStory;

    @Nullable
    private StoriesAdapter adapter;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        loadedLatestStory = ((TheBestoryApplication) getActivity().getApplication()).
                loadedStory.get("latest");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StoriesAdapter(getActivity(), loadedLatestStory);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_latest_tab, container, false);

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        rv = (RecyclerView) view.findViewById(R.id.rv_stories_latest_tab);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        if (savedInstanceState != null && savedInstanceState.containsKey("visit")) {
            flagForLoader = true;
            visitOnCreateLoader = savedInstanceState.getBoolean("visit");
            displayNonEmptyData();
        } else {
            if (loadedLatestStory.isEmpty()) {
                getLoaderManager().restartLoader(1, null, this);
            } else {
                displayNonEmptyData();
            }
        }

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (flagForLoader) {
                    return;
                }
                Log.w("onCreate", "Scroll");
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (adapter != null
                        && llm.findLastVisibleItemPosition() + 3 >= adapter.getItemCount()) {
                    flagForLoader = true;
                    getLoaderManager().restartLoader(1, null, thisFragment);
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
    public Loader<LoaderResult<List<Story>>> onCreateLoader(int id, Bundle args) {
        Loader<LoaderResult<List<Story>>> temp;
        if (loadedLatestStory.isEmpty()) {
            temp = ApiMethods.getInstance().getLatestStories(getActivity(),
                    ((TheBestoryApplication) getActivity().getApplication()).slug,
                    TypeOfCollection.NONE, null, 10);
        } else {
            String currentId = loadedLatestStory.get(loadedLatestStory.size() - 1).id;
            temp = ApiMethods.getInstance().getLatestStories(getActivity(),
                    ((TheBestoryApplication) getActivity().getApplication()).slug,
                    TypeOfCollection.AFTER, currentId, 10);
        }
        //temp.startLoading();
        visitOnCreateLoader = true;
        return temp;
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<Story>>> loader, LoaderResult<List<Story>> result) {
        switch (result.status) {
            case OK: {
                Log.w("onFinished", "OK");
                flagForLoader = result.data.isEmpty();
                if (!result.data.isEmpty()) {
                    if (visitOnCreateLoader) {
                        loadedLatestStory.addAll(result.data);
                    }
                    displayNonEmptyData();
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
        visitOnCreateLoader = false;
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

    private void displayNonEmptyData() {
        if (adapter != null) {
            adapter.addStories();
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("visit", visitOnCreateLoader);
    }
}
