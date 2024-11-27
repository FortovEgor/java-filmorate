DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS friends CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS mpa_ratings CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS films_genres CASCADE;

CREATE TABLE IF NOT EXISTS users(
                                    id  INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                    email    VARCHAR NOT NULL,
                                    login    VARCHAR(255) NOT NULL,
    name     VARCHAR(255),
    birthday DATE NOT NULL
    );

CREATE UNIQUE INDEX IF NOT EXISTS user_email_uindex ON users (email);
CREATE UNIQUE INDEX IF NOT EXISTS user_login_uindex ON users (login);

CREATE TABLE IF NOT EXISTS friends(
                                         PRIMARY KEY(user_id, friend_id),
    user_id   INT,
    friend_id INT,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(friend_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS mpa_ratings(
                                         id INT AUTO_INCREMENT,
                                         name      VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS films(
                                    film_id     INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                    name        VARCHAR(255) NOT NULL,
    description VARCHAR(200) NOT NULL,
    releaseDate DATE NOT NULL,
    duration    INT,
    rating_id   INT
    );

CREATE TABLE IF NOT EXISTS likes(
                                    film_id INT,
                                    user_id INT,
                                    PRIMARY KEY(user_id, film_id),
    FOREIGN KEY(film_id) REFERENCES films(film_id),
    FOREIGN KEY(user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS genres(
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name     VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS films_genres(
                                          id       INT AUTO_INCREMENT,
                                          film_id  INT,
                                          genre_id INT,
                                          FOREIGN KEY(film_id) REFERENCES films(film_id),
    FOREIGN KEY(genre_id) REFERENCES genres(id)
    );

