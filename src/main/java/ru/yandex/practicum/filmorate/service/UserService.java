package ru.yandex.practicum.filmorate.service;

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
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Integer user, Integer friend) {
        checkId(user, friend);
        findUserById(user).getFriends().add(friend);
        findUserById(friend).getFriends().add(user);
        log.debug("Пользатели c id {} и {} друзья.", user, friend);
    }

    public void removeFriend(Integer user, Integer friend) {
        checkId(user, friend);
        if (getUserFriends(user).isEmpty()) {
            log.debug("Список друзей пользователя c id {}  пуст.", user);
            return;
        }
        if (!findUserById(user).getFriends().contains(friend)) {
            throw new NotFoundException(findUserById(friend));
        }
        findUserById(user).getFriends().remove(friend);
        findUserById(friend).getFriends().remove(user);
        log.debug("Пользатели c id {} и {} удалены из друзей друг друга.", user, friend);
    }

    public List<User> getCommonFriends(Integer user, Integer friend) {
        checkId(user, friend);
        log.debug("Найдены общие друзья пользователей c id {} и {}.", user, friend);
        return getUserFriends(user).stream()
                .filter(getUserFriends(friend)::contains)
                .collect(Collectors.toList());
    }

    public List<User> getUserFriends(Integer id) {
        if (id <= 0) {
            throw new NotValidIdException();
        }
        Set<Integer> users = findUserById(id).getFriends();
        List<User> usersFriends = new ArrayList<>();

        if (!users.isEmpty()) {
            for (Integer integer:users) {
                usersFriends.add(findUserById(integer));
            }
        }
        log.debug("Составлен список друзей пользователя c id {}.", id);
        return usersFriends;
    }

    public User findUserById(Integer id) {
        if (id <= 0) {
            throw new NotValidIdException();
        }
        if (!userStorage.getUsers().containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь с запрашиваемым id=%d не зарегестрирован." +
                    " Кол-во пользователей: %d", id, userStorage.findAll().size()));
        }
        log.debug("Найден пользователь c id {}.", id);
        return userStorage.getUsers().get(id);
    }

    private void checkId(Integer user, Integer friend) {
        if (user <= 0 || friend <= 0) {
            throw new NotValidIdException();
        }
    }
}
