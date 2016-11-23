/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.thebestory.android.R;
import com.thebestory.android.activity.MainActivity;
import com.thebestory.android.adapter.main.StoriesFragmentPagerAdapter;
import com.thebestory.android.fragment.main.stories.NewStoryFragment;

/**
 * Fragment for Stories screen.
 * Use the {@link StoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoriesFragment extends Fragment {
    private View view;
    private MainActivity activity;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public StoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StoriesFragment.
     */
    public static StoriesFragment newInstance() {
        return new StoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories, container, false);
        activity = (MainActivity) getActivity();
        toolbar = (Toolbar) view.findViewById(R.id.main_stories_toolbar);
        tabLayout = (TabLayout) view.findViewById(R.id.main_stories_tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.main_stories_viewpager);

        toolbar.setTitle(R.string.navdrawer_main_stories);
        activity.setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = activity.getDrawerLayout();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar, R.string.navdrawer_open, R.string.navdrawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        StoriesFragmentPagerAdapter adapter = new StoriesFragmentPagerAdapter(
                getChildFragmentManager(), activity);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_stories, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.main_stories_toolbar_action_new:
                transaction.replace(R.id.main_frame_layout, NewStoryFragment.newInstance());
                break;
            case R.id.main_stories_toolbar_action_search:
                // TODO: Stories search
                break;
        }

        transaction.addToBackStack(null).commit();
        return super.onOptionsItemSelected(item);
    }

}
