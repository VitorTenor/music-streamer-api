CREATE TABLE IF NOT EXISTS playlists (
    id INT SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS playlist_musics (
    id INT SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    playlist_id INT NOT NULL,
    music_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (playlist_id) REFERENCES playlists(id),
    FOREIGN KEY (music_id) REFERENCES musics(id)
);
