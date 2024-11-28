CREATE TABLE assistants
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Добавляем админ-ассистента (пароль admin)
INSERT INTO assistants (name, password)
VALUES ('admin', '$2a$10$9tmvHxdW5D88gbNv.B8WOemwbh8EbNuewuVVtvrA.6o85eo6/IOuu');
