-- Создание таблиц
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, role)
    );

CREATE TABLE IF NOT EXISTS restaurants (
                                           id SERIAL PRIMARY KEY,
                                           name VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS menus (
                                     id SERIAL PRIMARY KEY,
                                     restaurant_id INTEGER REFERENCES restaurants(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    dish_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    UNIQUE(restaurant_id, date, dish_name)
    );

CREATE TABLE IF NOT EXISTS votes (
                                     id SERIAL PRIMARY KEY,
                                     user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    restaurant_id INTEGER REFERENCES restaurants(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    time TIME NOT NULL,
    UNIQUE(user_id, date)
    );

-- Вставка тестовых данных (пользователи)
INSERT INTO users (name, email, password, enabled) VALUES
                                                       ('Admin', 'admin@example.com', '{noop}admin', true),
                                                       ('User', 'user@example.com', '{noop}password', true),
                                                       ('Guest', 'guest@example.com', '{noop}guest', true)
    ON CONFLICT (email) DO NOTHING;

-- Роли
INSERT INTO user_roles (user_id, role) VALUES
                                           (1, 'ADMIN'),
                                           (2, 'USER'),
                                           (3, 'USER')
    ON CONFLICT DO NOTHING;

-- Рестораны
INSERT INTO restaurants (name, address) VALUES
                                            ('Япоша', 'ул. Ленина, 10'),
                                            ('Макдональдс', 'пр. Мира, 25'),
                                            ('Итальянская пиццерия', 'ул. Пушкина, 5')
    ON CONFLICT (name) DO NOTHING;

-- Меню на сегодня
INSERT INTO menus (restaurant_id, date, dish_name, price) VALUES
                                                              (1, CURRENT_DATE, 'Роллы Филадельфия', 450.00),
                                                              (1, CURRENT_DATE, 'Суши с лососем', 350.00),
                                                              (2, CURRENT_DATE, 'Биг Мак', 250.00),
                                                              (2, CURRENT_DATE, 'Картошка фри', 120.00),
                                                              (3, CURRENT_DATE, 'Маргарита', 350.00),
                                                              (3, CURRENT_DATE, 'Пепперони', 420.00)
    ON CONFLICT DO NOTHING;