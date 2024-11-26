package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidationTest {
    private FilmController filmController;
    private Film film;

    @BeforeEach
    void setUp() {
        FilmStorage filmStorage = new InMemoryFilmStorage();
        UserStorage userStorage = new InMemoryUserStorage();
        FilmService filmService = new FilmService(filmStorage, userStorage);
        filmController = new FilmController(filmService);
    }

    @Test
    void filmNameCanNotBeEmpty() {
        film = Film.builder()
                .id(1)
                .name("")
                .description("mock")
                .releaseDate(LocalDate.of(2003, 3, 3))
                .duration(101)
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        assertEquals("Название фильма не может быть пустым.", exception.getMessage());
    }

    @Test
    void filmDescriptionCanNotBeLongerThan200Symbols() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 201; ++i) {
            stringBuilder.append("a");
        }
        final String text = stringBuilder.toString();

        film = Film.builder()
                .id(2)
                .name("mock")
                .description(text)
                .releaseDate(LocalDate.of(2003, 3, 3))
                .duration(101)
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        assertEquals("Максимальная длина описания должна быть <= 200 символов.", exception.getMessage());
    }

    @Test
    void filmReleaseDateCanNotBEEarlierThan28Dec1895() {
        film = Film.builder()
                .id(1)
                .name("mock")
                .description("mock")
                .releaseDate(LocalDate.of(1775, 3, 3))
                .duration(101)
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        assertEquals("Дата релиза должна быть не раньше 28 декабря 1895 года;", exception.getMessage());
    }

    @Test
    void filmDurationMustBePoaitiveNumber() {
        film = Film.builder()
                .id(1)
                .name("mock")
                .description("mock")
                .releaseDate(LocalDate.of(2003, 3, 3))
                .duration(0)
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> filmController.validateFilm(film));
        assertEquals("Продолжительность фильма должна быть положительным числом.", exception.getMessage());
    }
}