/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.thebestory.android.fragment.main.stories.SubmitStoryFragment;
import com.thebestory.android.model.Topic;
import com.thebestory.android.util.BankTopics;

import java.util.List;

/**
 * Fragment for Topics screen.
 * Use the {@link TopicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicsFragment extends Fragment implements LoaderManager.
        LoaderCallbacks<LoaderResult<List<Topic>>>, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private MainActivity activity;

    public final TopicsFragment thisFragment = this;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    TopicsAdapter adapter;

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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_topics, container, false);
        activity = (MainActivity) getActivity();

        toolbar = (Toolbar) view.findViewById(R.id.main_topics_toolbar);

        toolbar.setTitle(R.string.navdrawer_main_topics);
        activity.setSupportActionBar(toolbar);

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        adapter = new TopicsAdapter(activity, BankTopics.getInstance().getList(), new TopicsAdapter.OnOldClickListener() {
            public void onClick(View v, Topic topic) {
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                );

                ((TheBestoryApplication) activity.getApplication()).currentTopic = topic;
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.main_frame_layout, StoriesFragment.newInstance());
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.rv_topics);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(
                Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        if (savedInstanceState != null && savedInstanceState.containsKey("visit")) {
            visitOnCreateLoader = savedInstanceState.getBoolean("visit");
            displayNonEmptyData();
        } else {
            if (BankTopics.getInstance().getCount() == 0) {
                getLoaderManager().restartLoader(0, null, this);
            } else {
                displayNonEmptyData(true);
            }
        }

        return view;
    }

    @Override
    public void onRefresh() {
        if (BankTopics.getInstance().getCount() != 0) {
            displayNonEmptyData(true);
        } else {
            getLoaderManager().restartLoader(0, null, thisFragment);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public Loader<LoaderResult<List<Topic>>> onCreateLoader(int id, Bundle args) {
        Log.w("onCreateLoader", "Loading...");
        Loader<LoaderResult<List<Topic>>> temp;
        temp = ApiMethods.getInstance().getTopicsList(getActivity());
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
                        BankTopics.getInstance().loadAndUpdateTopics(result.data);
                        displayNonEmptyData();
                    } else {
                        displayNonEmptyData(true);
                    }
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

    private void displayNonEmptyData(boolean flag) {
        progressView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    private void displayNonEmptyData() {
        if (adapter != null) {
            adapter.addTopics(BankTopics.getInstance().getList());
        }
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_topics, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.main_stories_toolbar_action_new:
                transaction.replace(R.id.main_frame_layout, SubmitStoryFragment.newInstance());
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
