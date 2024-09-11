package com.forest.forest_server.User;

import com.forest.forest_server.UserData.UserData;
import com.forest.forest_server.UserData.UserDataService;
import com.forest.forest_server.UserFam.UserFam;
import com.forest.forest_server.UserFam.UserFamService;
import com.forest.forest_server.form.AuthForm;
import com.forest.forest_server.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.codec.digest.DigestUtils;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService forestUserService;
    private final UserDataService userDataService;
    private final UserFamService userFamService;

    @Autowired
    public UserController(UserService forestUserService, UserDataService userDataService, UserFamService userFamService) {
        this.forestUserService = forestUserService;
        this.userDataService = userDataService;
        this.userFamService = userFamService;
    }

    @PostMapping("/register-user")
    public ResponseEntity<AuthForm> registerUser(@RequestBody RegisterForm form) {
        // 1. Create UserFam if provided
        UserFam userFam = null;
        if (form.getFamName() != null && !form.getFamName().isEmpty()) {
            userFam = new UserFam();
            userFam.setName(form.getFamName());
            userFam.setBirthDate(form.getFamBirth());
            userFam.setPhoneNumber(form.getFamPhoneNum());
            userFam.setEmail(form.getFamEmail());
            userFam = userFamService.createUserFam(userFam);
        }

        // 2. Create UserData
        UserData userData = new UserData();
        userData = userDataService.createUserData(userData);

        // 3. Create ForestUser
        ForestUser forestUser = new ForestUser();
        forestUser.setName(form.getName());
        forestUser.setBirthDate(form.getBirth());
        forestUser.setAphasiaType(form.getDiseaseType());
        forestUser.setPhoneNumber(form.getPhoneNum());
        forestUser.setEmail(form.getEmail());
        forestUser.setUserFam(userFam);  // Optional
        forestUser.setUserData(userData);  // Required
        forestUser.setHashValue(DigestUtils.sha256Hex(form.toString())); // 해시값 생성

        ForestUser savedForestUser = forestUserService.createUser(forestUser);

        // 4. Return AuthForm
        AuthForm authForm = new AuthForm();
        authForm.setId(savedForestUser.getId());
        authForm.setHash(forestUser.getHashValue());

        return ResponseEntity.ok(authForm);
    }

    public boolean authenticateUser(AuthForm form) {
        ForestUser user = forestUserService.getForestUserById(form.getId());
        if (user != null && user.getHashValue().equals(form.getHash())) {
            return true;
        }
        return false;
    }
}