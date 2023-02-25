create table if not exists playlists (
    id serial primary key,
    name varchar(255) not null,
    user_id int not null references users(id),
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists playlist_musics (
    id serial primary key,
    user_id int not null references users(id),
    playlist_id int not null references playlists(id),
    music_id int not null references musics(id),
    created_at timestamp not null,
    updated_at timestamp not null
);
