package com.example.forest_app.form;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RegisterForm {
    private String birthdate;
    private String sex;
    private String aphasiaType;
    public RegisterForm(){}
}
