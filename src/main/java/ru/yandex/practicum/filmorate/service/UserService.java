package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {  // добавление в друзья, удаление из друзей, вывод списка общих друзей.
    @Getter
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Integer user, Integer friend) {
        checkId(user, friend);
        userStorage.findUserById(user).getFriends().add(friend);
        userStorage.findUserById(friend).getFriends().add(user);
        log.debug("Пользатели c id {} и {} друзья.", user, friend);
    }

    public void removeFriend(Integer user, Integer friend) {
        checkId(user, friend);
        if (userStorage.getUserFriends(user).isEmpty()) {
            log.debug("Список друзей пользователя c id {}  пуст.", user);
            return;
        }
        if (!userStorage.findUserById(user).getFriends().contains(friend)) {
            throw new NotFoundException(userStorage.findUserById(friend));
        }
        userStorage.findUserById(user).getFriends().remove(friend);
        userStorage.findUserById(friend).getFriends().remove(user);
        log.debug("Пользатели c id {} и {} удалены из друзей друг друга.", user, friend);
    }

    public List<User> getCommonFriends(Integer user, Integer friend) {
        checkId(user, friend);
        log.debug("Найдены общие друзья пользователей c id {} и {}.", user, friend);
        return userStorage.getCommonFriends(user, friend);
    }

    private void checkId(Integer user, Integer friend) {
        if (user <= 0 || friend <= 0) {
            throw new NotValidIdException();
        }
    }
}
