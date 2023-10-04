create if not exists table users (
    id serial primary key,
    name varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    role varchar(255) not null,
   active boolean not null,
);