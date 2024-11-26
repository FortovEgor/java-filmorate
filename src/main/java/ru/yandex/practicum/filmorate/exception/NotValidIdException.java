package ru.yandex.practicum.filmorate.exception;

public class NotValidIdException extends RuntimeException {
    public NotValidIdException() {
        super("id должен быть > ноля.");
    }
}
