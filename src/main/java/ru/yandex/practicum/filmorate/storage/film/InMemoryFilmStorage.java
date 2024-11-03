package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

public class InMemoryFilmStorage implements FilmStorage {
    // @TODO: get rid of mocks below
    @Override
    public Film create(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Collection<Film> findAll() {
        return null;
    }

    @Override
    public HashMap<Long, Film> getFilms() {
        return new HashMap<>();
    }
}
