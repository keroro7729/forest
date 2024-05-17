package com.forest.forest_server;

import com.forest.forest_server.Category.CategorySecvice;
import com.forest.forest_server.Diary.Diary;
import com.forest.forest_server.Diary.DiaryService;
import com.forest.forest_server.Picture.Picture;
import com.forest.forest_server.Picture.PictureService;
import com.forest.forest_server.Record.Record;
import com.forest.forest_server.Record.RecordService;
import com.forest.forest_server.User.UserService;
import com.forest.forest_server.Word.Word;
import com.forest.forest_server.Word.WordService;
import com.forest.forest_server.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final PictureService pictureService;
    private final UserService userService;
    private final WordService wordService;
    private final RecordService recordService;
    private final CategorySecvice categorySecvice;
    private final DiaryService diaryService;

    @Autowired
    public ApiController(PictureService pictureService, UserService userService,
                         WordService wordService, RecordService recordService,
                         CategorySecvice categorySecvice, DiaryService diaryService){
        this.pictureService = pictureService;
        this.userService = userService;
        this.wordService = wordService;
        this.recordService = recordService;
        this.categorySecvice = categorySecvice;
        this.diaryService = diaryService;
    }

    @GetMapping("/hello")
    public ResponseEntity<ResponseForm> hello(){
        ResponseForm response = new ResponseForm();
        response.setResult("Success");
        response.setMessage("hello! wellcome to spring-boot-server");
        response.setData(null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quiz1")
    public ResponseEntity<QuizForm1> quiz1(@RequestParam Long userId){
        List<Record> records = recordService.getAllBy(userId);
        Long imgId = RandomSelecter.selectImg(records, 1);
        Picture image = pictureService.getById(imgId);
        Word answer = wordService.getById(image.getTag());
        List<Word> options = new ArrayList<>();
        List<Long> optionIds = RandomSelecter.selectWords(answer.getId(), 3);
        for(long id : optionIds)
            options.add(wordService.getById(id));
        QuizForm1 response = new QuizForm1(image, answer, options);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quiz2")
    public ResponseEntity<QuizForm2> quiz2(@RequestParam Long userId){
        List<Record> records = recordService.getAllBy(userId);
        Long answerImgId = RandomSelecter.selectImg(records, 1);
        Picture answerImg = pictureService.getById(answerImgId);
        Word answerWord = wordService.getById(answerImg.getTag());
        List<Long> optionIds = RandomSelecter.selectImgs(answerImgId, 3);
        List<Picture> options = new ArrayList<>();
        for(long id : optionIds)
            options.add(pictureService.getById(id));
        QuizForm2 response = new QuizForm2(answerImg, answerWord, options);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/write-diary")
    public ResponseEntity<ResponseForm> writeDiary(@RequestBody DiaryForm form){
        Diary diary = new Diary();
        diary.setWriter(form.getWriter());
        diary.setDate(TimeManager.timeStamp());
        diary.setText(form.getText());
        diaryService.addDiary(diary);
        ResponseForm response = new ResponseForm();
        response.setResult("Success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user-record")
    public ResponseEntity<ResponseForm> userRecord(@RequestBody RecordForm form){
        Record record = new Record();
        record.setUserId(form.getUserId());
        record.setCorrect(form.getCorrect());
        record.setQuizCat(form.getQuizCat());
        record.setQuizData(form.getQuizData());
        record.setTimeStamp(TimeManager.timeStamp());
        recordService.addRecord(record);
        ResponseForm response = new ResponseForm();
        response.setResult("Success");
        return ResponseEntity.ok(response);
    }
}
