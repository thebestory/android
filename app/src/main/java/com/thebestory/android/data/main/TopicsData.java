package com.thebestory.android.data.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.thebestory.android.model.Topic;

import java.util.ArrayList;

/**
 * Created by Октай on 04.12.2016.
 */

public class TopicsData extends Fragment {
    public static final String TAG = TopicsData.class.getCanonicalName();
    private ArrayList<Topic> currentTopics = new ArrayList<>();

    public TopicsData() {
        this.setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ArrayList<Topic> getCurrentTopics() {
        return currentTopics;
    }

}
