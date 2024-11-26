package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
public class Genre {
    @NonNull
    private Integer id;
    private String name;
}
