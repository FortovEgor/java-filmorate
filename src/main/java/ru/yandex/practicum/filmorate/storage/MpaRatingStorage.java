package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

public interface MpaRatingStorage {
    List<MpaRating> findAllMpa();

    Optional<MpaRating> findMpaById(int id);
}
