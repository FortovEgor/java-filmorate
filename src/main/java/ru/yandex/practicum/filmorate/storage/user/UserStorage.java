package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {
    // методы добавления, удаления и модификации объектов
    User create(User user) throws ValidationException;
    User update(User user) throws ValidationException;
    void remove(int id);
    Collection<User> findAll();
    Map<Integer, User> getUsers();
}
