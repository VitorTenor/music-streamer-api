create table if not exists musics (
    id serial primary key,
    name varchar(255) not null,
    artist varchar(255) not null,
    path_name varchar(255) not null
    created_at timestamp not null,
    updated_at timestamp not null
);
