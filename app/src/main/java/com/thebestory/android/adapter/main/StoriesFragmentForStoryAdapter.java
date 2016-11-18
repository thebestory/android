package com.thebestory.android.adapter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.thebestory.android.R;
import com.thebestory.android.models.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Октай on 18.10.2016.
 */

public class StoriesFragmentForStoryAdapter extends RecyclerView.Adapter<StoriesFragmentForStoryAdapter.StoryViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;

    private List<Story> stories = new ArrayList<>();

    public StoriesFragmentForStoryAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return StoryViewHolder.newInstance(layoutInflater, parent);
    }

    public void addStories(List<Story> stories) {
        final int pos = this.stories.size() + 1;
        this.stories.addAll(stories);
        notifyItemRangeInserted(pos, stories.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, int position) { //TODO: When Alex changes API = Change this methods
        final Story currentStory = stories.get(position);
        storyViewHolder.nameTopic.setText(Integer.toString(currentStory.topicId));
        storyViewHolder.numberStory.setText(Integer.toString(currentStory.id));
        storyViewHolder.textStory.setText(currentStory.story);
        storyViewHolder.timeTopic.setText(/*stories.get(i).*/"1000500");
        storyViewHolder.numbersLike.setText(Integer.toString(currentStory.likesCount));
        //storyViewHolder.imageTopic.setImageResource(currentStory.getImageTopic);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }


    static class StoryViewHolder extends RecyclerView.ViewHolder { //TODO: When Alex changes API = Change this methods
        TextView nameTopic;
        TextView numberStory;
        TextView textStory;
        TextView timeTopic;
        TextView numbersLike;
        //ImageView imageTopic;

        StoryViewHolder(View itemView) {
            super(itemView);
            nameTopic = (TextView) itemView.findViewById(R.id.name_topic);
            numberStory = (TextView) itemView.findViewById(R.id.number_story);
            textStory = (TextView) itemView.findViewById(R.id.text_story);
            timeTopic = (TextView) itemView.findViewById(R.id.time_topic);
            numbersLike = (TextView) itemView.findViewById(R.id.numbers_likes);
            //imageTopic = (ImageView) itemView.findViewById(R.id.image_topic);
        }

        static StoryViewHolder newInstance(LayoutInflater layoutInflater, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.fragment_template_story, parent, false);
            return new StoryViewHolder(view);
        }
    }

}
