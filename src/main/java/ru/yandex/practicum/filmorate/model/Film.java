package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * Film.
 */
@Data
@Builder
public class Film {
    private int id;
    @NotBlank(message = "Введите название фильма.")
    private String name;
    @NotNull
    @Size(max = 200, message = "Слишком длинное описание.")
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть больше 0.")
    private Integer duration;
    @NotNull
    private MpaRating mpa;
    private LinkedHashSet<Genre> genres = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Film other = (Film) obj;
        return id == other.id;
    }
}
