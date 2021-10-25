INSERT INTO users(login, password, name, phone, email)
VALUES  ('ivan', 'ivan', 'Иван', 1111111, 'ivan@mail'),
		('alex', 'alex', 'Алекс', 2222222, 'alex@mail'),
		('kir', 'kir', 'Кирилл', 3333333, 'kir@mail'),
		('piter', 'piter', 'Петр', 4444444, 'piter@mail'),
		('vova', 'vova', 'Владимир', 5555555, 'vova@mail');
		
INSERT INTO user_role(name)
VALUES  ('admin'),
		('manager'),
		('customer');
		
INSERT INTO users_user_role_link(role_id, user_id)
VALUES  (1, 1),
		(2, 3),
		(3, 1),
		(3, 4),
		(3, 2),
		(3, 5);
		
INSERT INTO orders(user_id, total_price, oorder_date, status)
VALUES  (1, 45, '2021-10-23', 'confirmed'),
		(4, 90, '2021-10-23', 'confirmed'),
		(2, 126, '2021-10-23', 'confirmed'),
		(5, 120, '2021-10-25', 'booked');
		
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
VALUES  (2, 3, 2, 26);

COMMIT;

		