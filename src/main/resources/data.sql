-- mpa_ratings
INSERT INTO mpa_ratings (id, name)
VALUES
    (1, 'G'),
    (2, 'PG'),
    (3, 'PG-13'),
    (4, 'R'),
    (5, 'NC-17');
-- genres
INSERT INTO genres (id, name)
VALUES
    (1, 'Action'),
    (2, 'Comedy'),
    (3, 'Drama'),
    (4, 'Sci-Fi'),
    (5, 'Horror'),
    (6, 'Thriller'),
    (7, 'Romance'),
    (8, 'Documentary');
-- films
INSERT INTO films (
    id, name, description, release_date,
    duration, rating_id
)
VALUES
    (
        1, 'The Shawshank Redemption', 'A meticulous and gripping tale of wrongful imprisonment and enduring hope.',
        '1994-09-23', 142, 4
    ),
    (
        2, 'The Dark Knight', 'A thrilling crime drama with mind-bending twists and stunning visuals.',
        '2008-07-18', 152, 4
    ),
    (
        3, 'Pulp Fiction', 'A cult classic known for its nonlinear storytelling and unforgettable characters.',
        '1994-10-14', 154, 4
    ),
    (
        4, 'Inception', 'A mind-bending heist film that explores the depths of the human subconscious.',
        '2010-07-16', 148, 3
    ),
    (
        5, 'Toy Story', 'A charming animated film about a group of toys who come to life.',
        '1995-11-22', 81, 1
    ),
    (
        6, 'Jurassic Park', 'A groundbreaking dinosaur adventure that redefined the action genre.',
        '1993-06-11', 127, 3
    );
-- films_genres
INSERT INTO films_genres (film_id, genre_id)
VALUES
    (1, 3),
    (2, 1),
    (2, 6),
    (3, 1),
    (3, 2),
    (4, 4),
    (4, 6),
    (5, 2),
    (5, 8),
    (6, 1),
    (6, 5);
-- users
INSERT INTO users (id, name, login, email, birthday)
VALUES
    (
        1, 'John Doe', 'jdoe', 'jdoe@example.com',
        '1985-03-15'
    ),
    (
        2, 'Jane Doe', 'jane', 'jane@example.com',
        '1990-11-20'
    ),
    (
        3, 'Peter Pan', 'peterpan', 'peter@example.com',
        '1970-01-01'
    );
-- likes
INSERT INTO likes (film_id, user_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 1),
    (5, 3),
    (6, 2);
-- friends
INSERT INTO friends (user_id, friend_id, approved)
VALUES
    (1, 2, true),
    (2, 1, true),
    (1, 3, false);
