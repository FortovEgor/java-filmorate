DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS mpa_ratings CASCADE;
DROP TABLE IF EXISTS films_genres CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS friends CASCADE;

CREATE TABLE IF NOT EXISTS films (
                         "id" integer PRIMARY KEY,
                         "name" varchar(100) NOT NULL,
                         "description" varchar(2000),
                         "release_date" date,
                         "duration" int,
                         "rating_id" int
);

CREATE TABLE IF NOT EXISTS "users" (
                         "id" integer PRIMARY KEY,
                         "name" varchar(30),
                         "login" varchar(30) NOT NULL,
                         "email" varchar(30) NOT NULL,
                         "birthday" date
);

CREATE TABLE IF NOT EXISTS mpa_ratings (
                               id integer PRIMARY KEY,
                               name varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS films_genres (
                                film_id integer,
                                genre_id integer,
                                PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS genres (
                          id integer PRIMARY KEY,
                          name varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS likes (
                         film_id integer,
                         user_id integer,
                         PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS "friends" (
                           "user_id" integer,
                           "friend_id" integer,
                           "approved" boolean NOT NULL,
                           PRIMARY KEY ("user_id", "friend_id")
);

ALTER TABLE "films_genres" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE "films_genres" ADD FOREIGN KEY ("genre_id") REFERENCES "genres" ("id");

ALTER TABLE "films" ADD FOREIGN KEY ("rating_id") REFERENCES "mpa_ratings" ("id");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "friends" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "friends" ADD FOREIGN KEY ("friend_id") REFERENCES "users" ("id");
