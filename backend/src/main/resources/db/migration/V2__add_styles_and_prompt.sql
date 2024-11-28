-- Добавляем таблицу с системными промптами
CREATE TABLE prompts
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    assistant_id BIGINT       NOT NULL,
    FOREIGN KEY (assistant_id) REFERENCES assistants (id)
);

-- Добавляем свойства стилей для ассистентов
ALTER TABLE assistants
    ADD COLUMN styles TEXT DEFAULT '{}';

-- Добавляем значения стилей по умолчанию
UPDATE assistants
SET styles = '{}'
WHERE styles IS NULL;

-- Устанавливаем NOT NULL для стилей
ALTER TABLE assistants
    ALTER COLUMN styles SET NOT NULL;
