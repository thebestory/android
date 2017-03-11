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
    private List<Topic> topics;

    public TopicsAdapter(Context context, ArrayList<Topic> loadedTopic, OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.topics = loadedTopic;
    }


    public void addTopics(ArrayList<Topic> newTopics) {
        //TODO: rechange it
        notifyItemRangeRemoved(0, topics.size());
        this.topics = newTopics;
        notifyItemRangeInserted(0, topics.size());
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new TopicViewHolder(inflater.inflate(
                R.layout.card_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(TopicViewHolder vh, int i) {
        vh.onBind(topics.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        View view;
        CardView cv;

        TextView title;
        TextView description;
        SimpleDraweeView icon;

        TopicViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cv = (CardView) itemView.findViewById(R.id.card_topic);

            title = (TextView) itemView.findViewById(R.id.card_topic_title);
            description = (TextView) itemView.findViewById(R.id.card_topic_description);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.card_topic_icon);
        }

        void onBind(final Topic topic, final OnClickListener listener) {
            title.setText(topic.title);
            description.setText(topic.description);
            icon.setImageURI(topic.icon);

            view.setOnClickListener(new View.OnClickListener() {
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
