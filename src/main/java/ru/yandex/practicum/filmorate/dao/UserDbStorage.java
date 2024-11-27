package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getLogin());
                    ps.setString(3, user.getName());
                    ps.setDate(4, Date.valueOf(user.getBirthday()));
                    return ps;
                }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";
        final int res = jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public void remove(int id) {  // not used
        // @TODO: implement
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT id, login, name, email, birthday FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public Map<Integer, User> getUsers() {
        // @TODO: implement
        return null;
    }

    @Override
    public List<User> getUserFriends(Integer id) {
        // @TODO: implement
        return null;
    }

    @Override
    public User findUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Optional<User> possibleUser = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), id).stream().findFirst();
        return possibleUser.orElse(null);
    }

    @Override
    public List<User> getCommonFriends(Integer user, Integer friend) {
        // @TODO: implement
        return null;
    }

    private User makeUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }
}