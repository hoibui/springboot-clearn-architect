package com.example.demo.athena.infrastructure.persistence;

import com.example.demo.athena.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {}
