package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mpa")
public class MpaRatingController {
    private final FilmService filmService;

    @GetMapping
    public List<MpaRating> findAllMpa() {
        log.info("GET / mpa");
        return filmService.findAllMpa();
    }

    @GetMapping("/{id}")
    public MpaRating findMpaById(@PathVariable("id") int id) {
        log.info("GET / mpa / {}", id);
        return filmService.findMpaById(id);
    }
}