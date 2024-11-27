package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MpaRatingDbStorage implements MpaRatingStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MpaRating> findAllMpa() {
        String sql = "SELECT * FROM mpa_ratings";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpaRating(rs));
    }

    @Override
    public Optional<MpaRating> findMpaById(int id) {
        String sql = "SELECT * FROM mpa_ratings where id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpaRating(rs), id).stream().findFirst();
    }

    private MpaRating makeMpaRating(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new MpaRating(id, name);
    }
}