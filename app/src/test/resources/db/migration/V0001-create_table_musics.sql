CREATE TABLE IF NOT EXISTS musics (
    id INT SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    path_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
