package com.forest.forest_server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addUser(){
        userRepository.save(new ForestUser());
    }

    public ForestUser getById(Long id){
        return userRepository.findById(id).orElse(null);
    }

}
