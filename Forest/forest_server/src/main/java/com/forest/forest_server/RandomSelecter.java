package com.forest.forest_server;

import com.forest.forest_server.Record.Record;
import com.forest.forest_server.Word.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomSelecter {
    private final static List<Long> IMG_LIST = getNUM_SET();
    private static List<Long> WORD_LIST = null;
    private static List<Long> getNUM_SET(){
        int n = ImageLoader.countImages();
        return oneToNList(n);
    }
    public static void setWordList(int n){
        if(WORD_LIST == null)
            WORD_LIST = oneToNList(n);
    }
    private static List<Long> oneToNList(int n){
        List<Long> result = new ArrayList<>();
        for(int i=1; i<=n; i++) result.add((long)i);
        return result;
    }

    public static Long selectImg(List<Record> records, int quizCat){
        List<Long> selectList = new ArrayList<>(IMG_LIST);
        for(Record r : records){
            if(r.getQuizCat() == quizCat)
                selectList.remove(r.getImgId());
        }
        Collections.shuffle(selectList);
        return selectList.get(0);
    }

    public static List<Long> selectWord(Word answer, int count){
        List<Long> selectList = new ArrayList<>(WORD_LIST);
        selectList.remove(answer);
        Collections.shuffle(selectList);
        List<Long> result = new ArrayList<>();
        for(int i=0; i<count; i++) result.add(selectList.get(i));
        return result;
    }
}
