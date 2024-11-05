package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserStorage {
    // методы добавления, удаления и модификации объектов
    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

    void remove(int id);

    Collection<User> findAll();

    Map<Integer, User> getUsers();

    void validateUser(User user) throws ValidationException;

    List<User> getUserFriends(Integer id);

    User findUserById(Integer id);

    List<User> getCommonFriends(Integer user, Integer friend);
}
