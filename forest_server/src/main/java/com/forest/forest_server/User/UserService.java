package com.forest.forest_server.User;

import com.forest.forest_server.form.AuthForm;
import com.forest.forest_server.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public ForestUser createUser(RegisterForm form) {
        ForestUser user = new ForestUser();
        user.setBirthdate(form.getBirthdate());
        user.setSex(form.getSex());
        user.setAphasiaType(form.getAphasiaType());

        return userRepository.save(user);
    }

    public ForestUser authenticateUser(AuthForm form) {
        ForestUser user = userRepository.findById(form.getId())
                .orElse(null);

        if (user == null || !user.getHash().equals(form.getHash())) {
            return null;
        }

        return user;
    }
}
