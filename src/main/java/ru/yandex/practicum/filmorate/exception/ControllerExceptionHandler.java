package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationException(ValidationException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Validation error", e.getMessage());

        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Объект не найден", e.getMessage());

        log.error(e.getStackTrace()[0].getMethodName() + ": " + e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(NotValidIdException.class)
    public ResponseEntity<?> notValidIdException(NotValidIdException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Некорректное значение", e.getMessage());

        log.error(e.getStackTrace()[0].getMethodName() + ": " + e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

    // Обработка всех остальных исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Internal exception occured", e.getMessage());

        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}
