package com.thebestory.android.fragment.main.stories;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.thebestory.android.api.ApiAsyncTask;
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.api.LoaderResult;
import com.thebestory.android.api.LoaderStatus;
import com.thebestory.android.api.urlCollection.TypeOfCollection;
import com.thebestory.android.model.Story;
import com.thebestory.android.util.BankStoriesLocation;
import com.thebestory.android.util.StoriesArray;
import com.thebestory.android.util.StoriesType;

import java.util.List;

/**
 * Created by Alex on 05.03.2017.
 */

public final class StoriesTabFragment extends Fragment implements LoaderManager.
        LoaderCallbacks<LoaderResult<List<Story>>>, SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private final StoriesTabFragment thisFragment = this;

    private RecyclerView rv;
    private TextView errorTextView;
    private ProgressBar progressView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean visitOnCreateLoader;
    private boolean flagForLoader;

    private StoriesArray loadedStories;

    @Nullable
    private StoriesAdapter adapter;

    private StoriesType type;


    public StoriesTabFragment() {
        super();
        this.type = StoriesType.LATEST;
    }


    public void setType(StoriesType type) {
        this.type = type;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StoriesTabFragment.
     */
    public static StoriesTabFragment newInstance(StoriesType type) {
        StoriesTabFragment temp = new StoriesTabFragment();
        temp.setType(type);
        return temp;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String currentId = ((TheBestoryApplication) getActivity().getApplication()).currentTopic.id;
        loadedStories = BankStoriesLocation.getInstance().getStoriesArray(type, currentId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StoriesAdapter(getActivity(), loadedStories);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_tab, container, false);

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        rv = (RecyclerView) view.findViewById(R.id.rv_stories_tab);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(
                Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        if (savedInstanceState != null && savedInstanceState.containsKey("visit")) {
            flagForLoader = true;
            visitOnCreateLoader = savedInstanceState.getBoolean("visit");
            displayNonEmptyData();
        } else {
            if (loadedStories.isEmpty()) {
                Bundle bundle = new Bundle();
                bundle.putString("request", "none");
                getLoaderManager().restartLoader(2, bundle, this);
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

                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (adapter != null
                        && llm.findLastVisibleItemPosition() + 3 >= adapter.getItemCount()) {
                    flagForLoader = true;
                    Bundle bundle = new Bundle();
                    if (loadedStories.isEmpty()) {
                        bundle.putString("request", "none");
                    } else {
                        bundle.putString("request", "after");
                    }
                    getLoaderManager().restartLoader(2, bundle, thisFragment);
                }
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        Bundle bundle = new Bundle();
        if (!loadedStories.isEmpty()) {
            bundle.putString("request", "before");
            getLoaderManager().restartLoader(2, bundle, thisFragment);
        } else {
            bundle.putString("request", "none");
            getLoaderManager().restartLoader(2, bundle, thisFragment);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public Loader<LoaderResult<List<Story>>> onCreateLoader(int id, Bundle args) {
        Loader<LoaderResult<List<Story>>> temp = null;
        if (args != null && args.containsKey("request") && args.getString("request") != null) {
            switch (args.getString("request")) {
                case "before": {
                    String currentStoryId = loadedStories.getStoryAt(0).id;
                    temp = ApiMethods.getInstance().getStories(type, getActivity(),
                            ((TheBestoryApplication) getActivity().getApplication()).currentTopic.id,
                            TypeOfCollection.BEFORE, currentStoryId, 25);
                    break;
                }
                case "none": {
                    temp = ApiMethods.getInstance().getStories(type, getActivity(),
                            ((TheBestoryApplication) getActivity().getApplication()).currentTopic.id,
                            TypeOfCollection.NONE, null, 10);
                    break;
                }
                case "after": {
                    String currentStoryId = loadedStories.getStoryAt(loadedStories.size() - 1).id;
                    temp = ApiMethods.getInstance().getStories(type, getActivity(),
                            ((TheBestoryApplication) getActivity().getApplication()).currentTopic.id,
                            TypeOfCollection.AFTER, currentStoryId, 10);
                    break;
                }
            }
        }
        visitOnCreateLoader = true;
        return temp;
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<Story>>> loader, LoaderResult<List<Story>> result) {
        Log.e("TAG", result.status.toString());
        switch (result.status) {
            case OK: {
                Log.w("onFinished", "OK");
                flagForLoader = false;
                if (!result.data.isEmpty()) {
                    if (visitOnCreateLoader) {
                        TypeOfCollection typeOfCollection = ((ApiAsyncTask) loader).getRequestType();
                        switch (typeOfCollection) {
                            case BEFORE: {
                                if (result.data.size() < 15) {
                                    loadedStories.addAllStoryAtHead(result.data);
                                } else {
                                    if (adapter != null) {
                                        adapter.clear();
                                    }
                                    loadedStories.addAllStoryAtTail(result.data);
                                }
                                displayNonEmptyData(result.data.size(), typeOfCollection);
                                break;
                            }
                            case NONE: {
                                loadedStories.addAllStoryAtTail(result.data);
                                displayNonEmptyData(result.data.size(), typeOfCollection);
                                break;
                            }
                            case AFTER: {
                                loadedStories.addAllStoryAtTail(result.data);
                                displayNonEmptyData(result.data.size(), typeOfCollection);
                                break;
                            }
                        }
                    } else {
                        displayNonEmptyData();
                    }
                } else {
                    if (loadedStories.size() == 0) {
                        displayEmptyData();
                    }
                }
                break;
            }
            case WARNING:
            case ERROR: {
                displayError(result.status);
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

    private void displayNonEmptyData(int size, TypeOfCollection typeOfCollection) {
        if (adapter != null) {
            if (TypeOfCollection.BEFORE == typeOfCollection) {
                adapter.addFirstStories(size);
            } else {
                adapter.addLastStories(size);
            }
        }
        progressView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    private void displayNonEmptyData() {
        progressView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    private void displayError(LoaderStatus resultType) {
        if ((adapter != null ? adapter.getItemCount() : 0) == 0) {
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
        } else {
            Snackbar.make(
                    getActivity().findViewById(R.id.main_stories_layout),
                    R.string.no_internet,
                    Snackbar.LENGTH_LONG
            ).show();
        }
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
