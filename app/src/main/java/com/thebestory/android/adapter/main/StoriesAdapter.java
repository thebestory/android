/*
 * The Bestory Project
 */

package com.thebestory.android.adapter.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thebestory.android.R;
import com.thebestory.android.model.Story;

import java.util.ArrayList;
import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {
    private final Context context;

    private List<Story> stories = new ArrayList<>();

    public StoriesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new StoryViewHolder(inflater.inflate(
                R.layout.fragment_template_story, parent, false));
    }

    public void addStories(List<Story> stories) {
        final int pos = this.stories.size() + 1;
        this.stories.addAll(stories);
        notifyItemRangeInserted(pos, stories.size());
    }

    @Override
    public void onBindViewHolder(StoryViewHolder storyViewHolder, int position) {
        storyViewHolder.onBind(stories.get(position));
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }


    static class StoryViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView topicIcon;
        TextView topicTitle;
        TextView content;
        TextView timestamp;
        TextView likesCount;
        ImageView likeView;

        StoryViewHolder(View itemView) {
            super(itemView);

            topicIcon = (SimpleDraweeView) itemView.findViewById(R.id.card_story_topic_icon);
            topicTitle = (TextView) itemView.findViewById(R.id.card_story_topic_title);
            content = (TextView) itemView.findViewById(R.id.card_story_content);
            timestamp = (TextView) itemView.findViewById(R.id.card_story_timestamp);
            likesCount = (TextView) itemView.findViewById(R.id.card_story_likes_count);
            likeView = (ImageView) itemView.findViewById(R.id.card_story_like);

            likeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeView.setImageResource(R.drawable.ic_liked);
                }
            });
        }

        void onBind(Story story) {
            content.setText(story.content);
            timestamp.setText(story.publishDate);
            likesCount.setText(Integer.toString(story.likesCount));
        }
    }
}
