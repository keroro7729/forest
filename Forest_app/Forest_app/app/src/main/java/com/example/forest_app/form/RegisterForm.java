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
    private String name, birth, diseaseType, phoneNum, email;
    private String famName, famBirth, famPhoneNum, famEmail;
    public RegisterForm(){}
}
