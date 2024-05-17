package com.forest.forest_server.Diary;

import org.springframework.stereotype.Service;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    public DiaryService(DiaryRepository diaryRepository){
        this.diaryRepository = diaryRepository;
    }

    public void addDiary(Long userId, String date, String text){
        Diary diary = new Diary();
        diary.setWriter(userId);
        diary.setDate(date);
        diary.setText(text);
        diaryRepository.save(diary);
    }

    public void addDiary(Diary diary){
        diaryRepository.save(diary);
    }

    public Diary getById(Long id){
        return diaryRepository.findById(id).orElse(null);
    }
}
