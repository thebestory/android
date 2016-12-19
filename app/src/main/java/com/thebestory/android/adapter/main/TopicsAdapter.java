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

import com.facebook.drawee.view.SimpleDraweeView;
import com.thebestory.android.R;
import com.thebestory.android.model.*;

import java.util.ArrayList;
import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder> {

    private Context context;
    private OnClickListener listener;
    private final LayoutInflater layoutInflater;
    private List<Topic> topics;

    public TopicsAdapter(Context context, ArrayList<Topic> loadedTopic, OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.topics = loadedTopic;
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void addTopics() { //TODO: rechange it
        final int pos = this.topics.size() + 1;
        notifyItemRangeInserted(pos, topics.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new TopicViewHolder(inflater.inflate(
                R.layout.fragment_template_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(TopicViewHolder topicViewHolder, int i) {
        topicViewHolder.title.setText(topics.get(i).title);
        topicViewHolder.description.setText(topics.get(i).description);
        topicViewHolder.storiesCount.setText(Integer.toString(topics.get(i).storiesCount));
        topicViewHolder.imageTopic.setImageURI(topics.get(i).icon);
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
        TextView storiesCount;
        SimpleDraweeView imageTopic;
        View addView;

        TopicViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_topic);
            title = (TextView) itemView.findViewById(R.id.text_title);
            description = (TextView) itemView.findViewById(R.id.text_description);
            storiesCount = (TextView) itemView.findViewById(R.id.text_stories_count);
            imageTopic = (SimpleDraweeView) itemView.findViewById(R.id.card_topic_icon);
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
    }

    public interface OnClickListener {
        void onClick(View view, Topic topic);
    }

}
