package com.forest.forest_server.QuizData;

import com.forest.forest_server.CategoryTables.QuizDataType;
import com.forest.forest_server.CategoryTables.QuizDataTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizDataService {

    private final QuizDataRepository quizDataRepository;

    @Autowired
    public QuizDataService(QuizDataRepository quizDataRepository, QuizDataTypeRepository typeRepository){
        this.quizDataRepository = quizDataRepository;
    }

    public List<QuizData> getAllQuizData(List<String> excepts){
        if(excepts == null || excepts.isEmpty())
            return quizDataRepository.findAll();
        else return quizDataRepository.findByTextNotIn(excepts);
    }

    public List<QuizData> getAllQuizData(String type, List<String> excepts) {
        if(excepts == null || excepts.isEmpty())
            return quizDataRepository.findAllByType(type);
        else return quizDataRepository.findByTypeAndTextNotIn(type, excepts);
    }

    public Optional<QuizData> getQuizDataByText(String text) {
        return quizDataRepository.findByText(text);
    }

    public List<QuizData> getAllQuizData(String type) {
        return quizDataRepository.findAllByType(type);
    }
}
