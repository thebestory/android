package com.thebestory.android.fragment.main;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.thebestory.android.R;
import com.thebestory.android.activity.MainActivity;
import com.thebestory.android.adapter.main.TopicsFragmentForTopicAdapter;
import com.thebestory.android.fragment.main.stories.NewStoryFragment;
import com.thebestory.android.models.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicsFragment extends Fragment { //TODO: When we create RecentTabFragment -> Do it!
    private View view;
    private MainActivity activity;


    private Toolbar toolbar;
    private RecyclerView rv;
    private List<Topic> topics;

    private OnFragmentInteractionListener mListener;

    private Fragment newStoryFragment;

    public TopicsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopicsFragment.
     */
    public static TopicsFragment newInstance() {
        return new TopicsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        newStoryFragment = NewStoryFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_topics, container, false);
        activity = (MainActivity) getActivity();
        toolbar = (Toolbar) view.findViewById(R.id.main_topics_toolbar);
        rv = (RecyclerView) view.findViewById(R.id.rv_topics);

        toolbar.setTitle(R.string.navdrawer_main_topics);
        activity.setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = activity.getDrawerLayout();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar, R.string.navdrawer_open, R.string.navdrawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);

        //debugInitializeData();
        initializeAdapter();

        return view;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_stories, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.main_stories_toolbar_action_new:
                transaction.replace(R.id.main_frame_layout, newStoryFragment);
                break;
            case R.id.main_stories_toolbar_action_search:
                // TODO: Stories search
                break;
        }

        transaction.addToBackStack(null).commit();
        return super.onOptionsItemSelected(item);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*private void debugInitializeData() {
        topics = new ArrayList<>();
        topics.add(new Topic(1, "Funny", "Very impressive stories that happened in good moments life", ".png", 5));
        topics.add(new Topic(2, "Sad", "Very impressive stories that happened in bad moments life", ".png", 6));
        topics.add(new Topic(2, "Sad", "Very impressive stories that happened in bad moments life", ".png", 6));
        topics.add(new Topic(2, "Sad", "Very impressive stories that happened in bad moments life", ".png", 6));
    }*/

    private void initializeAdapter() {
        TopicsFragmentForTopicAdapter adapter = new TopicsFragmentForTopicAdapter(topics);
        rv.setAdapter(adapter);
    }
}
