DROP TABLE IF EXISTS players;

CREATE TABLE IF NOT EXISTS players (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(255) NOT NULL,
    sprite int NOT NULL,
    wins int NOT NULL,
    losses int NOT NULL,
    x_pos int NOT NULL,
    y_pos int NOT NULL
);
