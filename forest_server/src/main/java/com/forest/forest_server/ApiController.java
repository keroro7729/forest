package com.forest.forest_server;

import com.forest.forest_server.Picture.PictureService;
import com.forest.forest_server.User.UserService;
import com.forest.forest_server.Word.WordService;
import com.forest.forest_server.form.ResponseForm;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final PictureService pictureService;
    private final UserService userService;
    private final WordService wordService;

    @Autowired
    public ApiController(PictureService pictureService, UserService userService, WordService wordService){
        this.pictureService = pictureService;
        this.userService = userService;
        this.wordService = wordService;
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
    public ResponseEntity<ResponseForm> quiz1(@RequestBody UserLogForm form){

    }
}
