INSERT INTO role(name) VALUES ('ADMIN'), ('USER');

-- demo admin (password: admin123)
INSERT INTO users(id, username, password, email, enabled) VALUES (1, 'admin', '$2a$10$5NC6C9bT6xS5aXKzvpsv1eXq3l5Z0p8vWwzK3Yk3d8VYw3kR9bNSe', 'admin@example.com', true);
INSERT INTO user_roles(user_id, role_id) VALUES (1, 1);
