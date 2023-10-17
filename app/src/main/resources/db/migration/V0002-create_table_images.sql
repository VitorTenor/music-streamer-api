CREATE TABLE IF NOT EXISTS images (
    id INT SERIAL PRIMARY KEY,
    music_id INT NOT NULL,
    path_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (music_id) REFERENCES musics(id)
);
