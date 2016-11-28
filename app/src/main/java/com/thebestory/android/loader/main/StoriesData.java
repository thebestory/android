package com.thebestory.android.loader.main;

import com.thebestory.android.model.Story;

import java.util.ArrayList;

/**
 * Created by Октай on 28.11.2016.
 */

interface StoriesData {

    ArrayList<Story> getCurrentStories();

    String getLastId();
}
