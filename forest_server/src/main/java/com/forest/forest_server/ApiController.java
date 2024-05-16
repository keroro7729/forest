package com.forest.forest_server;

import com.forest.forest_server.Picture.Picture;
import com.forest.forest_server.Picture.PictureService;
import com.forest.forest_server.Record.Record;
import com.forest.forest_server.Record.RecordService;
import com.forest.forest_server.User.UserService;
import com.forest.forest_server.Word.Word;
import com.forest.forest_server.Word.WordService;
import com.forest.forest_server.form.QuizForm1;
import com.forest.forest_server.form.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final PictureService pictureService;
    private final UserService userService;
    private final WordService wordService;
    private final RecordService recordService;

    @Autowired
    public ApiController(PictureService pictureService, UserService userService, WordService wordService, RecordService recordService){
        this.pictureService = pictureService;
        this.userService = userService;
        this.wordService = wordService;
        this.recordService = recordService;
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
        List<Long> optionIds = RandomSelecter.selectWord(answer, 3);

        QuizForm1 response = new QuizForm1(image, answer, options);
        return ResponseEntity.ok(response);
    }
}
