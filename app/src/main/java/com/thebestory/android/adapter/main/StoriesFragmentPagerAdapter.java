/*
 * The Bestory Project
 */

package com.thebestory.android.adapter.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thebestory.android.fragment.main.StoriesFragment;
import com.thebestory.android.fragment.main.stories.*;
import com.thebestory.android.util.StoriesType;

import static com.thebestory.android.R.string.main_stories_latest_tab;
import static com.thebestory.android.R.string.main_stories_hot_tab;
import static com.thebestory.android.R.string.main_stories_top_tab;
import static com.thebestory.android.R.string.main_stories_random_tab;


public class StoriesFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    /**
     * Tabs resource ids for titles
     */
    private static final int[] TAB_TITLE_RES_ID = new int[]{
            main_stories_latest_tab,
            main_stories_hot_tab,
            main_stories_top_tab,
            main_stories_random_tab
    };

    public StoriesFragmentPagerAdapter(FragmentManager fm, Context context, String slug) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(TAB_TITLE_RES_ID[position]);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StoriesTabFragment.newInstance(StoriesType.LATEST);
            case 1:
                return StoriesTabFragment.newInstance(StoriesType.HOT);
            case 2:
                return StoriesTabFragment.newInstance(StoriesType.TOP);
            case 3:
                return StoriesTabFragment.newInstance(StoriesType.RANDOM);
        }

        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLE_RES_ID.length;
    }
}
