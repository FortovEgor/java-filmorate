package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private int currentId = 0;
    @Override
    public Film create(Film film) throws ValidationException {
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

    @Override
    public Film update(Film film) throws ValidationException {
        if (!films.containsKey((long) film.getId())) {
            log.debug("Фильм " + film.getName() + " не найден.");
            throw new NotFoundException("Такого фильма нет. Создайте новый.");
        }
        films.put((long) film.getId(), film);
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
    public Collection<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public HashMap<Long, Film> getFilms() {  // @FIXME: unused?
        return new HashMap<>();
    }

    @Override
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
