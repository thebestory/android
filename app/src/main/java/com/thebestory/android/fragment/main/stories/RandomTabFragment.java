/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main.stories;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thebestory.android.R;

/**
 * Fragment for Random tab on Stories screen.
 * Use the {@link RandomTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomTabFragment extends Fragment { //TODO: When we create RecentTabFragment -> Do it!
    private View view;

    public RandomTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RandomTabFragment.
     */
    public static RandomTabFragment newInstance() {
        return new RandomTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_random_tab, container, false);
        return view;
    }

}
