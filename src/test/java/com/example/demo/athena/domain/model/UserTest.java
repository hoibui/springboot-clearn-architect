package com.example.demo.athena.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("Should create user with default constructor")
    void constructor_shouldCreateUser_whenDefaultConstructorUsed() {
        // When
        User user = new User();

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isNull();
        assertThat(user.getAge()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should create user with name and age constructor")
    void constructor_shouldCreateUser_whenNameAndAgeConstructorUsed() {
        // Given
        String name = "John Doe";
        int age = 25;

        // When
        User user = new User(name, age);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getAge()).isEqualTo(age);
    }

    @Test
    @DisplayName("Should create user with all args constructor")
    void constructor_shouldCreateUser_whenAllArgsConstructorUsed() {
        // Given
        Long id = 1L;
        String name = "John Doe";
        int age = 25;

        // When
        User user = new User(id, name, age);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getAge()).isEqualTo(age);
    }

    @Test
    @DisplayName("Should set and get properties correctly")
    void properties_shouldSetAndGet_whenValidValuesProvided() {
        // Given
        User user = new User();
        Long id = 1L;
        String name = "Jane Smith";
        int age = 30;

        // When
        user.setId(id);
        user.setName(name);
        user.setAge(age);

        // Then
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getAge()).isEqualTo(age);
    }

    @Test
    @DisplayName("Should handle null name")
    void properties_shouldHandleNullName_whenNullNameSet() {
        // Given
        User user = new User();

        // When
        user.setName(null);

        // Then
        assertThat(user.getName()).isNull();
    }

    @Test
    @DisplayName("Should handle negative age")
    void properties_shouldHandleNegativeAge_whenNegativeAgeSet() {
        // Given
        User user = new User();
        int negativeAge = -5;

        // When
        user.setAge(negativeAge);

        // Then
        assertThat(user.getAge()).isEqualTo(negativeAge);
    }

    @Test
    @DisplayName("Should demonstrate equals and hashCode functionality")
    void equalsAndHashCode_shouldWork_whenComparingUsers() {
        // Given
        User user1 = new User(1L, "John Doe", 25);
        User user2 = new User(1L, "John Doe", 25);
        User user3 = new User(2L, "Jane Smith", 30);

        // Then
        assertThat(user1).isEqualTo(user2);
        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    @DisplayName("Should demonstrate toString functionality")
    void toString_shouldContainAllFields_whenCalled() {
        // Given
        User user = new User(1L, "John Doe", 25);

        // When
        String result = user.toString();

        // Then
        assertThat(result).contains("id=1");
        assertThat(result).contains("name=John Doe");
        assertThat(result).contains("age=25");
    }
}