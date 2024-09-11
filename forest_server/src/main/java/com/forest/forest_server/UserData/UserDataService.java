package com.forest.forest_server.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public UserData createUserData(UserData userData) {
        return userDataRepository.save(userData);
    }

    public UserData getUserDataById(Long id) {
        return userDataRepository.findById(id).orElse(null);
    }

    public List<UserData> getAllUserData() {
        return userDataRepository.findAll();
    }

    public void deleteUserData(Long id) {
        userDataRepository.deleteById(id);
    }

    public UserData updateUserData(UserData userData){
        Long id = userData.getId();
        if(id == null || id == 0)
            return null;
        return userDataRepository.save(userData);
    }
}