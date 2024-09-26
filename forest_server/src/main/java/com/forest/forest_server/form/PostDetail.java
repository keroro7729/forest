package com.forest.forest_server.form;

import com.forest.forest_server.Comment.Comment;
import com.forest.forest_server.Post.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostDetail {
    private Long id;

    private Long authorId;
    private String authorName;

    private Boolean anonymous;

    private String title;

    private String content;

    private LocalDateTime updatedAt;

    private int views;

    private int likes;

    private List<CommentDetail> comments;

    public PostDetail(){}
    public PostDetail(Post post){
        this.id = post.getId();
        this.anonymous = post.getAnonymous();
        if(!anonymous){
            this.authorId = post.getAuthor().getId();
            this.authorName = post.getAuthor().getName();
        }
        this.title = post.getTitle();
        this.content = post.getContent();
        this.updatedAt = post.getUpdatedAt();
        this.views = post.getViews();
        this.likes = post.getLikes();
        this.comments = new ArrayList<>();
        for(Comment c : post.getComments())
            this.comments.add(new CommentDetail(c));
    }
}
