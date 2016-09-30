package com.thebestory.android.adapter.main;

import android.content.Context;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.thebestory.android.R;
import com.thebestory.android.fragment.main.stories.HotTabFragment;
import com.thebestory.android.fragment.main.stories.RandomTabFragment;
import com.thebestory.android.fragment.main.stories.RecentTabFragment;
import com.thebestory.android.fragment.main.stories.TopTabFragment;

public class StoriesFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private static final int[] TAB_TITLE_RES_ID = new int[]{
            R.string.main_stories_tab_recent,
            R.string.main_stories_tab_hot,
            R.string.main_stories_tab_top,
            R.string.main_stories_tab_random
    };

    public StoriesFragmentPagerAdapter(FragmentManager fm, Context context) {
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
                return RecentTabFragment.newInstance();
            case 1:
                return HotTabFragment.newInstance();
            case 2:
                return TopTabFragment.newInstance();
            case 3:
                return RandomTabFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLE_RES_ID.length;
    }
}
