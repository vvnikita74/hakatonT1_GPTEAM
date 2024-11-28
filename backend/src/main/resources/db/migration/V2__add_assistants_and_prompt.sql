CREATE TABLE assistants
(
    id       BIGSERIAL PRIMARY KEY,
    styles   TEXT,
    api_key  VARCHAR(255),
    FOREIGN KEY (id) REFERENCES users (id)
);

-- Добавляем таблицу с системными промптами
CREATE TABLE prompts
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    assistant_id BIGINT       NOT NULL,
    FOREIGN KEY (assistant_id) REFERENCES assistants (id)
);

-- Добавляем админа-ассистента
INSERT INTO assistants (id, styles, api_key)
VALUES (1, '', 'n7Z3OSfudx6QuH0GWvwAXIStDkt1JGla3zS7uE0wUfs');
