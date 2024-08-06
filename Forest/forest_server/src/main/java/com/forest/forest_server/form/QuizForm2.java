package com.forest.forest_server.form;

import com.forest.forest_server.ImageLoader;
import com.forest.forest_server.Picture.Picture;
import com.forest.forest_server.Word.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizForm2 {
    private String words;
    private List<String> imgData;
    public QuizForm2(Picture image, Word answer, List<Picture> options){
        words = ImageLoader.load(image.getSource());
        imgData = new ArrayList<>();
        imgData.add(answer.getWord());
        for(Picture w : options)
            imgData.add(w.getPicture());
        Collections.shuffle(imgData);
    }
}
