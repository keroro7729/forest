package com.forest.forest_server.UserData;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_data")
@Getter @Setter
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_data_id")
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "level", nullable = false)
    private int level;

    public UserData() {
        this.createdAt = LocalDateTime.now();
        this.level = 1; // 기본 레벨
    }
}