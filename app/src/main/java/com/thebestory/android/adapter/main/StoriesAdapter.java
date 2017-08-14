/*
 * The Bestory Project
 */

package com.thebestory.android.adapter.main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thebestory.android.R;
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.loader.AsyncLoader;
import com.thebestory.android.model.Story;
import com.thebestory.android.util.BankStoriesLocation;
import com.thebestory.android.util.CacheStories;
import com.thebestory.android.util.StoriesArray;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.thebestory.android.util.TimeConverter.relative;


public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {
    private final Context context;
    private StoriesArray stories;

    public StoriesAdapter(Context context, StoriesArray storiesList) {
        this.context = context;
        this.stories = storiesList;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new StoryViewHolder(inflater.inflate(
                R.layout.card_story, parent, false));
    }

    public void addLastStories(int sizeStories) {
        final int pos = this.stories.size() - sizeStories;
        notifyItemRangeInserted(pos, sizeStories);
    }

    public void addFirstStories(int sizeStories) {
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(StoryViewHolder vh, int position) {
        vh.onBind(stories.getStoryAt(position));
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void clear() {
        notifyItemRangeRemoved(0, stories.size());
        stories.clear();
    }


    static class StoryViewHolder extends RecyclerView.ViewHolder {
        Story story;

        SimpleDraweeView topicIcon;
        TextView topicTitle;
        TextView id;
        TextView content;
        TextView timestamp;
        LinearLayout likesView;
        TextView likesCount;
        ImageView likesIcon;
        LinearLayout commentsView;
        TextView commentsCount;
        ImageView commentsIcon;

        boolean isBookmarked;

        StoryViewHolder(View itemView) {
            super(itemView);

            topicIcon = (SimpleDraweeView) itemView.findViewById(R.id.card_story_topic_icon);
            topicTitle = (TextView) itemView.findViewById(R.id.card_story_topic_title);
            id = (TextView) itemView.findViewById(R.id.card_story_id);
            content = (TextView) itemView.findViewById(R.id.card_story_content);
            timestamp = (TextView) itemView.findViewById(R.id.card_story_timestamp);
            likesView = (LinearLayout) itemView.findViewById(R.id.card_story_likes_view);
            likesCount = (TextView) itemView.findViewById(R.id.card_story_likes_count);
            likesIcon = (ImageView) itemView.findViewById(R.id.card_story_likes_icon);
            commentsView = (LinearLayout) itemView.findViewById(R.id.card_story_comments_view);
            commentsCount = (TextView) itemView.findViewById(R.id.card_story_comments_count);
            commentsIcon = (ImageView) itemView.findViewById(R.id.card_story_comments_icon);

            likesView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (story.isLiked()) {
                        ApiMethods.getInstance().postStoryUnlike(story.id, new AsyncLoader.OnAsyncLoaderListener() {
                            @Override
                            public void onComplete(ByteArrayOutputStream result) {
                                likesIcon.setImageResource(R.drawable.ic_not_liked);
                                likesCount.setText(Integer.toString(story.unlike()));
                            }

                            @Override
                            public void onProgressChange(int percent) {
                            }

                            @Override
                            public void onError() {
                            }
                        });
                    } else {
                        ApiMethods.getInstance().postStoryLike(story.id, new AsyncLoader.OnAsyncLoaderListener() {
                            @Override
                            public void onComplete(ByteArrayOutputStream result) {
                                likesIcon.setImageResource(R.drawable.ic_liked);
                                likesCount.setText(Integer.toString(story.like()));
                            }

                            @Override
                            public void onProgressChange(int percent) {
                            }

                            @Override
                            public void onError() {
                            }
                        });
                    }
                }
            });

            id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CacheStories.getInstance().isBookmarked(story.id)) {
                        BankStoriesLocation.getInstance().getBookmarkedStoriesArray().removeStory(story.id);
                        id.setTextColor(ContextCompat.getColor(id.getContext(), R.color.colorIdNotBookmarked));
                    } else {
                        BankStoriesLocation.getInstance().getBookmarkedStoriesArray().addStoryAtHead(story);
                        id.setTextColor(ContextCompat.getColor(id.getContext(), R.color.colorIdBookmarked));
                    }
                }
            });


        }

        void onBind(Story story) {
            this.story = story;
            topicIcon.setImageURI(story.topic.icon);
            topicTitle.setText(story.topic.title);
            id.setText("#" + story.id);
            id.setTextColor(CacheStories.getInstance().isBookmarked(story.id) ?
                    ContextCompat.getColor(id.getContext(), R.color.colorIdBookmarked)
                    : ContextCompat.getColor(id.getContext(), R.color.colorIdNotBookmarked));
            content.setText(story.content);
            timestamp.setText(relative(story.publishDate));
            likesCount.setText(Integer.toString(story.getLikesCount()));
            commentsCount.setText(Integer.toString(story.commentsCount));

            if (story.isLiked()) {
                likesIcon.setImageResource(R.drawable.ic_liked);
            } else {
                likesIcon.setImageResource(R.drawable.ic_not_liked);
            }

        }
    }
}
