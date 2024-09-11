package com.forest.forest_server.User;

import com.forest.forest_server.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ForestUser createUser(ForestUser forestUser) {
        return userRepository.save(forestUser);
    }

    public ForestUser getForestUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<ForestUser> getAllForestUsers() {
        return userRepository.findAll();
    }

    public void deleteForestUser(Long id) {
        userRepository.deleteById(id);
    }

    public ForestUser updateForestUser(Long id, ForestUser updatedUser) {
        return userRepository.findById(id).map(forestUser -> {
            forestUser.setName(updatedUser.getName());
            forestUser.setBirthDate(updatedUser.getBirthDate());
            forestUser.setAphasiaType(updatedUser.getAphasiaType());
            forestUser.setPhoneNumber(updatedUser.getPhoneNumber());
            forestUser.setEmail(updatedUser.getEmail());
            forestUser.setUserFam(updatedUser.getUserFam());
            forestUser.setUserData(updatedUser.getUserData());
            forestUser.setHashValue(updatedUser.getHashValue());
            return userRepository.save(forestUser);
        }).orElse(null);
    }
}
