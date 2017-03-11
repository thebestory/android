package com.thebestory.android.fragment.main.stories;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by Alex on 10.03.2017.
 */

public class BookmarkedStoriesFragment extends Fragment {

    private View view;

    private RecyclerView rv;
    private TextView errorTextView;


    private StoriesArray loadedStories;

    @Nullable
    private StoriesAdapter adapter;


    public BookmarkedStoriesFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StoriesTabFragment.
     */
    public static BookmarkedStoriesFragment newInstance() {
        BookmarkedStoriesFragment temp = new BookmarkedStoriesFragment();
        return temp;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loadedStories = BankStoriesLocation.getInstance().getBookmarkedStoriesArray();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StoriesAdapter(getActivity(), loadedStories);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_bookmarks, container, false);

        Toolbar temp = (Toolbar)view.findViewById(R.id.main_stories_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(temp);
        temp.setTitle(R.string.bookmarks);

        errorTextView = (TextView) view.findViewById(R.id.error_text);

        rv = (RecyclerView) view.findViewById(R.id.rv_stories_tab);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);



        if (loadedStories.isEmpty()) {
            displayEmptyData();
        } else {
            displayNonEmptyData();
        }

        return view;
    }


    private void displayEmptyData() {
        rv.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.stories_not_found);
    }

    private void displayNonEmptyData() {
        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_stories, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.main_stories_toolbar_action_new:
                transaction.replace(R.id.main_frame_layout, SubmitStoryFragment.newInstance());
                break;
//            case R.id.main_stories_toolbar_action_search:
//                // TODO: Stories search
//                break;
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
    }
}
