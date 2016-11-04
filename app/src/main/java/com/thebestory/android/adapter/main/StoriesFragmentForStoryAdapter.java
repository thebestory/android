package com.thebestory.android.adapter.main;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.thebestory.android.R;
import com.thebestory.android.models.Story;

import java.util.List;

/**
 * Created by Октай on 18.10.2016.
 */

public class StoriesFragmentForStoryAdapter extends RecyclerView.Adapter<StoriesFragmentForStoryAdapter.StoryViewHolder> {

    static class StoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView nameTopic;
        TextView numberStory;
        TextView textStory;
        TextView timeTopic;
        TextView numbersLike;
        //ImageView imageTopic;

        StoryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_story);
            nameTopic = (TextView) itemView.findViewById(R.id.name_topic);
            numberStory = (TextView) itemView.findViewById(R.id.number_story);
            textStory = (TextView) itemView.findViewById(R.id.text_story);
            timeTopic = (TextView) itemView.findViewById(R.id.time_topic);
            numbersLike = (TextView) itemView.findViewById(R.id.numbers_likes);
            //imageTopic = (ImageView) itemView.findViewById(R.id.image_topic);
        }
    }

    private List<Story> stories;

    public StoriesFragmentForStoryAdapter(List<Story> story) {
        this.stories = story;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_template_story, viewGroup, false);
        return new StoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, int i) {
        storyViewHolder.nameTopic.setText(Integer.toString(stories.get(i).topicId));
        storyViewHolder.numberStory.setText(Integer.toString(stories.get(i).id));
        storyViewHolder.textStory.setText(stories.get(i).story);
        storyViewHolder.timeTopic.setText(/*stories.get(i).*/"1000500");
        storyViewHolder.numbersLike.setText(Integer.toString(stories.get(i).likesCount));
        //storyViewHolder.imageTopic.setImageResource(stories.get(i).getImageTopic);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

}
