package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.NotValidIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int generatorId = 0;
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    @Override
    public User create(User user) throws ValidationException {
        if (users.containsValue(user)) {
            throw new ValidationException("Такой пользователь уже существует.");
        }
        validateUser(user);
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
        validateUser(user);
        users.put(id, user);
        log.debug("Пользователь {} обновлен.", user.getLogin());
        return user;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
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

    public List<User> getCommonFriends(Integer user, Integer friend) {
        return getUserFriends(user).stream()
                .filter(getUserFriends(friend)::contains)
                .collect(Collectors.toList());
    }

    @Override
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

    @Override
    public User findUserById(Integer id) {
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
