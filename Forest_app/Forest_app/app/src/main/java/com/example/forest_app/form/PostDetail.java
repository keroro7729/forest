package com.example.forest_app.form;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
}

