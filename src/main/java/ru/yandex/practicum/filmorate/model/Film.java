package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import java.time.LocalDate;

/**
 * Film.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private int id;
    @NonNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    @NonNull
    private MpaRating mpa;
    private final LinkedHashSet<Genre> genres = new LinkedHashSet<>();
    private Set<Integer> idUsersWhoLikedFilm = new HashSet<>();

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

    public static Integer getFilmsLikes(Film film) {
        return film.getIdUsersWhoLikedFilm().size();
    }
}
