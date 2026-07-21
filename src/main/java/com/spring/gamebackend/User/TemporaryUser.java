package com.spring.gamebackend.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TemporaryUser {
    @Id
    private String userName;
    private String password;
    private int tempPassword;

    private LocalDateTime sendPasswordTime;



}
