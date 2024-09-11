package com.example.forest_app.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attachment {
    private Long id;
    private Long postId;
    private String fileUrl;
}
