package com.thebestory.android.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.thebestory.android.R;
import com.thebestory.android.fragment.main.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private StoriesFragment storiesFragment;
    private TopicsFragment topicsFragment;
    private SettingsFragment settingsFragment;
    private AboutFragment aboutFragment;
    private DebugFragment debugFragment;

    private static final int[] NAVDRAWER_MENU_RES_ID = new int[]{
            R.id.navdrawer_main_stories,
            R.id.navdrawer_main_topics,
            R.id.navdrawer_main_settings,
            R.id.navdrawer_main_about,
            R.id.navdrawer_main_send_feedback,
            R.id.navdrawer_main_debug
    };

    private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.navdrawer_main_stories,
            R.string.navdrawer_main_topics,
            R.string.navdrawer_main_settings,
            R.string.navdrawer_main_about,
            R.string.navdrawer_main_send_feedback,
            R.string.navdrawer_main_debug
    };

    private static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_navdrawer_stories,
            R.drawable.ic_navdrawer_topics,
            R.drawable.ic_navdrawer_settings,
            R.drawable.ic_navdrawer_about,
            R.drawable.ic_navdrawer_send_feedback,
            R.drawable.ic_navdrawer_debug
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.main_navdrawer);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO: we have to edit this
        // Update: Why? It's okay
        storiesFragment = StoriesFragment.newInstance();
        topicsFragment = TopicsFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();
        aboutFragment = AboutFragment.newInstance();
        debugFragment = DebugFragment.newInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout, storiesFragment).commit();
    }

    //For button "Back"
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Processing click on item NavigationMenu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.navdrawer_main_stories:
                transaction.replace(R.id.main_frame_layout, storiesFragment);
                break;
            case R.id.navdrawer_main_topics:
                transaction.replace(R.id.main_frame_layout, topicsFragment);
                break;
            case R.id.navdrawer_main_settings:
                transaction.replace(R.id.main_frame_layout, settingsFragment);
                break;
            case R.id.navdrawer_main_about:
                transaction.replace(R.id.main_frame_layout, aboutFragment);
                break;
            case R.id.navdrawer_main_send_feedback:
                // TODO: Send feedback (switch to Play Market)
                break;
            case R.id.navdrawer_main_debug:
                transaction.replace(R.id.main_frame_layout, debugFragment);
                break;
        }

        transaction.addToBackStack(null).commit();

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

    public NavigationView getNavigationView() {
        return navigationView;
    }
}
