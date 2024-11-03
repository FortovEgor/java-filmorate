package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

public interface FilmStorage {
    // методы добавления, удаления и модификации объектов
    Film create(Film film) throws ValidationException;
    Film update(Film film) throws ValidationException;
    void remove(int id);
    Film get(Integer id);

    Collection<Film> findAll();

    HashMap<Long, Film> getFilms();
    void validateFilm(Film film) throws ValidationException;
}
