drop table if exists users;

Create table users (
	id int AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(80) NOT NULL,
	email VARCHAR(100) NOT NULL,
	user_name VARCHAR(80) NOT NULL,
	password VARCHAR(100) NOT NULL,
	role_id int,
	INDEX role_ind (role_id),
	FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);