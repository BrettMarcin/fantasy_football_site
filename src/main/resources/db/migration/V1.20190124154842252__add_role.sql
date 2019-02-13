drop table if exists role;

Create table role (
	id int AUTO_INCREMENT PRIMARY KEY,
	role_name VARCHAR(20) NOT NULL,
	description VARCHAR(20) NOT NULL
);