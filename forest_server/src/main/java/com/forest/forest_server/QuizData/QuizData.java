package com.forest.forest_server.QuizData;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "quiz_data")
@Getter
public class QuizData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, length = 5)
    private String type;

    @Column(name = "text", nullable = false, unique = true, length = 255)
    private String text;

    @Column(name = "image_path", length = 255)
    private String imagePath;
}
