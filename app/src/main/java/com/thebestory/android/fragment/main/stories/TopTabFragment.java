/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main.stories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebestory.android.R;

/**
 * Fragment for Top tab on Stories screen.
 * TODO: When we create RecentTabFragment -> Do it!
 * Use the {@link TopTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopTabFragment extends Fragment {
    private View view;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_top_tab, container, false);
        return view;
    }

}
