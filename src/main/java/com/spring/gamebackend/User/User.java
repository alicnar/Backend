package com.spring.gamebackend.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    private String userName;
    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registrationDate;

    private boolean login;


}
