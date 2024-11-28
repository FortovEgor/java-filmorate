package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    // методы добавления, удаления и модификации объектов
    Film create(Film film) throws ValidationException;

    Film update(Film film) throws ValidationException;

    Film get(int id);

    List<Film> getAll();

    List<Film> getTopFilms(int count);
}
