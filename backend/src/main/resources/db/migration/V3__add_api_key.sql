-- Добавляем колонку api_key в таблицу assistants
ALTER TABLE assistants
    ADD COLUMN api_key VARCHAR(255);

-- Устанавливаем значение по умолчанию для существующих записей
UPDATE assistants
SET api_key = ''
WHERE assistants.api_key IS NULL;