drop table if exists users;

Create table users (
	id int AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(80) NOT NULL,
	email VARCHAR(100) NOT NULL,
	user_name VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	KEY user_name_key (user_name)
);