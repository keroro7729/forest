package com.example.forest_app.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Post {
    private Long id;
    private String author;
    private Boolean anonymous;
    private String title;
    private String content;
    private int views;
    private int likes;
    private String createdAt;
    private String updatedAt;
}
