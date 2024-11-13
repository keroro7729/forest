package com.forest.forest_server.Controller;

import com.forest.forest_server.QuizData.QuizData;
import com.forest.forest_server.QuizData.QuizDataService;
import com.forest.forest_server.Util.ImageLoader;
import com.forest.forest_server.Util.RandomSelecter;
import com.forest.forest_server.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiController {

    private final QuizDataService quizDataService;
    private final UserController userController;
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);


    @Autowired
    public ApiController(QuizDataService quizDataService, UserController userController){
        this.quizDataService = quizDataService;
        this.userController = userController;
    }

    @GetMapping("/hello")
    public ResponseEntity<ResponseForm> hello(){
        ResponseForm response = new ResponseForm();
        response.setResult("Success");
        response.setMessage("hello! wellcome to spring-boot-server");
        response.setData(null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-test-image")
    public ResponseEntity<ResponseForm> getImage(){
        ResponseForm response = new ResponseForm();
        response.setResult("Success");
        response.setMessage("random image file from server");
        response.setData(ImageLoader.load("dog.jpg"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/find-img-by-word")
    public ResponseEntity<Text_1_Img_4_Form> findImgByWord(@RequestBody AuthForm auth){
        if(!userController.authenticateUser(auth)){
            return ResponseEntity.status(401).build(); // auth fail code
        }
        else{
            List<QuizData> quizPool = quizDataService.getAllQuizData("word", null);
            List<QuizData> selected = RandomSelecter.select(quizPool, 4);

            String text = selected.get(0).getText();
            List<String> imgDatum = new ArrayList<>();
            for(QuizData row : selected){
                imgDatum.add(ImageLoader.load(row.getImagePath()));
            }
            return ResponseEntity.ok().body(new Text_1_Img_4_Form(text, imgDatum));
        }
    }

    @PostMapping("/find-word-by-img")
    public ResponseEntity<Text_4_Img_1_Form> findWordByImg(@RequestBody AuthForm auth){
        if(!userController.authenticateUser(auth)){
            return ResponseEntity.status(401).build(); // auth fail code
        }
        else{
            List<QuizData> quizPool = quizDataService.getAllQuizData("word", null);
            List<QuizData> selected = RandomSelecter.select(quizPool, 4);

            String imgData = ImageLoader.load(selected.get(0).getImagePath());
            List<String> texts = new ArrayList<>();
            for(QuizData row : selected){
                texts.add(row.getText());
            }
            return ResponseEntity.ok().body(new Text_4_Img_1_Form(texts, imgData));
        }
    }

    @PostMapping("/find-word-by-listening")
    public ResponseEntity<Text_4_Form> findWordByListening(@RequestBody AuthForm auth){
        if(!userController.authenticateUser(auth)){
            return ResponseEntity.status(401).build(); // auth fail code
        }
        else{
            List<QuizData> quizPool = quizDataService.getAllQuizData("word", null);
            List<QuizData> selected = RandomSelecter.select(quizPool, 4);

            List<String> texts = new ArrayList<>();
            for(QuizData row : selected){
                texts.add(row.getText());
            }
            return ResponseEntity.ok().body(new Text_4_Form(texts));
        }
    }

    @PostMapping("/find-img-by-listening")
    public ResponseEntity<Text_1_Img_4_Form> findImgByListening(@RequestBody AuthForm auth){
        if(!userController.authenticateUser(auth)){
            return ResponseEntity.status(401).build(); // auth fail code
        }
        else{
            List<QuizData> quizPool = quizDataService.getAllQuizData("word", null);
            List<QuizData> selected = RandomSelecter.select(quizPool, 4);

            String text = selected.get(0).getText();
            List<String> imgDatum = new ArrayList<>();
            for(QuizData row : selected){
                imgDatum.add(ImageLoader.load(row.getImagePath()));
            }
            return ResponseEntity.ok().body(new Text_1_Img_4_Form(text, imgDatum));
        }
    }

    @PostMapping("/try-speech")
    public ResponseEntity<ResponseForm> trySpeech(@RequestBody AuthForm auth){
        if(!userController.authenticateUser(auth)){
            return ResponseEntity.status(401).build();
        }
        else{
            ResponseForm response = new ResponseForm();
            List<QuizData> quizPool = quizDataService.getAllQuizData(new ArrayList<>());
            QuizData selected = RandomSelecter.select(quizPool, 1).get(0);
            response.setMessage(selected.getText());
            if(Math.random() < 0.5){
                response.setResult("data:img");
                response.setData(ImageLoader.load(selected.getImagePath()));
            }
            else{
                response.setResult("data:null");
                response.setData(null);
            }
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping("/find-statement-by-img")
    public ResponseEntity<Text_4_Img_1_Form> findStatementByImg(@RequestBody AuthForm auth){
        if(!userController.authenticateUser(auth)){
            return ResponseEntity.status(401).build(); // auth fail code
        }
        else{
            List<QuizData> quizPool = quizDataService.getAllQuizData("state", null);
            List<QuizData> selected = RandomSelecter.select(quizPool, 4);

            String imgData = ImageLoader.load(selected.get(0).getImagePath());
            List<String> texts = new ArrayList<>();
            for(QuizData row : selected){
                texts.add(row.getText());
            }
            return ResponseEntity.ok().body(new Text_4_Img_1_Form(texts, imgData));
        }
    }

    @GetMapping("/get-hospital-list")
    public ResponseEntity<HospitalList> getHospitalList(){
        HospitalList response = new HospitalList();
        List<Hospital> items = response.getItems();
        items.add(new Hospital(37.893354394734565d, 127.75560364908274, "두루바른언어심리임상센터", "평일 9:00-18:00   033-244-0075"));
        items.add(new Hospital(37.857870757995066d, 127.72047561487184d, "김두라언어임상연구소", "전화예약 033-243-3555"));
        items.add(new Hospital(37.911330650731216d, 127.73884410019444d, "푸른솔언어심리발달센터", "평일 10:00~20:00 주말 10:00~14:00 일요일 정기휴무\n010-8402-6301"));
        return ResponseEntity.ok().body(response);
    }
}
