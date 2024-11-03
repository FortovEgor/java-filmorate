package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int generatorId = 0;
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    // @TODO: get rid of mocks below
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
        log.debug("Пользователь {} создан.", user.getLogin());
        return user;
    }

    @Override
    public User update(User user) throws ValidationException {
        int id = user.getId();
        if (!users.containsKey(id)) {
            log.debug("Пользователь не найден.");
            throw new ValidationException("Пользователь не найден.");
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
    public Map<Integer, User> getUsers() {  // @FIXME: unused?
        return new ConcurrentHashMap<>();
    }

    @Override
    public final void validateUser(User user) throws ValidationException {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getLogin().isBlank() || user.getLogin().matches(".*\\s+.*")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
//        if (user.getName() == null || user.getName().isBlank()) {  // в таком случае будет использован ЛОГИН
//            throw new ValidationException("Имя не может быть пустым или быть null.");
//        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }
}
