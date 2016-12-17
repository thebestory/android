/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thebestory.android.R;
import com.thebestory.android.TheBestoryApplication;
import com.thebestory.android.activity.MainActivity;
import com.thebestory.android.adapter.main.TopicsAdapter;
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.api.LoaderResult;
import com.thebestory.android.api.LoaderStatus;
import com.thebestory.android.fragment.main.stories.NewStoryFragment;
import com.thebestory.android.data.main.TopicsData;
import com.thebestory.android.model.Story;
import com.thebestory.android.model.Topic;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragment for Topics screen.
 * Use the {@link TopicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<LoaderResult<List<Topic>>> {

    private View view;
    private MainActivity activity;

    TopicsAdapter adapter;

    private ArrayList<Topic> loadedTopic;

    Toolbar toolbar;

    private RecyclerView rv;

    private boolean visitOnCreateLoader;


    private TextView errorTextView;
    private ProgressBar progressView;


    public TopicsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopicsFragment.
     */
    public static TopicsFragment newInstance() {
        return new TopicsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loadedTopic = ((TheBestoryApplication) getActivity().getApplication()).
                currentLoadedTopics;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity = (MainActivity) getActivity();
        adapter = new TopicsAdapter(activity, loadedTopic, new TopicsAdapter.OnClickListener() {
            public void onClick(View v, Topic topic) {
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                ((TheBestoryApplication) activity.getApplication()).slug = topic.slug;
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.main_frame_layout, StoriesFragment.newInstance());
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_topics, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.main_topics_toolbar);

        FragmentManager fm = getFragmentManager();

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        toolbar.setTitle(R.string.navdrawer_main_topics);
        activity.setSupportActionBar(toolbar);

        rv = (RecyclerView) view.findViewById(R.id.rv_topics);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        if (savedInstanceState != null && savedInstanceState.containsKey("visit")) {
            visitOnCreateLoader = savedInstanceState.getBoolean("visit");
            displayNonEmptyData();
        } else {
            if (loadedTopic.isEmpty()) {
                getLoaderManager().restartLoader(0, null, this);
            } else {
                displayNonEmptyData();
            }
        }

        return view;
    }


    @Override
    public Loader<LoaderResult<List<Topic>>> onCreateLoader(int id, Bundle args) {
        Log.w("onCreateLoader", "Loading...");
        Loader<LoaderResult<List<Topic>>> temp;
        temp = ApiMethods.getInstance().getTopicsList(getActivity());
        //temp.startLoading();
        visitOnCreateLoader = true;
        return temp;
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<Topic>>> loader, LoaderResult<List<Topic>> result) {
        switch (result.status) {
            case OK: {
                Log.w("onFinished", "OK");
                if (!result.data.isEmpty()) {
                    if (visitOnCreateLoader) {
                        loadedTopic.addAll(result.data);
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
                //TODO: Try to write this)))
                break;
            }
        }
        visitOnCreateLoader = false;
    }


    @Override
    public void onLoaderReset(Loader<LoaderResult<List<Topic>>> loader) {
        displayEmptyData();
    }

    private void displayEmptyData() {
        progressView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.topics_not_found);
    }

    private void displayNonEmptyData() {
        if (adapter != null) {
                adapter.addTopics();
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_topics, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.main_stories_toolbar_action_new:
                transaction.replace(R.id.main_frame_layout, NewStoryFragment.newInstance());
                break;
        }

        transaction.addToBackStack(null).commit();
        return super.onOptionsItemSelected(item);
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
