package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {  // добавление и удаление лайка, вывод 10 наиболее популярных фильмов по количеству лайков.
    private final FilmStorage filmStorage;
    private final MpaRatingStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;

    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public Film createFilm(Film film) throws ValidationException {
        validateFilm(film);
        if (mpaStorage.findMpaById(film.getMpa().getId()).isEmpty()) {
            throw new ValidationException("No such MPA rating!");
        }
        LinkedHashSet<Genre> filmGenres = film.getGenres();
        for (Genre genre : filmGenres) {
            if (genreStorage.findGenreById(genre.getId()).isEmpty()) {
                throw new ValidationException("No such genre!");
            }
        }

        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) throws ValidationException {
        if (filmStorage.get(film.getId()) == null) {
            throw new NotFoundException("No film with such id!");
        }
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
//        if (!userStorage.getUsers().containsKey(userId)) {
//            throw new NotFoundException(String.format("User with id=%dno found", userId));
//        }
        if (userStorage.findUserById(userId) == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        if (filmStorage.get(filmId) == null ) {
            throw new NotFoundException("Фильм не найден.");
        }
        likeStorage.addLike(filmId, userId);
        checkId(filmId, userId);
        likeStorage.addLike(filmId, userId);
//        findFilmById(filmId).getIdUsersWhoLikedFilm().add(userId);
//        log.debug("Пользователь c id {} поставил лайк фильму с айди {}.", userId, filmId);
    }

    public List<Genre> findAllGenres() {
        return genreStorage.findAllGenres();
    }

    public Genre findGenreById(int id) {
        return genreStorage.findGenreById(id).orElseThrow(() -> new RuntimeException("Жанр не найден."));
    }

    public void removeLike(Integer filmId, Integer userId) {
        if (userStorage.findUserById(userId) == null) {
            throw new NotFoundException(String.format("User with id=%dno found", userId));
        }
        if (filmStorage.get(filmId) == null) {
            throw new NotFoundException("Фильм не найден.");
        }
        checkId(filmId, userId);
        likeStorage.removeLike(filmId, userId);
//        if (findFilmById(filmId).getIdUsersWhoLikedFilm().isEmpty()) {
//            throw new NotFoundException("Список фильмов пуст.");
//        }
//        findFilmById(filmId).getIdUsersWhoLikedFilm().remove(userId);
//        log.debug("Пользователь c id {} удалил лайк фильму с айди {}.", userId, filmId);
    }

    public List<Film> getTopFilmsByLikes(Integer count) {
        List<Film> films = filmStorage.getTopFilms(count);
        if (films.isEmpty()) {
            throw new NotFoundException("Список фильмов пуст.");
        }
        genreStorage.findAllGenresByFilm(films);
        log.debug("Топ {} фильмов успешно отобран.", count);
        return films;
    }

    public List<MpaRating> findAllMpa() {
        return mpaStorage.findAllMpa();
    }

    public MpaRating findMpaById(int id) {
        return mpaStorage.findMpaById(id).orElseThrow(() -> new RuntimeException("Рейтинг MPA не найден."));
    }

    public Film findFilmById(Integer id) {
//
//        if (!filmStorage.getFilms().containsKey(id)) {
//            throw new NotFoundException(String.format("Фильм с запрашиваемым id=%d отсутствует. Кол-во фильмов: %s",
//                    id, filmStorage.getFilms().keySet().stream().map(String::valueOf) // Преобразуем Long ключи в строки
//                            .collect(Collectors.joining(", "))));
//        }
//
//        return filmStorage.getFilms().get(id);
        if (id <= 0) {
            throw new NotValidIdException();
        }
        Film film = filmStorage.get(id);
        if (film == null) {
            throw new NotFoundException("Фильм с запрашиваемым id отсутствует");
        }
        genreStorage.findAllGenresByFilm(List.of(film));
        log.debug("Получен фильм с айди {}.", id);
        return film;
    }

    private void checkId(Integer filmId, Integer userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new NotValidIdException();
        }
    }
}
