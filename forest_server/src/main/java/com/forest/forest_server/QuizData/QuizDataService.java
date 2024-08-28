package com.forest.forest_server.QuizData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizDataService {

    private final QuizDataRepository quizDataRepository;

    @Autowired
    public QuizDataService(QuizDataRepository quizDataRepository){
        this.quizDataRepository = quizDataRepository;
    }

    public List<QuizData> getQuizDatas(List<String> excepts){
        if(excepts == null || excepts.isEmpty())
            return quizDataRepository.findAll();
        else return quizDataRepository.findByTextNotIn(excepts);
    }
}
