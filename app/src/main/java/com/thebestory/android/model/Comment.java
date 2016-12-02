package com.thebestory.android.model;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02.12.2016.
 */

public final class Comment {
    public final String id;

    public final Account account;
    public final String content;
    public final int likesCount;
    public final int commentsCount;
    public final List<Comment> comments;
    public final String editedDate;
    public final String parentId;
    public final Story story;
    public final String submittedDate;

    public Comment(String id,
                   Account account,
                   String content,
                   String editedDate,
                   String parentId,
                   Story story,
                   String submittedDate,
                   int likesCount,
                   int commentsCount,
                   List<Comment> comments) {
        this.id = id;
        this.account = account;
        this.content = content;
        this.editedDate = editedDate;
        this.parentId = parentId;
        this.story = story;
        this.submittedDate = submittedDate;
        this.comments = comments;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
    }

    /**
     * Parses an comment information ({@link Comment}) from json object ({@link JsonReader}).
     *
     * @param jr json object data
     * @return {@link Comment} instance with parsed information
     * @throws IOException
     */
    public static Comment parse(JsonReader jr) throws IOException {
        String id = null, submitDate = null, content = null,  editDate = null, parentId = null;
        Story story = null;
        Account account = null;
        int likesCount = 0, commentsCount = 0;
        List<Comment> comments = null;

        jr.beginObject();

        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "author":
                    //account = Account.parse(jr); TODO: Account support
                    jr.skipValue();
                    break;
                case "content":
                    content = jr.nextString();
                    break;
                case "edit_date":
                    editDate = jr.nextString();
                    break;
                case "id":
                    id = jr.nextString();
                    break;
                case "parent_id":
                    parentId = jr.nextString();
                    break;
                case "story":
                    story = Story.parse(jr);
                    break;
                case "submit_date":
                    submitDate = jr.nextString();
                    break;
                case "likes_count":
                    likesCount = jr.nextInt();
                    break;
                case "comments_count":
                    commentsCount = jr.nextInt();
                    break;
                case "comments":
                    comments = new ArrayList<Comment>();
                    jr.beginArray();
                    while (jr.hasNext()) {
                        comments.add(Comment.parse(jr));
                    }
                    jr.endArray();
                    break;
                default:
                    jr.skipValue();
                    break;
            }
        }

        jr.endObject();

        return new Comment(id,
                account,
                content,
                editDate,
                parentId,
                story,
                submitDate,
                likesCount,
                commentsCount,
                comments
        );
    }
}
