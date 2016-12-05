/*
 * The Bestory Project
 */

package com.thebestory.android.adapter.main;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thebestory.android.R;
import com.thebestory.android.model.*;

import java.util.ArrayList;
import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder> {

    private Context context;
    private OnClickListener listener;
    private final LayoutInflater layoutInflater;
    private List<Topic> topics = new ArrayList<>();

    public TopicsAdapter(Context context, OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void addTopics(List<Topic> topics) {
        final int pos = this.topics.size() + 1;
        this.topics.addAll(topics);
        notifyItemRangeInserted(pos, topics.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TopicViewHolder.newInstance(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder topicViewHolder, int i) { //TODO: When Alex changes API = Change this methods
        topicViewHolder.title.setText(topics.get(i).title);
        topicViewHolder.description.setText(topics.get(i).description);
        topicViewHolder.storiesCount.setText(Integer.toString(topics.get(i).storiesCount));
        topicViewHolder.setOnClickListener(listener, topics.get(i));
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView description;
        //ImageView icon;
        TextView storiesCount;
        //ImageView imageTopic;
        View addView;

        TopicViewHolder(View itemView) { //TODO: When Alex changes API = Change this methods
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_topic);
            title = (TextView) itemView.findViewById(R.id.text_title);
            description = (TextView) itemView.findViewById(R.id.text_description);
            //icon = (TextView) itemView.findViewById(R.id.);
            storiesCount = (TextView) itemView.findViewById(R.id.text_stories_count);
            //imageTopic = (ImageView) itemView.findViewById(R.id.image_topic);
            addView = itemView;
        }

        void setOnClickListener(final OnClickListener listener, final Topic topic) {
            addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, topic);
                }
            });
        }


        static TopicViewHolder newInstance(LayoutInflater layoutInflater, ViewGroup parent) {
            final View view = layoutInflater.inflate(R.layout.fragment_template_topic, parent, false);
            return new TopicViewHolder(view);
        }
    }

    public interface OnClickListener {
        void onClick(View view, Topic topic);
    }

}
