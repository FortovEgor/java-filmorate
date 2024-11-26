package ru.yandex.practicum.filmorate.model;

import lombok.*;
@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MpaRating {
    @NonNull
    Integer id;
    @NonNull
    String name;

    public MpaRating(@NonNull Integer id) {
        this.id = id;
    }
}
