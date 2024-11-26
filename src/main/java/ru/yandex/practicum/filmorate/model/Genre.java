package ru.yandex.practicum.filmorate.model;

import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @NonNull
    Integer id;
//    @NotBlank
    String name;
}
