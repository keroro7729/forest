package com.forest.forest_server.QuizData;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizDataRepository extends JpaRepository<QuizData, Long> {
    Optional<QuizData> findByText(String text);

    List<QuizData> findByTextNotIn(List<String> text);
}
