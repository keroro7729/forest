package com.forest.forest_server.UserFam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFamService {

    private final UserFamRepository userFamRepository;

    @Autowired
    public UserFamService(UserFamRepository userFamRepository) {
        this.userFamRepository = userFamRepository;
    }

    public UserFam createUserFam(UserFam userFam) {
        return userFamRepository.save(userFam);
    }

    public UserFam getUserFamById(Long id) {
        return userFamRepository.findById(id).orElse(null);
    }

    public List<UserFam> getAllUserFam() {
        return userFamRepository.findAll();
    }

    public void deleteUserFam(Long id) {
        userFamRepository.deleteById(id);
    }
}