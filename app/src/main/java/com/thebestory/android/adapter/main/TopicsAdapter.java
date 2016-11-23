/*
 * The Bestory Project
 */

package com.thebestory.android.adapter.main;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thebestory.android.R;
import com.thebestory.android.model.*;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder> {

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView description;
        //ImageView icon;
        TextView storiesCount;
        //ImageView imageTopic;

        TopicViewHolder(View itemView) { //TODO: When Alex changes API = Change this methods
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_topic);
            title = (TextView) itemView.findViewById(R.id.text_title);
            description = (TextView) itemView.findViewById(R.id.text_description);
            //icon = (TextView) itemView.findViewById(R.id.);
            storiesCount = (TextView) itemView.findViewById(R.id.text_stories_count);
            //imageTopic = (ImageView) itemView.findViewById(R.id.image_topic);
        }
    }

    private List<Topic> topics;

    public TopicsAdapter(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_template_topic, viewGroup, false);
        return new TopicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder storyViewHolder, int i) { //TODO: When Alex changes API = Change this methods
        storyViewHolder.title.setText(topics.get(i).title);
        storyViewHolder.description.setText(topics.get(i).description);
        storyViewHolder.storiesCount.setText(Integer.toString(topics.get(i).storiesCount));
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

}
