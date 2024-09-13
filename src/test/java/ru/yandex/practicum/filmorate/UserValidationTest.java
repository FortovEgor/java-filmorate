package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {
    private UserController userController;
    private User user;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void userEmailCanNotBeEmpty() {
        user = User.builder()
                .id(1)
                .email("")
                .login("mock")
                .name("mock")
                .birthday(LocalDate.of(2003, 12, 12))
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", exception.getMessage());
    }

    @Test
    void emailMustBeCorrect() {
        user = User.builder()
                .id(1)
                .email("whattt.tututu")
                .login("mock")
                .name("mock")
                .birthday(LocalDate.of(2003, 12, 12))
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", exception.getMessage());
    }

    @Test
    void userLoginCanNotBeEmpty() {
        user = User.builder()
                .id(1)
                .email("mock@gmail.com")
                .login("")
                .name("mock")
                .birthday(LocalDate.of(2003, 12, 12))
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Логин не может быть пустым и содержать пробелы.", exception.getMessage());
    }

    @Test
    void userLoginCanNotContainSpacesInTheMiddle() {
        user = User.builder()
                .id(1)
                .email("mock@gmail.com")
                .login("mock mock2")
                .name("mock")
                .birthday(LocalDate.of(2003, 12, 12))
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Логин не может быть пустым и содержать пробелы.", exception.getMessage());
    }

    @Test
    void userLoginCanNotContainSpacesInTheLeft() {
        user = User.builder()
                .id(1)
                .email("mock@gmail.com")
                .login(" mock")
                .name("Mock")
                .birthday(LocalDate.of(2003, 12, 12))
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Логин не может быть пустым и содержать пробелы.", exception.getMessage());
    }

    @Test
    void userLoginCanNotContainSpacesInTheRight() {
        user = User.builder()
                .id(1)
                .email("mock@gmail.com")
                .login("mock ")
                .name("Mock")
                .birthday(LocalDate.of(2003, 12, 12))
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Логин не может быть пустым и содержать пробелы.", exception.getMessage());
    }

    @Test
    void userNameCanNotBeEmpty() {
        user = User.builder()
                .id(1)
                .email("mock@gmail.com")
                .login("mock")
                .name("")
                .birthday(LocalDate.of(2003, 12, 12))
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Имя не может быть пустым или быть null.", exception.getMessage());
    }

    @Test
    void userBirthdayMustBeInFuture() {
        user = User.builder()
                .id(1)
                .email("mock@gmail.com")
                .login("mock")
                .name("Mock")
                .birthday(LocalDate.MAX)
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> userController.validateUser(user));
        assertEquals("Дата рождения не может быть в будущем.", exception.getMessage());
    }
}