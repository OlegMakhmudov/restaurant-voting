# Restaurant Voting System

Система голосования для выбора ресторана для обеда.

## 📋 О проекте

REST API приложение для голосования за рестораны.  
Администратор управляет ресторанами и меню, пользователи голосуют за понравившийся ресторан.

### Основные возможности

- ✅ Аутентификация и авторизация (ADMIN / USER)
- ✅ Админ: управление ресторанами (CRUD)
- ✅ Админ: управление меню (CRUD)
- ✅ Пользователь: просмотр ресторанов и сегодняшнего меню
- ✅ Пользователь: голосование за ресторан
- ✅ Ограничение: один голос в день от пользователя
- ✅ Ограничение: изменение голоса только до 11:00

## 🛠️ Стек технологий

- Java 17
- Spring Boot 2.7.18
- Spring Data JPA (Hibernate)
- Spring Security
- Spring Web MVC
- H2 Database
- Lombok
- Swagger/OpenAPI
- 
## 🏗️ Архитектура

- Слои: Controller → Service → Repository
- Контроллеры разделены по ролям (`/api/...`, `/api/admin/...`)
- Использованы DTO для изоляции сущностей
- Обработка ошибок через `@RestControllerAdvice`
- Логирование через SLF4J

## 🚀 Запуск

```bash
git clone https://github.com/OlegMakhmudov/restaurant-voting.git
cd restaurant-voting
mvn spring-boot:run