package com.forest.forest_server;

import com.forest.forest_server.QuizData.QuizData;
import com.forest.forest_server.QuizData.QuizDataService;
import com.forest.forest_server.User.ForestUser;
import com.forest.forest_server.User.UserController;
import com.forest.forest_server.User.UserService;
import com.forest.forest_server.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

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
        if(userController.authenticateUser(auth)){
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
        if(userController.authenticateUser(auth)){
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
        if(userController.authenticateUser(auth)){
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
        if(userController.authenticateUser(auth)){
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
        if(userController.authenticateUser(auth)){
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
        if(userController.authenticateUser(auth)){
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
}
