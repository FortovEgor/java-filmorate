package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//@Component  // @TODO: uncomment this for Spring to use this Impl
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int generatorId = 0;
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    @Override
    public User create(User user) throws ValidationException {
        if (users.containsValue(user)) {
            throw new ValidationException("Такой пользователь уже существует.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(++generatorId);
        users.put(user.getId(), user);
        log.info("Пользователь {} создан.", user.getLogin());
        return user;
    }

    @Override
    public User update(User user) throws ValidationException {
        int id = user.getId();
        if (!users.containsKey(id)) {
            log.debug("Пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }
        users.put(id, user);
        log.debug("Пользователь {} обновлен.", user.getLogin());
        return user;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Collection<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    public List<User> getCommonFriends(Integer user, Integer friend) {
//        return getUserFriends(user).stream()
//                .filter(getUserFriends(friend)::contains)
//                .collect(Collectors.toList());
        return null;  // temp solution for this sprint
    }

    @Override
    public List<User> getUserFriends(Integer id) {
        if (id <= 0) {
            throw new NotValidIdException();
        }
//        Set<Integer> users = findUserById(id).getFriends();  // @TODO: handle new code architecture
        Set<Integer> users = new HashSet<>();
        List<User> usersFriends = new ArrayList<>();

        if (!users.isEmpty()) {
            for (Integer integer:users) {
                usersFriends.add(findUserById(integer));
            }
        }
        log.debug("Составлен список друзей пользователя c id {}.", id);
        return usersFriends;
    }

    @Override
    public User findUserById(int id) {
        if (id <= 0) {
            throw new NotValidIdException();
        }
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь с запрашиваемым id=%d не зарегестрирован." +
                    " Кол-во пользователей: %d", id, users.size()));
        }
        log.debug("Найден пользователь c id {}.", id);
        return users.get(id);
    }
}
