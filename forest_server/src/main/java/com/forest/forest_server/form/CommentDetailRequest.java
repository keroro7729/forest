package com.forest.forest_server.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor
public class CommentDetailRequest {
    AuthForm authForm;
    CommentDetail commentDetail;
    public CommentDetailRequest(){}
}
