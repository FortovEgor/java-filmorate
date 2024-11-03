package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserStorage implements UserStorage {
    // @TODO: get rid of mocks below
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public Map<Integer, User> getUsers() {
        return new ConcurrentHashMap<>();
    }
}
