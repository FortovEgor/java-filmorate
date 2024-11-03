package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

public interface FilmStorage {
    // методы добавления, удаления и модификации объектов
    Film create(Film film);
    Film update(Film film);
    void remove(int id);
    Collection<Film> findAll();

    HashMap<Long, Film> getFilms();
}
