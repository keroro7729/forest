package com.forest.forest_server.Word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordService(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }

    public void addWord(String word_value, String discription, Long categoryId){
        Word word = new Word();
        word.setWord(word_value);
        word.setDiscription(discription);
        word.setCategoryId(categoryId);
        wordRepository.save(word);
    }

    public Word getById(Long id){
        return wordRepository.findById(id).orElse(null);
    }
}
