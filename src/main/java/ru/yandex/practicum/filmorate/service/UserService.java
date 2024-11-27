package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {  // добавление в друзья, удаление из друзей, вывод списка общих друзей.
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public User findUserById(Integer id) {
        if (id <= 0) {
            throw new NotValidIdException();
        }
//        if (!userStorage.getUsers().containsKey(id)) {
//            throw new NotFoundException(String.format("Фильм с запрашиваемым id=%d отсутствует. Кол-во фильмов: %s",
//                    id, userStorage.getUsers().keySet().stream().map(String::valueOf) // Преобразуем Long ключи в строки
//                            .collect(Collectors.joining(", "))));
//        }
//
//        return userStorage.getUsers().get(id);

        User possibleUser = userStorage.findUserById(id);
        if (possibleUser == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        log.debug("Получен фильм с айди {}.", id);
        return possibleUser;
    }

    public List<User> findAllFriends(int id) {
        List<User> friends = friendStorage.findAllFriends(id);
        User possibleUser = findUserById(id);  // check that user with current id exists
        return friends;
    }

    public final Collection<User> getAllUsers() {
        return userStorage.getAll();
    }

    public User createUser(User user) throws ValidationException {
        validateUser(user);
        return userStorage.create(user);
    }

    public User updateUser(User user) throws ValidationException {
        validateUser(user);

        if (userStorage.findUserById(user.getId()) == null) {
            throw new NotFoundException("Пользователь не найден!");
        }
        User newUser = userStorage.update(user);
//        if (newUser == null) {
//            throw new NotFoundException("Пользователь не найден!");
//        }
        return newUser;
    }

    public final void validateUser(User user) throws ValidationException {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getLogin().isBlank() || user.getLogin().matches(".*\\s+.*")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }

    public void addFriend(Integer user, Integer friend) {
        if (userStorage.findUserById(user) == null || userStorage.findUserById(friend) == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        checkId(user, friend);
        friendStorage.addFriend(user, friend);
        log.debug("Пользатели c id {} и {} друзья.", user, friend);
    }

    public void removeFriend(Integer user, Integer friend) {
        checkId(user, friend);
//        if (userStorage.getUserFriends(user).isEmpty()) {
//            log.debug("Список друзей пользователя c id {}  пуст.", user);
//            return;
//        }
//        if (!userStorage.findUserById(user).getFriends().contains(friend)) {
//            throw new NotFoundException(userStorage.findUserById(friend));
//        }
//        userStorage.findUserById(user).getFriends().remove(friend);
//        userStorage.findUserById(friend).getFriends().remove(user);


        if (userStorage.findUserById(user) == null || userStorage.findUserById(friend) == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        friendStorage.removeFriend(user, friend);
        log.debug("Пользатели c id {} и {} удалены из друзей друг друга.", user, friend);
    }

    public List<User> getCommonFriends(Integer user, Integer friend) {
        checkId(user, friend);
        return friendStorage.findCommonFriends(user, friend);
//        log.debug("Найдены общие друзья пользователей c id {} и {}.", user, friend);
    }

    private void checkId(Integer user, Integer friend) {
        if (user <= 0 || friend <= 0) {
            throw new NotValidIdException();
        }
    }
}
