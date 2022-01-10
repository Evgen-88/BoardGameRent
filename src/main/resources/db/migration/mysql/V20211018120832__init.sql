CREATE TABLE IF NOT EXISTS users
(
	id			BIGINT			NOT NULL AUTO_INCREMENT,
	login		VARCHAR(32)  	NOT NULL,
	password	VARCHAR(256)	NOT NULL,
	name		VARCHAR(32)  	NOT NULL,
	phone		BIGINT 			NULL,
	email		VARCHAR(32)  	NULL,
	PRIMARY KEY (id),
	CONSTRAINT UNIQUE_LOGIN UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS user_role
(
	id		BIGINT		NOT NULL AUTO_INCREMENT,
	name	VARCHAR(32)	NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT UNIQUE_ROLE_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS authorities
(
    id		BIGINT		NOT NULL AUTO_INCREMENT,
    name	VARCHAR(32)	NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UNIQUE_AUTHORITY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS users_user_role_link
(
	role_id	BIGINT	 NOT NULL,
	user_id	BIGINT	 NOT NULL,
	PRIMARY KEY (role_id, user_id),
    CONSTRAINT users_user_role_link_users_fk
    	FOREIGN KEY (user_id)
        	REFERENCES users (id),
    CONSTRAINT users_user_role_link_user_role_fk
        FOREIGN KEY (role_id)
            REFERENCES user_role (id)
);

CREATE TABLE IF NOT EXISTS user_role_authorities_link
(
    role_id         BIGINT	 NOT NULL,
    authority_id    BIGINT	 NOT NULL,
    PRIMARY KEY (role_id, authority_id),
    CONSTRAINT user_role_authorities_link_user_role_fk
        FOREIGN KEY (role_id)
            REFERENCES user_role (id),
    CONSTRAINT user_role_authorities_link_authorities_fk
        FOREIGN KEY (authority_id)
            REFERENCES authorities (id)
);

CREATE TABLE IF NOT EXISTS orders
(
	id			BIGINT									NOT NULL AUTO_INCREMENT,
	user_id		BIGINT									NULL,
	total_price	BIGINT 									NOT NULL,
	order_date	DATE									NOT NULL,
	status		ENUM('booked', 'rejected', 'confirmed')	NOT NULL,
	PRIMARY KEY (id),
    CONSTRAINT orders_users_fk
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS board_game
(
	id			BIGINT			NOT NULL AUTO_INCREMENT,
	name		VARCHAR(512)	NOT NULL,
	rent_price	BIGINT 			NOT NULL,
	quantity	BIGINT 			NULL,
	PRIMARY KEY (id),
	CONSTRAINT UNIQUE_BOARD_GAME_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS rent
(
	id				BIGINT	NOT NULL AUTO_INCREMENT,
	board_game_id	BIGINT	NULL,
	order_id		BIGINT	NULL,
	rent_from		DATE	NOT NULL,
	rent_to			DATE	NOT NULL,
	price			BIGINT 	NOT NULL,
	PRIMARY KEY (id),
    CONSTRAINT rent_board_game_fk
        FOREIGN KEY (board_game_id)
            REFERENCES board_game (id),
    CONSTRAINT rent_orders_fk
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
);

CREATE TABLE IF NOT EXISTS accessory
(
	id			BIGINT			NOT NULL AUTO_INCREMENT,
	name		VARCHAR(512) 	NOT NULL,
	price		BIGINT 			NOT NULL,
	quantity	BIGINT 			NULL,
	PRIMARY KEY (id),
	CONSTRAINT UNIQUE_ACCESSORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS purchase
(
	id				BIGINT	NOT NULL AUTO_INCREMENT,
	accessory_id	BIGINT	NULL,
	order_id		BIGINT	NULL,
	quantity		BIGINT	NOT NULL,
	price			BIGINT 	NOT NULL,
	PRIMARY KEY (id),
    CONSTRAINT purchase_accessory_fk
        FOREIGN KEY (accessory_id)
            REFERENCES accessory (id),
    CONSTRAINT purchase_orders_fk
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
);

COMMIT;