package com.forest.forest_server.form;

import com.forest.forest_server.ImageLoader;
import com.forest.forest_server.Picture.Picture;
import com.forest.forest_server.Word.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizForm1 {
    // 그림보고 단어 맞추기
    private String imgData;
    private List<String> words;
    private int answerIdx;
    public QuizForm1(Picture image, Word answer, List<Word> options){
        imgData = ImageLoader.load(image.getSource());
        words = new ArrayList<>();
        words.add(answer.getWord());
        for(Word w : options)
            words.add(w.getWord());
        Collections.shuffle(words);
        answerIdx = words.indexOf(answer);
    }
}
