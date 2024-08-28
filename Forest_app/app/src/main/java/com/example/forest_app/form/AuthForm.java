package com.example.forest_app.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AuthForm {
    private Long id;
    private String hash;
    public AuthForm(){}
}