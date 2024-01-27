INSERT INTO users (id, name, email, password, created_at, updated_at, role, active)
VALUES
    (0,'admin', 'admin@admin.com', '$2a$10$XPT8j0CWsiEjwPqurzWRXOPfENDjbpolLRP8U/81reL43xaYEKeNe', NOW(), NOW(), 0, 1),
    (1, 'user', 'user@user.com', '$2a$10$XPT8j0CWsiEjwPqurzWRXOPfENDjbpolLRP8U/81reL43xaYEKeNe', NOW(), NOW(), 1, 1);
