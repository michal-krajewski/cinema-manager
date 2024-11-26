CREATE TABLE movies
(
    id      uuid PRIMARY KEY,
    title   VARCHAR(1024) NOT NULL,
    imdb_id VARCHAR(64)   NOT NULL
);

CREATE TABLE users
(
    id uuid PRIMARY KEY,
    username VARCHAR,
    encoded_password VARCHAR,
    role VARCHAR
);

CREATE TABLE movie_ticket_prices
(
    movie_id uuid PRIMARY KEY,
    price    numeric NOT NULL,
    CONSTRAINT fk_movie_id_movie_ticket_prices
        FOREIGN KEY (movie_id)
            REFERENCES movies (id)
);

CREATE TABLE movie_scores
(
    user_id  uuid NOT NULL,
    movie_id uuid NOT NULL,
    score    int  NOT NULL,
    PRIMARY KEY (user_id, movie_id),
    CONSTRAINT fk_movie_id_movie_scores
        FOREIGN KEY (movie_id)
            REFERENCES movies (id),
    CONSTRAINT fk_user_id_movie_scores
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);

CREATE TABLE show_schedules
(
    id uuid PRIMARY KEY,
    movie_id uuid REFERENCES movies(id),
    start_time timestamp with time zone
);

