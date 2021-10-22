CREATE TABLE IF NOT EXISTS 'user'
(
	'id'		BIGINT			NOT NULL AUTO_INCREMENT,
	'login'		VARCHAR(32)  	NOT NULL,
	'password'	VARCHAR(256)	NOT NULL,
	'name'		VARCHAR(32)  	NOT NULL,
	'phone'		BIGINT 			NULL,
	'email'		VARCHAR(32)  	NULL,
	PRIMARY KEY ('id'),
	CONSTRAINT UNIQUE_LOGIN UNIQUE ('login')
);

CREATE TABLE IF NOT EXISTS 'user_role_link' (
	'role_id'	BIGINT	 NOT NULL,
	'user_id'	BIGINT	 NOT NULL,
	PRIMARY KEY ('role_id', 'user_id'),
    CONSTRAINT 'user_role_link_user_fk'
    	FOREIGN KEY ('user_id')
        	REFERENCES 'user' ('id')
            	ON DELETE NO ACTION
            	ON UPDATE NO ACTION,
    CONSTRAINT 'user_role_link_role_fk'
        FOREIGN KEY ('role_id')
            REFERENCES 'role' ('id')
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS 'role' (
	'id'		BIGINT		NOT NULL AUTO_INCREMENT,
	'name'		VARCHAR(32)	NOT NULL,
	PRIMARY KEY ('id'),
	CONSTRAINT UNIQUE_NAME UNIQUE ('name')
);

CREATE TABLE IF NOT EXISTS 'board_game' (
	'id'			BIGINT			NOT NULL AUTO_INCREMENT,
	'name'			VARCHAR(512)	NOT NULL,
	'rent_price'	BIGINT 			NOT NULL,
	'qoantity'		BIGINT 			NULL,
	PRIMARY KEY ('id'),
	CONSTRAINT UNIQUE_NAME UNIQUE ('name')
);

CREATE TABLE IF NOT EXISTS 'rent' (
	'id'			BIGINT	NOT NULL AUTO_INCREMENT,
	'board_game_id'	BIGINT	NOT NULL,
	'order_id'		BIGINT	NOT NULL,
	'from'			DATE	NOT NULL,
	'to'			DATE	NOT NULL,
	'price'			BIGINT 	NOT NULL,
	PRIMARY KEY ('id'),
    CONSTRAINT 'rent_board_game_fk'
        FOREIGN KEY ('board_game_id')
            REFERENCES 'board_game' ('id')
    CONSTRAINT 'rent_order_fk'
        FOREIGN KEY ('order_id')
            REFERENCES 'order' ('id')
);

CREATE TABLE IF NOT EXISTS 'order' (
	'id'			BIGINT		NOT NULL AUTO_INCREMENT,
	'user_id'		BIGINT		NOT NULL,
	'total_price'	BIGINT 		NOT NULL,
	'date'			DATE		NOT NULL,
	'status'		VARCHAR(32)	NOT NULL,
	PRIMARY KEY ('id'),
    CONSTRAINT 'order_user_fk'
        FOREIGN KEY ('user_id')
            REFERENCES 'user' ('id')
);

CREATE TABLE IF NOT EXISTS 'accessory' (
	'id'		BIGINT			NOT NULL AUTO_INCREMENT,
	'name'		VARCHAR(512) 	NOT NULL,
	'price'		BIGINT 			NOT NULL,
	'quantity'	BIGINT 			NULL,
	PRIMARY KEY ('id'),
	CONSTRAINT UNIQUE_NAME UNIQUE ('name')
);

CREATE TABLE IF NOT EXISTS 'purchase' (
	'id'			BIGINT	NOT NULL AUTO_INCREMENT,
	'accessory_id'	BIGINT	NOT NULL,
	'order_id'		BIGINT	NOT NULL,
	'quantity'		BIGINT	NOT NULL,
	'price'			BIGINT 	NOT NULL,
	PRIMARY KEY ('id'),
    CONSTRAINT 'purchase_accessory_fk'
        FOREIGN KEY ('accessory_id')
            REFERENCES 'accessory' ('id'),
    CONSTRAINT 'purchase_order_fk'
        FOREIGN KEY ('order_id')
            REFERENCES 'order' ('id')
);

COMMIT;