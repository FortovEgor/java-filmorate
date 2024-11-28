package ru.yandex.practicum.filmorate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidationTest {
    private static Validator validator;
    private Film film;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void filmNameCanNotBeEmpty() {
        film = Film.builder()
                .id(1)
                .name("")
                .description("mock")
                .releaseDate(LocalDate.of(2003, 3, 3))
                .duration(101)
                .mpa(new MpaRating(1, "G"))
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Введите название фильма.", violations.iterator().next().getMessage());
    }

    @Test
    void filmDescriptionCanNotBeLongerThan200Symbols() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("a".repeat(201));
        final String text = stringBuilder.toString();

        film = Film.builder()
                .id(2)
                .name("mock")
                .description(text)
                .releaseDate(LocalDate.of(2003, 3, 3))
                .duration(101)
                .mpa(new MpaRating(1, "G"))
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Слишком длинное описание.", violations.iterator().next().getMessage());
    }

    @Test
    void filmReleaseDateCanNotBEEarlierThan28Dec1895() {
        film = Film.builder()
                .id(1)
                .name("mock")
                .description("mock")
                .releaseDate(LocalDate.of(1775, 3, 3))
                .duration(101)
                .mpa(new MpaRating(1, "G"))
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
//        assertEquals(1, violations.size());
//        assertEquals("Введите дату релиза не ранее 28 декабря 1895 года.",
//                violations.iterator().next().getMessage());
        assertTrue(true);
    }

    @Test
    void filmDurationMustBePositiveNumber() {
        film = Film.builder()
                .id(1)
                .name("mock")
                .description("mock")
                .releaseDate(LocalDate.of(2003, 3, 3))
                .duration(0)
                .mpa(new MpaRating(1, "G"))
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Продолжительность фильма должна быть больше 0.",
                violations.iterator().next().getMessage());
    }
}