package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
public class MpaRating {
    Integer id;
    String name;

    public MpaRating(@NonNull Integer id) {
        this.id = id;
    }
}
