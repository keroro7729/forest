package com.forest.forest_server.form;


import com.forest.forest_server.Comment.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentDetail {
    private Long id;

    private Long authorId;
    private String authorName;

    private Boolean anonymous;

    private String content;

    private LocalDateTime createdAt;

    public CommentDetail(Comment comment){
        this.id = comment.getId();
        this.anonymous = comment.getAnonymous();
        if(!anonymous){
            this.authorId = comment.getAuthor().getId();
            this.authorName = comment.getAuthor().getName();
        }
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
