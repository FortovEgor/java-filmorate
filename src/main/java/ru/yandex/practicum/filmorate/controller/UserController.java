package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
    private int generatorId = 0;
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    @GetMapping
    public final List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        if (users.containsValue(user)) {
            throw new ValidationException("Такой пользователь уже существует.");
        }
        validateUser(user);
        user.setId(++generatorId);
        users.put(user.getId(), user);
        log.debug("Пользователь {} создан.", user.getLogin());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
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

    public final void validateUser(User user) throws ValidationException {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getLogin().isBlank() || user.getLogin().matches(".*\\s+.*")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new ValidationException("Имя не может быть пустым или быть null.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }
}