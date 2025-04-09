package com.example.demo.athena.infrastructure.persistence;

import java.util.Optional;
import com.example.demo.athena.application.service.*;
import com.example.demo.athena.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpringUserRepository implements UserService.UserPort {
    private final UserRepositoryJpa userRepositoryJpa;

    public SpringUserRepository(UserRepositoryJpa userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public User save(User user) {
        return userRepositoryJpa.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepositoryJpa.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepositoryJpa.findById(id);
    }
}
