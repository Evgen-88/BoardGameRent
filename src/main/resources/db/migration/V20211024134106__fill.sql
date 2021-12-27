INSERT INTO users(login, password, name, phone, email)
VALUES  ('ivan', '$2a$12$Zz1osbGrGZrFpB0OuozlZuqtcjKdr0iAN0OM0/BdVlZhPxaMx01jW', 'Иван', 1111111, 'ivan@mail'),
		('alex', '$2a$12$QmvrBgy8.RAuCj9cZ3yQDek1ZOKY/nRF7vb7kyIOWo7M9aWxsngi6', 'Алекс', 2222222, 'alex@mail'),
		('kir', '$2a$12$yaZL8PGRDB4MiwrwPajavOEWgL5d1FyldWWWEMWSzm2v/xqzHuU0C', 'Кирилл', 3333333, 'kir@mail'),
		('piter', '$2a$12$AhLOgtPWDytNQtixRXef8.KLeZNPUDNgzjJ/VB/yZJnivQ0rSSkFC', 'Петр', 4444444, 'piter@mail'),
		('vova', '$2a$12$eIV9PyAhpi9SvGTG.c/DU.CuP54stjgX/uiMNMQHCeqre/2/6mPZm', 'Владимир', 5555555, 'vova@mail');
		
INSERT INTO user_role(name)
VALUES  ('admin'),
		('manager'),
		('customer');

INSERT INTO authorities(name)
VALUES  ('ACCESSORY_WRITE_DELETE'),
        ('BOARD_GAME_WRITE_DELETE'),
        ('ORDER_READ_WRITE'),
        ('ORDER_DELETE'),
        ('PURCHASE_READ_WRITE'),
        ('PURCHASE_DELETE'),
        ('RENT_READ_WRITE'),
        ('RENT_DELETE'),
        ('ROLE_READ'),
        ('USER_READ'),
        ('USER_DELETE');
		
INSERT INTO users_user_role_link(role_id, user_id)
VALUES  (1, 1),
		(3, 3),
		(2, 3),
		(3, 1),
		(3, 4),
		(3, 2),
		(3, 5);

INSERT INTO user_role_authorities_link(role_id, authority_id)
VALUES  (1, 9),
        (1, 11),
        (2, 1),
        (2, 2),
        (2, 3),
        (2, 4),
        (2, 5),
        (2, 6),
        (2, 7),
        (2, 8),
        (2, 10),
        (3, 3),
        (3, 5),
        (3, 7),
        (3, 10);
		
INSERT INTO orders(user_id, total_price, order_date, status)
VALUES  (1, 45, '2021-10-23', 'confirmed'),
		(4, 90, '2021-10-23', 'confirmed'),
		(2, 126, '2021-10-23', 'confirmed'),
		(4, 120, '2021-10-22', 'booked');
		
INSERT INTO board_game(name, rent_price, quantity)
VALUES  ('Сквозь века', 45, 3),
		('Сумеречная борьба', 40, 2),
		('Звездные Войны. Восстание', 50, 2),
		('Особняки безумия', 50, 4);

INSERT INTO rent(board_game_id, order_id, rent_from, rent_to, price)
VALUES  (1, 1, '2021-10-23', '2021-10-24', 45),
		(1, 2, '2021-10-23', '2021-10-25', 90),
		(3, 3, '2021-10-22', '2021-10-24', 100),
		(2, 4, '2021-10-25', '2021-10-28', 120);
		
INSERT INTO accessory(name, price, quantity)
VALUES  ('Протекторы для карт 65х87', 12, 24),
		('Протекторы для карт 48х64', 13, 20),
		('Протекторы для карт 102х143', 18, 11);
		
INSERT INTO purchase(accessory_id, order_id, quantity, price)
VALUES  (2, 3, 2, 26),
		(1, 2, 3, 36);

COMMIT;

		