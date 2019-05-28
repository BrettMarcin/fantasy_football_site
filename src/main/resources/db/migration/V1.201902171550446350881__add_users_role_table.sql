drop table if exists draft_users_invited;

Create table users_role (
	users_name VARCHAR(100) not null,
	role_id int not null,
	PRIMARY KEY (users_name, role_id),
	CONSTRAINT users_role_ibfk_1 FOREIGN KEY (users_name) REFERENCES users(user_name),
	CONSTRAINT users_role_ibfk_2 FOREIGN KEY (role_id) REFERENCES role(id)
);
