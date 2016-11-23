/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main.stories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebestory.android.R;
import com.thebestory.android.activity.MainActivity;

/**
 * Fragment for New story screen.
 * TODO: When we come up how to post request -> Do it!
 * Use the {@link NewStoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewStoryFragment extends Fragment {
    private View view;
    private AppCompatActivity activity;

    private Toolbar toolbar;

    public NewStoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewStoryFragment.
     */
    public static NewStoryFragment newInstance() {
        return new NewStoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_new, container, false);
        activity = (MainActivity) getActivity();

        toolbar = (Toolbar) view.findViewById(R.id.main_stories_new_toolbar);

        toolbar.setTitle(R.string.main_stories_new_toolbar_title);
        activity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_stories_new, menu);
    }
}
