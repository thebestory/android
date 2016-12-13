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
import com.thebestory.android.data.main.TopStoriesData;
import com.thebestory.android.model.Story;

import java.util.List;

/**
 * Fragment for Top tab on Stories screen.
 * Use the {@link TopTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<LoaderResult<List<Story>>> {

    private View view;
    final TopTabFragment thisFragment = this;

    private RecyclerView rv;
    private TextView errorTextView;
    private ProgressBar progressView;

    private boolean used;
    private boolean flagForLoader;

    @Nullable
    private StoriesAdapter adapter;
    private TopStoriesData topStoriesData;

    public TopTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopTabFragment.
     */
    public static TopTabFragment newInstance() {
        return new TopTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StoriesAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_top_tab, container, false);

        FragmentManager fm = getFragmentManager();

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        topStoriesData = (TopStoriesData) fm.findFragmentByTag(TopStoriesData.TAG);


        rv = (RecyclerView) view.findViewById(R.id.rv_stories_top_tab);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rv.addItemDecoration(new RecylcerDividersDecorator(R.color.colorPrimaryDark));
        rv.setAdapter(adapter);

        if (topStoriesData == null) {
            topStoriesData = new TopStoriesData();
            fm.beginTransaction().add(topStoriesData, TopStoriesData.TAG).commit();
        }

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        /*if (savedInstanceState != null) {
            displayNonEmptyData(topStoriesData.getCurrentStories());
        } else {
            flagForLoader = true;
            //Log.e("onCreateView: ", "i am here");
            getLoaderManager().initLoader(2, null, this);
        }*/

        if (savedInstanceState != null && savedInstanceState.containsKey("Used")) {
            used = savedInstanceState.getBoolean("Used");
            flagForLoader = true;
            getLoaderManager().restartLoader(2, null, this);
        } else {
            getLoaderManager().initLoader(2, null, this);
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
                    getLoaderManager().restartLoader(2, null, thisFragment);
                }
            }
        });

        return view;
    }

    @Override
    public Loader<LoaderResult<List<Story>>> onCreateLoader(int id, Bundle args) {
        String currentId = topStoriesData.getLastId();
        Loader<LoaderResult<List<Story>>> temp;
        if (currentId.equals("0")) {
            temp = ApiMethods.getInstance().getTopStories(getActivity(),
                    ((TheBestoryApplication) getActivity().getApplication()).slug,
                    TypeOfCollection.NONE, null, 10);
        } else {
            temp = ApiMethods.getInstance().getTopStories(getActivity(),
                    ((TheBestoryApplication) getActivity().getApplication()).slug,
                    TypeOfCollection.AFTER, currentId, 10);
        }
        temp.startLoading();
        return temp;
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<Story>>> loader, LoaderResult<List<Story>> result) {
        /*flagForLoader = result.data.isEmpty();

        if (result.status == LoaderStatus.OK) {
            if (!result.data.isEmpty()) {
                displayNonEmptyData(result.data);
                topStoriesData.getCurrentStories().addAll(result.data);
            } else if (topStoriesData.getCurrentStories().isEmpty()) {
                displayEmptyData();
            }
        } else {
            displayError(result.status);
        }*/

        switch (result.status) {

            case OK: {
                flagForLoader = result.data.isEmpty();
                if (!result.data.isEmpty() || result.data.isEmpty()) {
                    if (!result.data.isEmpty()) {
                        topStoriesData.getCurrentStories().addAll(result.data);
                    }
                    displayNonEmptyData(result.data);
                } else if (topStoriesData.getCurrentStories().isEmpty()) {
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
    public void onDestroy() {
        super.onDestroy();
        topStoriesData = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
