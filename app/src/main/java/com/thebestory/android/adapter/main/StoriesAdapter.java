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
import com.thebestory.android.api.ApiMethods;
import com.thebestory.android.loader.AsyncLoader;
import com.thebestory.android.model.Story;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.thebestory.android.util.TimeConverter.relative;


public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {
    private final Context context;
    private List<Story> stories;

    public StoriesAdapter(Context context, ArrayList<Story> storiesList) {
        this.context = context;
        this.stories = storiesList;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new StoryViewHolder(inflater.inflate(
                R.layout.fragment_template_story, parent, false));
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
        vh.onBind(stories.get(position));
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void clear() {
        final int size = stories.size();
        notifyItemRangeRemoved(0, size);
        stories.clear();
    }


    static class StoryViewHolder extends RecyclerView.ViewHolder {
        Story story;

        SimpleDraweeView topicIcon;
        TextView topicTitle;
        TextView content;
        TextView timestamp;
        TextView likesCount;
        TextView id;
        ImageView likesView;
        TextView commentsCount;
        ImageView commentsView;

        StoryViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.card_story_id);
            topicIcon = (SimpleDraweeView) itemView.findViewById(R.id.card_story_topic_icon);
            topicTitle = (TextView) itemView.findViewById(R.id.card_story_topic_title);
            content = (TextView) itemView.findViewById(R.id.card_story_content);
            timestamp = (TextView) itemView.findViewById(R.id.card_story_timestamp);
            likesCount = (TextView) itemView.findViewById(R.id.card_story_likes_count);
            likesView = (ImageView) itemView.findViewById(R.id.card_story_likes_view);
            commentsCount = (TextView) itemView.findViewById(R.id.card_story_comments_count);
            commentsView = (ImageView) itemView.findViewById(R.id.card_story_comments_view);

            likesView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (story.isLiked()) {
                        ApiMethods.getInstance().postStoryUnlike(story.id, new AsyncLoader.OnAsyncLoaderListener() {
                            @Override
                            public void onComplete(ByteArrayOutputStream result) {
                                likesView.setImageResource(R.drawable.ic_not_liked);
                                likesCount.setText("" + story.unlike());
                            }

                            @Override
                            public void onProgressChange(int percent) {
                                return;
                            }

                            @Override
                            public void onError() {
                                return;
                            }
                        });
                    } else {
                        ApiMethods.getInstance().postStoryLike(story.id, new AsyncLoader.OnAsyncLoaderListener() {
                            @Override
                            public void onComplete(ByteArrayOutputStream result) {
                                likesView.setImageResource(R.drawable.ic_liked);
                                likesCount.setText("" + story.like());
                            }

                            @Override
                            public void onProgressChange(int percent) {
                                return;
                            }

                            @Override
                            public void onError() {
                                return;
                            }
                        });
                    }
                }
            });
        }

        void onBind(Story story) {
            this.story = story;

            id.setText(story.id);
            topicTitle.setText(story.topic.title);
            content.setText(story.content);
            timestamp.setText(relative(story.publishDate));
            likesCount.setText(Integer.toString(story.likesCount));
            commentsCount.setText(Integer.toString(story.commentsCount));
            topicIcon.setImageURI(story.topic.icon);

            if (story.isLiked()) {
                likesView.setImageResource(R.drawable.ic_liked);
            } else {
                likesView.setImageResource(R.drawable.ic_not_liked);
            }
        }
    }
}
