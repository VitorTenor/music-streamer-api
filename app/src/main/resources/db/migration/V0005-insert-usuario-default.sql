insert into users(id, name, email, password, created_at, updated_at, role, active) values
((select next_val as id_val from users_seq for update) , 'admin', 'admin@admin.com', '123456', now(), now(), 'ADMIN', true);

update users_seq set next_val= next_val + 1;

insert into users(id, name, email, password, created_at, updated_at, role, active) values
((select next_val as id_val from users_seq for update) ,'user', 'user@user.com', '123456', now(), now(), 'USER', true);

update users_seq set next_val= next_val + 1;