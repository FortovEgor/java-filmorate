package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private int currentId = 0;

    @GetMapping
    public final List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
        if (films.containsKey(film.getId())) {
            log.debug("Фильм " + film.getName() + " уже существует");
            throw new ValidationException("Такой фильм уже есть. Поменяйте id.");
        }
        currentId++;
        film.setId((int) currentId);  // переназначаем id фильму
        films.put((long) film.getId(), film);

        log.debug("Фильм " + film.getName() + " создан.");
        return film;
    }

    @PutMapping
    public Film put(@RequestBody Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            log.debug("Фильм " + film.getName() + " не найден.");
            throw new ValidationException("Такого фильма нет. Создайте новый.");
        }
        validateFilm(film);
        films.put((long) film.getId(), film);
        log.debug("Фильм " + film.getName() + " обновлен.");
        return film;
    }

    public final void validateFilm(Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания должна быть <= 200 символов.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года;");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом.");
        }
    }
}
