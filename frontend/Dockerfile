# Этап 1: Сборка приложения
FROM node:20 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем package.json и package-lock.json для установки зависимостей
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем все файлы проекта в контейнер
COPY . .

# Собираем приложение
RUN npm run build

# Этап 2: Развертывание приложения
FROM nginx:alpine

# Удаляем стандартный конфигурационный файл nginx
RUN rm /etc/nginx/conf.d/default.conf

# Копируем наш конфигурационный файл nginx в контейнер
COPY nginx.conf /etc/nginx/conf.d

# Копируем собранные файлы из предыдущего этапа в папку, откуда nginx будет обслуживать файлы
COPY --from=build /app/dist /usr/share/nginx/html

# Открываем порт, на котором будет доступно приложение
EXPOSE 80

# Запускаем nginx
CMD ["nginx", "-g", "daemon off;"]