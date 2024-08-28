package com.forest.forest_server;

import com.forest.forest_server.QuizData.QuizDataService;
import com.forest.forest_server.User.ForestUser;
import com.forest.forest_server.User.UserService;
import com.forest.forest_server.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;
    private final QuizDataService quizDataService;
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    public ApiController(UserService userService, QuizDataService quizDataService){
        this.quizDataService = quizDataService;
        this.userService = userService;
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

    @PostMapping("/create-user")
    public ResponseEntity<AuthForm> createUser(@RequestBody RegisterForm form) {
        logger.info("Received request to create user with details: {}", form);

        try {
            ForestUser user = userService.createUser(form);
            AuthForm auth = new AuthForm(user.getId(), user.getHash());
            logger.info("User created successfully with ID: {}", user.getId());
            return ResponseEntity.ok(auth);
        } catch (Exception e) {
            logger.error("Error occurred while creating user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
