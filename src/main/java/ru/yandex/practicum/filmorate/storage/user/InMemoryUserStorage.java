package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
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
    public Map<Integer, User> getUsers() {  // @FIXME: unused?
        return new ConcurrentHashMap<>();
    }
}
