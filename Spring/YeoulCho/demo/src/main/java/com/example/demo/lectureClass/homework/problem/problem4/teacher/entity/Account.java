package com.example.demo.lectureClass.homework.problem.problem4.teacher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Account {
    private Long id;
    private String userEmail;
    private String userPassword;

    public Account(Long id, String email, String password) {
        this.id = id;
        this.userEmail = email;
        this.userPassword = password;
    }
}
