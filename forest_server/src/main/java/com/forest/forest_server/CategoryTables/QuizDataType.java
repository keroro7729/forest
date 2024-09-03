package com.forest.forest_server.CategoryTables;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "quiz_data_type")
@Getter
public class QuizDataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, unique = true, length = 5)
    private String type;
}
