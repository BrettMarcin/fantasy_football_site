drop table if exists role;

Create table role (
	id int AUTO_INCREMENT PRIMARY KEY,
	role_name VARCHAR(20) NOT NULL,
	description VARCHAR(20) NOT NULL,
	the_user_name VARCHAR(100),
-- 	index user_name_key (the_user_name),
	FOREIGN KEY (the_user_name) REFERENCES users(user_name) ON DELETE CASCADE
);