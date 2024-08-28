package com.forest.forest_server;

import com.forest.forest_server.Category.CategorySecvice;
import com.forest.forest_server.Record.Record;
import com.forest.forest_server.Record.RecordService;
import com.forest.forest_server.User.UserService;
import com.forest.forest_server.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;
    private final RecordService recordService;
    private final CategorySecvice categorySecvice;

    @Autowired
    public ApiController(UserService userService, RecordService recordService,
                         CategorySecvice categorySecvice){
        this.userService = userService;
        this.recordService = recordService;
        this.categorySecvice = categorySecvice;
    }

    @GetMapping("/hello")
    public ResponseEntity<ResponseForm> hello(){
        ResponseForm response = new ResponseForm();
        response.setResult("Success");
        response.setMessage("hello! wellcome to spring-boot-server");
        response.setData(null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/get-image")
    public ResponseEntity<ResponseForm> getImage(){
        ResponseForm response = new ResponseForm();
        response.setResult("Success");
        response.setMessage("random image file from server");
        response.setData(ImageLoader.load("dog.jpg"));
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
