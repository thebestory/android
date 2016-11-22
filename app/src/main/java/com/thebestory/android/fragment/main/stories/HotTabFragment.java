/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main.stories;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebestory.android.R;

/**
 * Fragment for Hot tab on Stories screen.
 * Use the {@link HotTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotTabFragment extends Fragment { //TODO: When we create RecentTabFragment -> Do it!
    private View view;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_hot_tab, container, false);
        return view;
    }

}
