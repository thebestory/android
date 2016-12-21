/*
 * The Bestory Project
 */

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

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for Hot tab on Stories screen.
 * TODO: DON'T WORK! There are will work, when Nariman add on Server this functional
 * Use the {@link HotTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotTabFragment extends Fragment implements LoaderManager.
        LoaderCallbacks<LoaderResult<List<Story>>>, SwipeRefreshLayout.OnRefreshListener {

    private View view;
    final HotTabFragment thisFragment = this;

    private RecyclerView rv;
    private TextView errorTextView;
    private ProgressBar progressView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean visitOnCreateLoader;
    private boolean flagForLoader;

    private ArrayList<Story> loadedHotStories;

    @Nullable
    private StoriesAdapter adapter;


    public HotTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HotTabFragment.
     */
    public static HotTabFragment newInstance() {
        return new HotTabFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String currentSlug =  ((TheBestoryApplication) getActivity().getApplication()).slug;
        loadedHotStories = ((TheBestoryApplication) getActivity().getApplication()).
                loadedStories.get(currentSlug).get("hot");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StoriesAdapter(getActivity(), loadedHotStories);
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
            if (loadedHotStories.isEmpty()) {
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
                    if (loadedHotStories.isEmpty()) {
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
        if (!loadedHotStories.isEmpty()) {
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
        if (args != null && args.containsKey("request")) {
            switch (args.getString("request")) {
                case "before": {
                    String currentId = loadedHotStories.get(0).id;
                    temp = ApiMethods.getInstance().getHotStories(getActivity(),
                            ((TheBestoryApplication) getActivity().getApplication()).slug,
                            TypeOfCollection.BEFORE, currentId, 10);
                    break;
                }
                case "none": {
                    temp = ApiMethods.getInstance().getHotStories(getActivity(),
                            ((TheBestoryApplication) getActivity().getApplication()).slug,
                            TypeOfCollection.NONE, null, 10);
                    break;
                }
                case "after": {
                    String currentId = loadedHotStories.get(loadedHotStories.size() - 1).id;
                    temp = ApiMethods.getInstance().getHotStories(getActivity(),
                            ((TheBestoryApplication) getActivity().getApplication()).slug,
                            TypeOfCollection.AFTER, currentId, 10);
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
//                                if (result.data.size() < 10) {
//                                    for (int i = 0; i < result.data.size(); i++) {
//                                        loadedHotStories.add(0, result.data.get(i));
//                                    }
//                                } else {
                                    if (adapter != null) {
                                        adapter.clear();
                                    }
                                    loadedHotStories.addAll(result.data);
//                                }
                                displayNonEmptyData(result.data.size(), typeOfCollection);
                                break;
                            }
                            case NONE: {
                                loadedHotStories.addAll(result.data);
                                displayNonEmptyData(result.data.size(), typeOfCollection);
                                break;
                            }
                            case AFTER: {
                                loadedHotStories.addAll(result.data);
                                displayNonEmptyData(result.data.size(), typeOfCollection);
                                break;
                            }
                        }
                    } else {
                        displayNonEmptyData();
                    }
                }
                break;
            }
            case ERROR: {
                displayError(result.status);
                break;
            }
            case WARNING: {
                flagForLoader = false;
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
