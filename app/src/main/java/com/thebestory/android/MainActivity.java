package com.thebestory.android;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thebestory.android.fragment.main.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int[] NAVDRAWER_MENU_RES_ID = new int[]{
            R.id.navdrawer_stories,
            R.id.navdrawer_topics,
            R.id.navdrawer_settings,
            R.id.navdrawer_about,
            R.id.navdrawer_send_feedback,
            R.id.navdrawer_debug
    };

    private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.navdrawer_stories,
            R.string.navdrawer_topics,
            R.string.navdrawer_settings,
            R.string.navdrawer_about,
            R.string.navdrawer_send_feedback,
            R.string.navdrawer_debug
    };

    private static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_navdrawer_stories,
            R.drawable.ic_navdrawer_topics,
            R.drawable.ic_navdrawer_settings,
            R.drawable.ic_navdrawer_about,
            R.drawable.ic_navdrawer_send_feedback,
            R.drawable.ic_navdrawer_debug
    };

    private StoriesFragment storiesFragment;
    private TopicsFragment topicsFragment;
    private SettingsFragment settingsFragment;
    private AboutFragment aboutFragment;
    private DebugFragment debugFragment;

    private Button auth;
    private ImageView avatar;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storiesFragment = StoriesFragment.newInstance();
        topicsFragment = TopicsFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();
        aboutFragment = AboutFragment.newInstance();
        debugFragment = DebugFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_container, storiesFragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navdrawer);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void myClick(View view) {
        auth = (Button) findViewById(R.id.navdrawer_action_auth);
        avatar = (ImageView) findViewById(R.id.navdrawer_account_avatar);
        username = (TextView) findViewById(R.id.navdrawer_account_username);

        auth.setVisibility(View.GONE);
        avatar.setVisibility(View.VISIBLE);
        username.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.navdrawer_stories:
                transaction.replace(R.id.main_frame_container, storiesFragment);
                break;
            case R.id.navdrawer_topics:
                transaction.replace(R.id.main_frame_container, topicsFragment);
                break;
            case R.id.navdrawer_settings:
                transaction.replace(R.id.main_frame_container, settingsFragment);
                break;
            case R.id.navdrawer_about:
                transaction.replace(R.id.main_frame_container, aboutFragment);
                break;
            case R.id.navdrawer_send_feedback:
                // TODO: Send feedback (Switch to Play Market)
                break;
            case R.id.navdrawer_debug:
                transaction.replace(R.id.main_frame_container, debugFragment);
                break;
            default:
                break;
        }

        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
