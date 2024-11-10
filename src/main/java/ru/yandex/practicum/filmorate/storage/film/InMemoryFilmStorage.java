package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int currentId = 0;

    @Override
    public Film create(Film film) throws ValidationException {
        if (films.containsKey(film.getId())) {
            log.debug("Фильм " + film.getName() + " уже существует");
            throw new ValidationException("Такой фильм уже есть. Поменяйте id.");
        }
        currentId++;
        film.setId((int) currentId);  // переназначаем id фильму
        films.put(film.getId(), film);

        log.debug("Фильм " + film.getName() + " создан.");
        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            log.debug("Фильм " + film.getName() + " не найден.");
            throw new NotFoundException("Такого фильма нет. Создайте новый.");
        }
        films.put(film.getId(), film);
        log.debug("Фильм " + film.getName() + " обновлен.");
        return film;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Film get(Integer id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Map<Integer, Film> getFilms() {  // @FIXME: unused?
        return films;
    }

    @Override
    public boolean isEmpty() {
        return films.isEmpty();
    }

    @Override
    public List<Film> getTopFilms(int count) {
        var films = new ArrayList<>(this.films.values());
        return films.stream()
                .sorted(Comparator.comparing(Film::getFilmsLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
