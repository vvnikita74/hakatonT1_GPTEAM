CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Добавляем роль
INSERT INTO roles (name)
VALUES ('ROLE_USER');

-- Добавляем админ-ассистента (пароль admin)
INSERT INTO users (name, password)
VALUES ('admin', '$2a$10$9tmvHxdW5D88gbNv.B8WOemwbh8EbNuewuVVtvrA.6o85eo6/IOuu');

-- Добавляем админу роль
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);