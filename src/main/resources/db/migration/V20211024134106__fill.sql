INSERT INTO users('id', 'login', 'password', 'name', 'phone', 'email')
VALUES  (1L, 'ivan', 'ivan', 'Иван', 1111111, 'ivan@mail'),
		(2L, 'alex', 'alex', 'Алекс', 2222222, 'alex@mail'),
		(3L, 'kir', 'kir', 'Кирилл', 3333333, 'kir@mail'),
		(4L, 'piter', 'piter', 'Петр', 4444444, 'piter@mail'),
		(5L, 'vova', 'vova', 'Владимир', 5555555, 'vova@mail');
		
INSERT INTO users_user_role_link('role_id', 'user_id')
VALUES  (1L, 1L),
		(2L, 3L),
		(3L, 1L),
		(3L, 4L),
		(3L, 2L),
		(3L, 5L);
		
INSERT INTO user_role('id', 'name')
VALUES  (1L, 'admin'),
		(2L, 'manager'),
		(3L, 'customer');
		
INSERT INTO board_game('id', 'name', 'rent_price', 'quantity')
VALUES  (1L, 'Сквозь века', 45, 3),
		(2L, 'Сумеречная борьба', 40, 2),
		(3L, 'Звездные Войны. Восстание', 50, 2),
		(4L, 'Особняки безумия', 50, 4);

INSERT INTO rent('id', 'board_game_id', 'order_id', 'from', 'to', 'price')
VALUES  (1L, 1L, 1L, '2021-10-23', '2021-10-24', 45),
		(2L, 1L, 2L, '2021-10-23', '2021-10-25', 90),
		(3L, 3L, 3L, '2021-10-22', '2021-10-24', 100),
		(4L, 2L, 4L, '2021-10-25', '2021-10-28', 120),

INSERT INTO orders('id', 'user_id', 'total_price', 'date', 'status')
VALUES  (1L, 1L, 45, '2021-10-23', 'confirmed'),
		(2L, 4L, 90, '2021-10-23', 'confirmed'),
		(3L, 2L, 126, '2021-10-23', 'confirmed'),
		(4L, 5L, 120, '2021-10-25', 'booked');
		
INSERT INTO accessory('id', 'name', 'price', 'quantity')
VALUES  (1L, 'Протекторы для карт 65х87', 12, 24),
		(2L, 'Протекторы для карт 48х64', 13, 20),
		(3L, 'Протекторы для карт 102х143', 18, 11);
		
INSERT INTO purchase('id', 'accessory_id', 'order_id', 'quantity', 'price')
VALUES  (1L, 2L, 3L, 2, 26);

COMMIT;

		