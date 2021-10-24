INSERT INTO users('id', 'login', 'password', 'name', 'phone', 'email')
VALUES  (1, 'ivan', 'ivan', 'Иван', 1111111, 'ivan@mail'),
	(2, 'alex', 'alex', 'Алекс', 2222222, 'alex@mail'),
	(3, 'kir', 'kir', 'Кирилл', 3333333, 'kir@mail'),
	(4, 'piter', 'piter', 'Петр', 4444444, 'piter@mail'),
	(5, 'vova', 'vova', 'Владимир', 5555555, 'vova@mail');
		
INSERT INTO users_user_role_link('role_id', 'user_id')
VALUES  (1, 1),
	(2, 3),
	(3, 1),
	(3, 4),
	(3, 2),
	(3, 5);
		
INSERT INTO user_role('id', 'name')
VALUES  (1, 'admin'),
	(2, 'manager'),
	(3, 'customer');
		
INSERT INTO board_game('id', 'name', 'rent_price', 'quantity')
VALUES  (1, 'Сквозь века', 45, 3),
	(2, 'Сумеречная борьба', 40, 2),
	(3, 'Звездные Войны. Восстание', 50, 2),
	(4, 'Особняки безумия', 50, 4);

INSERT INTO rent('id', 'board_game_id', 'order_id', 'from', 'to', 'price')
VALUES  (1, 1, 1, '2021-10-23', '2021-10-24', 45),
	(2, 1, 2, '2021-10-23', '2021-10-25', 90),
	(3, 3, 3, '2021-10-22', '2021-10-24', 100),
	(4, 2, 4, '2021-10-25', '2021-10-28', 120),

INSERT INTO orders('id', 'user_id', 'total_price', 'date', 'status')
VALUES  (1, 1, 45, '2021-10-23', 'confirmed'),
	(2, 4, 90, '2021-10-23', 'confirmed'),
	(3, 2, 126, '2021-10-23', 'confirmed'),
	(4, 5, 120, '2021-10-25', 'booked');
		
INSERT INTO accessory('id', 'name', 'price', 'quantity')
VALUES  (1, 'Протекторы для карт 65х87', 12, 24),
	(2, 'Протекторы для карт 48х64', 13, 20),
	(3, 'Протекторы для карт 102х143', 18, 11);
		
INSERT INTO purchase('id', 'accessory_id', 'order_id', 'quantity', 'price')
VALUES  (1, 2, 3, 2, 26);

COMMIT;

		
