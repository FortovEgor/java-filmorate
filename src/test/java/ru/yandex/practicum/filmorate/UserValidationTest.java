package ru.yandex.practicum.filmorate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {
    private static Validator validator;
    private User user;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
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
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", violations.iterator().next().getMessage());
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
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может быть пустым и содержать пробелы.",
                violations.iterator().next().getMessage());
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
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может быть пустым и содержать пробелы.",
                violations.iterator().next().getMessage());
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
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может быть пустым и содержать пробелы.",
                violations.iterator().next().getMessage());
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
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин не может быть пустым и содержать пробелы.",
                violations.iterator().next().getMessage());
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
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Дата рождения не может быть в будущем.",
                violations.iterator().next().getMessage());
    }
}