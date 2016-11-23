/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebestory.android.R;
import com.thebestory.android.activity.MainActivity;

/**
 * Fragment for Debug screen.
 * TODO: Create debug screen
 * Use the {@link DebugFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebugFragment extends Fragment {
    private View view;
    private MainActivity activity;

    public DebugFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DebugFragment.
     */
    public static DebugFragment newInstance() {
        return new DebugFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_debug, container, false);
        activity = (MainActivity) getActivity();

        return view;
    }
}
