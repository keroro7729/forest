package com.example.forest_app.form;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDetail {
    private Long id;

    private Long authorId;
    private String authorName;

    private Boolean anonymous;

    private String content;

    private LocalDateTime createdAt;
}
