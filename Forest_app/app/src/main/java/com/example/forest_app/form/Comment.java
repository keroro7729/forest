package com.example.forest_app.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Comment {
    private Long id;
    private Long postId;
    private String author;
    private Boolean anonymous;
    private String content;
    private String createdAt;
}
