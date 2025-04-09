package com.example.demo.athena.application.service;

import com.example.demo.athena.domain.model.User;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserPort userPort;

    public UserService(UserPort userPort) {
        this.userPort = userPort;
    }

    public User createUser(String name, int age) {
        return userPort.save(new User(name, age));
    }

    public List<User> getUsers() {
        return userPort.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userPort.findById(id);
    }


    public interface UserPort {
        User save(User user);
        List<User> findAll();
        Optional<User> findById(Long id);
    }
}
