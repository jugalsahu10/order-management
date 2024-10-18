package com.docsehr.flowerhub.service;

import com.docsehr.flowerhub.model.mysql.User;
import com.docsehr.flowerhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void validateUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User addUser(User user) {
        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
        if(dbUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
        if(!dbUser.isPresent()) {
            throw new RuntimeException("User don't exist");
        }
        dbUser.get().setName(user.getName());
        dbUser.get().setAddress(user.getAddress());
        return userRepository.save(user);
    }
}

