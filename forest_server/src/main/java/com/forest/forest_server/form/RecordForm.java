package com.forest.forest_server.form;

import lombok.Getter;

@Getter
public class RecordForm {
    private Long userId;
    private Integer quizCat;
    private Boolean correct;
    private String quizData;
}
