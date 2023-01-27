create table if not exists images (
    id serial primary key,
    music_id int not null references musics(id),
    path_name varchar(255) not null
    created_at timestamp not null,
    updated_at timestamp not null
);

