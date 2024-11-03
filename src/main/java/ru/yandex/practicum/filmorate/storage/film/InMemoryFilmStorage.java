package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    // @TODO: get rid of mocks below
    private final Map<Long, Film> films = new HashMap<>();
    private int currentId = 0;
    @Override
    public Film create(Film film) throws ValidationException {
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

    @Override
    public Film update(Film film) throws ValidationException {
        if (!films.containsKey((long) film.getId())) {
            log.debug("Фильм " + film.getName() + " не найден.");
            throw new ValidationException("Такого фильма нет. Создайте новый.");
        }
        films.put((long) film.getId(), film);
        log.debug("Фильм " + film.getName() + " обновлен.");
        return film;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Collection<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public HashMap<Long, Film> getFilms() {  // @FIXME: unused?
        return new HashMap<>();
    }
}
