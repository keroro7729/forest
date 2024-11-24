package com.example.forest_app.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostDetailRequest {
    private AuthForm authForm;
    private PostDetail postDetail;
    public PostDetailRequest(){}
}
