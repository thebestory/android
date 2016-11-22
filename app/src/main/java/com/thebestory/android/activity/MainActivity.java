/*
 * The Bestory Project
 */

package com.thebestory.android.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.thebestory.android.R;
import com.thebestory.android.fragment.main.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * Navigation Drawer (Menu) resource ids for list
     */
    private static final int[] NAVDRAWER_MENU_RES_ID = new int[]{
            R.id.navdrawer_main_stories,
            R.id.navdrawer_main_topics,
            R.id.navdrawer_main_settings,
            R.id.navdrawer_main_about,
            R.id.navdrawer_main_send_feedback,
            R.id.navdrawer_main_debug
    };

    /**
     * Navigation Drawer (Menu) resource ids for titles
     */
    private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.navdrawer_main_stories,
            R.string.navdrawer_main_topics,
            R.string.navdrawer_main_settings,
            R.string.navdrawer_main_about,
            R.string.navdrawer_main_send_feedback,
            R.string.navdrawer_main_debug
    };

    /**
     * Navigation Drawer (Menu) resource ids for icons
     */
    private static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_navdrawer_main_stories,
            R.drawable.ic_navdrawer_main_topics,
            R.drawable.ic_navdrawer_main_settings,
            R.drawable.ic_navdrawer_main_about,
            R.drawable.ic_navdrawer_main_send_feedback,
            R.drawable.ic_navdrawer_main_debug
    };

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.main_navdrawer);
        navigationView.setNavigationItemSelectedListener(this);

        getFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout, StoriesFragment.newInstance()).commit();
    }

    @Override
    public void onBackPressed() {
        // TODO: Replace w/ native library
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.navdrawer_main_stories:
                transaction.replace(R.id.main_frame_layout, StoriesFragment.newInstance());
                break;
            case R.id.navdrawer_main_topics:
                transaction.replace(R.id.main_frame_layout, TopicsFragment.newInstance());
                break;
            case R.id.navdrawer_main_settings:
                transaction.replace(R.id.main_frame_layout, SettingsFragment.newInstance());
                break;
            case R.id.navdrawer_main_about:
                transaction.replace(R.id.main_frame_layout, AboutFragment.newInstance());
                break;
            case R.id.navdrawer_main_send_feedback:
                // TODO: Send feedback (switch to Play Market)
                break;
            case R.id.navdrawer_main_debug:
                transaction.replace(R.id.main_frame_layout, DebugFragment.newInstance());
                break;
        }

        transaction.addToBackStack(null).commit();

        // TODO: Replace w/ native library
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // TODO: next two methods close keyboard from NewStoryFragment
    // Update: It's a crutch, we need to change it
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }
}
