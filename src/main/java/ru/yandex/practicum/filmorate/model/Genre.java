package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class Genre {
    @NonNull
    private Integer id;
    private String name;
}
