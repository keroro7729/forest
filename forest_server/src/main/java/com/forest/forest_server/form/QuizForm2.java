package com.forest.forest_server.form;

import com.forest.forest_server.ImageLoader;
import com.forest.forest_server.Picture.Picture;
import com.forest.forest_server.RandomSelecter;
import com.forest.forest_server.Word.Word;

import java.util.ArrayList;
import java.util.List;

public class QuizForm2 {
    private String word;
    private List<String> imgDatas;
    private int answerIdx;

    public QuizForm2(Picture answerImg, Word answerWord, List<Picture> options){
        word = answerWord.getWord();
        answerIdx = RandomSelecter.getRandNum(options.size());
        imgDatas = new ArrayList<>();
        for(Picture o : options)
            imgDatas.add(ImageLoader.load(o.getSource()));
        imgDatas.add(answerIdx, ImageLoader.load(answerImg.getSource()));
    }
}
