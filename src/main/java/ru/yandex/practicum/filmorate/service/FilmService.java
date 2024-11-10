package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {  // добавление и удаление лайка, вывод 10 наиболее популярных фильмов по количеству лайков.
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public Film createFilm(Film film) throws ValidationException {
        validateFilm(film);
        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) throws ValidationException {
        return filmStorage.update(film);
    }

    public Film getFilm(Integer id) {
        return filmStorage.get(id);
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

    public void addLike(Integer filmId, Integer userId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            throw new NotFoundException(String.format("User with id=%dno found", userId));
        }
        checkId(filmId, userId);
        findFilmById(filmId).getIdUsersWhoLikedFilm().add(userId);
        log.debug("Пользователь c id {} поставил лайк фильму с айди {}.", userId, filmId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        if (!userStorage.getUsers().containsKey(userId)) {
            throw new NotFoundException(String.format("User with id=%dno found", userId));
        }
        checkId(filmId, userId);
        if (findFilmById(filmId).getIdUsersWhoLikedFilm().isEmpty()) {
            throw new NotFoundException("Список фильмов пуст.");
        }
        findFilmById(filmId).getIdUsersWhoLikedFilm().remove(userId);
        log.debug("Пользователь c id {} удалил лайк фильму с айди {}.", userId, filmId);
    }

    public List<Film> getTopFilmsByLikes(Integer count) {
        if (filmStorage.isEmpty()) {
            throw new NotFoundException("Список фильмов пуст.");
        }
        log.debug("Топ {} фильмов успешно отобран.", count);
        return filmStorage.getTopFilms(count);
    }

    public Film findFilmById(Integer id) {
        if (id <= 0) {
            throw new NotValidIdException();
        }
        if (!filmStorage.getFilms().containsKey(id)) {
            throw new NotFoundException(String.format("Фильм с запрашиваемым id=%d отсутствует. Кол-во фильмов: %s",
                    id, filmStorage.getFilms().keySet().stream().map(String::valueOf) // Преобразуем Long ключи в строки
                            .collect(Collectors.joining(", "))));
        }
        log.debug("Получен фильм с айди {}.", id);
        return filmStorage.getFilms().get(id);
    }

    private void checkId(Integer filmId, Integer userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new NotValidIdException();
        }
    }
}
