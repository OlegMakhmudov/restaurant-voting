-- -- ============================================================
-- -- ОЧИСТКА ТАБЛИЦ (для случая, если данные уже есть)
-- -- ============================================================
-- DELETE FROM votes;
-- DELETE FROM menus;
-- DELETE FROM user_roles;
-- DELETE FROM users;
-- DELETE FROM restaurants;
-- ALTER SEQUENCE users_id_seq RESTART WITH 1;
-- ALTER SEQUENCE restaurants_id_seq RESTART WITH 1;
-- ALTER SEQUENCE menus_id_seq RESTART WITH 1;
-- ALTER SEQUENCE votes_id_seq RESTART WITH 1;

-- ============================================================
-- ПОЛЬЗОВАТЕЛИ
-- ============================================================
-- Пароли с префиксом {noop} — без шифрования (для тестирования)
-- В продакшене нужно использовать шифрование

INSERT INTO users (name, email, password, enabled) VALUES
                                                       ('Admin', 'admin@example.com', '{noop}admin', true),
                                                       ('User', 'user@example.com', '{noop}password', true),
                                                       ('Guest', 'guest@example.com', '{noop}guest', true);

-- ============================================================
-- РОЛИ ПОЛЬЗОВАТЕЛЕЙ
-- ============================================================
INSERT INTO user_roles (user_id, role) VALUES
                                           (1, 'ADMIN'),
                                           (2, 'USER'),
                                           (3, 'USER');

-- ============================================================
-- РЕСТОРАНЫ
-- ============================================================
INSERT INTO restaurants (name, address) VALUES
                                            ('Япоша', 'ул. Ленина, 10'),
                                            ('Макдональдс', 'пр. Мира, 25'),
                                            ('Итальянская пиццерия', 'ул. Пушкина, 5');

-- ============================================================
-- МЕНЮ НА СЕГОДНЯ
-- ============================================================
-- Япоша (ресторан 1)
INSERT INTO menus (restaurant_id, date, dish_name, price) VALUES
                                                              (1, CURRENT_DATE, 'Роллы Филадельфия', 450.00),
                                                              (1, CURRENT_DATE, 'Суши с лососем', 350.00),
                                                              (1, CURRENT_DATE, 'Гунканы с угрем', 400.00);

-- Макдональдс (ресторан 2)
INSERT INTO menus (restaurant_id, date, dish_name, price) VALUES
                                                              (2, CURRENT_DATE, 'Биг Мак', 250.00),
                                                              (2, CURRENT_DATE, 'Картошка фри', 120.00),
                                                              (2, CURRENT_DATE, 'Чизбургер', 150.00),
                                                              (2, CURRENT_DATE, 'Наггетсы (6 шт.)', 180.00);

-- Итальянская пиццерия (ресторан 3)
INSERT INTO menus (restaurant_id, date, dish_name, price) VALUES
                                                              (3, CURRENT_DATE, 'Маргарита', 350.00),
                                                              (3, CURRENT_DATE, 'Пепперони', 420.00),
                                                              (3, CURRENT_DATE, 'Четыре сыра', 480.00);

-- ============================================================
-- МЕНЮ НА ПРОШЛЫЕ ДНИ (для истории)
-- ============================================================
-- Вчерашнее меню Япоша
INSERT INTO menus (restaurant_id, date, dish_name, price) VALUES
                                                              (1, CURRENT_DATE - 1, 'Роллы Калифорния', 430.00),
                                                              (1, CURRENT_DATE - 1, 'Суши с креветкой', 320.00);

-- Вчерашнее меню Макдональдс
INSERT INTO menus (restaurant_id, date, dish_name, price) VALUES
                                                              (2, CURRENT_DATE - 1, 'Роял Чизбургер', 280.00),
                                                              (2, CURRENT_DATE - 1, 'МакФлурри', 150.00);

-- ============================================================
-- ГОЛОСА (примеры)
-- ============================================================
-- Голос пользователя User за Япоша сегодня
INSERT INTO votes (user_id, restaurant_id, date, time) VALUES
    (2, 1, CURRENT_DATE, '10:30:00');

-- Голос администратора за Макдональдс сегодня (другой пользователь, не конфликтует)
INSERT INTO votes (user_id, restaurant_id, date, time) VALUES
    (1, 2, CURRENT_DATE, '09:15:00');

-- Голос пользователя Guest за Пиццерию вчера
INSERT INTO votes (user_id, restaurant_id, date, time) VALUES
    (3, 3, CURRENT_DATE - 1, '12:00:00');