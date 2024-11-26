package ru.yandex.practicum.filmorate.model;

import lombok.*;
@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @NonNull
    private Integer id;
    private String name;
}
