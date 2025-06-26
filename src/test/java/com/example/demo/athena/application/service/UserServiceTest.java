package com.example.demo.athena.application.service;

import com.example.demo.athena.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserService.UserPort userPort;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userPort);
    }

    @Test
    @DisplayName("Should create user successfully when valid data is provided")
    void createUser_shouldReturnUser_whenValidDataProvided() {
        // Given
        String name = "John Doe";
        int age = 25;
        User expectedUser = new User(1L, name, age);
        
        when(userPort.save(any(User.class))).thenReturn(expectedUser);

        // When
        User result = userService.createUser(name, age);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getAge()).isEqualTo(age);
        assertThat(result.getId()).isEqualTo(1L);
        
        verify(userPort, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should return all users when getUsers is called")
    void getUsers_shouldReturnAllUsers_whenCalled() {
        // Given
        List<User> expectedUsers = Arrays.asList(
            new User(1L, "John Doe", 25),
            new User(2L, "Jane Smith", 30)
        );
        
        when(userPort.findAll()).thenReturn(expectedUsers);

        // When
        List<User> result = userService.getUsers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(expectedUsers);
        
        verify(userPort, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return user when valid ID exists")
    void getUserById_shouldReturnUser_whenValidIdExists() {
        // Given
        Long userId = 1L;
        User expectedUser = new User(userId, "John Doe", 25);
        
        when(userPort.findById(userId)).thenReturn(Optional.of(expectedUser));

        // When
        Optional<User> result = userService.getUserById(userId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedUser);
        
        verify(userPort, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should return empty Optional when user ID does not exist")
    void getUserById_shouldReturnEmpty_whenUserIdDoesNotExist() {
        // Given
        Long userId = 999L;
        
        when(userPort.findById(userId)).thenReturn(Optional.empty());

        // When
        Optional<User> result = userService.getUserById(userId);

        // Then
        assertThat(result).isEmpty();
        
        verify(userPort, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should handle null name gracefully")
    void createUser_shouldHandleNullName_whenNullNameProvided() {
        // Given
        String name = null;
        int age = 25;
        User expectedUser = new User(1L, name, age);
        
        when(userPort.save(any(User.class))).thenReturn(expectedUser);

        // When
        User result = userService.createUser(name, age);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isNull();
        assertThat(result.getAge()).isEqualTo(age);
        
        verify(userPort, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should handle negative age")
    void createUser_shouldHandleNegativeAge_whenNegativeAgeProvided() {
        // Given
        String name = "John Doe";
        int age = -5;
        User expectedUser = new User(1L, name, age);
        
        when(userPort.save(any(User.class))).thenReturn(expectedUser);

        // When
        User result = userService.createUser(name, age);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getAge()).isEqualTo(age);
        
        verify(userPort, times(1)).save(any(User.class));
    }
}