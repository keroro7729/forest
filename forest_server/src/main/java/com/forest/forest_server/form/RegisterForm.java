package com.forest.forest_server.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RegisterForm {
    private String name, birth, diseaseType, phoneNum, email;
    private String famName, famBirth, famPhoneNum, famEmail;
    public RegisterForm(){}
}
